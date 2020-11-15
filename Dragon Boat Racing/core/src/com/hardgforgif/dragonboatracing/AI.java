package com.hardgforgif.dragonboatracing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class AI extends Boat{

    public Vector2 laneChecker;
    public Vector2 objectChecker;
    private OrthographicCamera camera;
    private float targetAngle = 0f;
    private boolean dodging = false;
    private float detectedObstacleYPos;

    public AI(float robustness, float stamina, float handling, float speed, String textureName, OrthographicCamera camera, Lane lane) {
        super(robustness, stamina, handling, speed, textureName, lane);
        this.camera = camera;
        this.turningSpeed = 0.25f;
    }

    @Override
    public void moveBoat(float metersToPixels) {
        // Get the coordinates of the center of the boat
        float originX = boatBody.getPosition().x * metersToPixels;
        float originY = boatBody.getPosition().y * metersToPixels;

        // First we need to calculate the position of the playear's head (the front of the boat)
        // So we can move him based on this and not the center of the boat
        Vector2 boatHeadPos = new Vector2();
        float radius = boatSprite.getHeight()/2;
        boatHeadPos.set(originX + radius * MathUtils.cosDeg(boatSprite.getRotation() + 90),
                originY + radius * MathUtils.sinDeg(boatSprite.getRotation() + 90));

        // Create the vector that shows which way we need to move
        Vector2 target = new Vector2();

        // Calculate the x and y positions of the direction vector, based on the rotation of the boat
        double auxAngle = boatSprite.getRotation() % 90;
        if (boatSprite.getRotation() < 90 || boatSprite.getRotation() >= 180 && boatSprite.getRotation() < 270)
            auxAngle = 90 - auxAngle;
        auxAngle = auxAngle * MathUtils.degRad;
        float x = (float) (Math.cos(auxAngle) * speed);
        float y = (float) (Math.sin(auxAngle) * speed);

        // Build the direction vector based on the position of the player's head
        if (boatSprite.getRotation() < 90)
            target.set(boatHeadPos.x - x, boatHeadPos.y + y);
        else if (boatSprite.getRotation() < 180)
            target.set(boatHeadPos.x - x, boatHeadPos.y - y);
        else if (boatSprite.getRotation() < 270)
            target.set(boatHeadPos.x + x, boatHeadPos.y - y);
        else
            target.set(boatHeadPos.x + x, boatHeadPos.y + y);

        Vector2 direction = new Vector2();
        Vector2 velocity = new Vector2();
        Vector2 movement = new Vector2();

        direction.set(target).sub(boatHeadPos).nor();
        velocity.set(direction).scl(speed);
        movement.set(velocity).scl(Gdx.graphics.getDeltaTime());

        boatBody.setLinearVelocity(movement);
    }

    @Override
    public void rotateBoat(float angle) {
        // Calculate the difference between the target angle and the current rotation of the boat
        float angleDifference = angle - boatBody.getAngle() * MathUtils.radDeg;

        if (Math.abs(angleDifference) < turningSpeed) {
            boatBody.setTransform(boatBody.getPosition(), angle * MathUtils.degRad);
            return;
        }

        // Create the new angle we want the player to be rotated to every frame, based on the turning speed
        float newAngle = boatSprite.getRotation();

        if (angleDifference < 0)
            newAngle += turningSpeed * (-1);
        else if (angleDifference > 0)
            newAngle += turningSpeed;

        boatBody.setTransform(boatBody.getPosition(), newAngle * MathUtils.degRad);
    }


    private Vector2 getAIPredictionVector(float distance, float metersToPixels) {
        // Get the coordinates of the center of the boat
        float originX = boatBody.getPosition().x * metersToPixels;
        float originY = boatBody.getPosition().y * metersToPixels;

        // First we need to calculate the position of the playear's head (the front of the boat)
        // So we can move him based on this and not the center of the boat
        Vector2 boatHeadPos = new Vector2();
        float radius = (boatSprite.getHeight() * boatSprite.getScaleY())/2;
        boatHeadPos.set(originX + radius * MathUtils.cosDeg(boatSprite.getRotation() + 90),
                originY + radius * MathUtils.sinDeg(boatSprite.getRotation() + 90));

        // Create the vector that shows which way we need to move
        Vector2 target = new Vector2();

        // Calculate the x and y positions of the direction vector, based on the rotation of the boat
        double auxAngle = boatSprite.getRotation() % 90;
        if (boatSprite.getRotation() < 90 || boatSprite.getRotation() >= 180 && boatSprite.getRotation() < 270)
            auxAngle = 90 - auxAngle;
        auxAngle = auxAngle * MathUtils.degRad;
        float x = (float) (Math.cos(auxAngle) * distance);
        float y = (float) (Math.sin(auxAngle) * distance);

        // Build the direction vector based on the position of the player's head
        if (boatSprite.getRotation() < 90)
            target.set(boatHeadPos.x - x, boatHeadPos.y + y);
        else if (boatSprite.getRotation() < 180)
            target.set(boatHeadPos.x - x, boatHeadPos.y - y);
        else if (boatSprite.getRotation() < 270)
            target.set(boatHeadPos.x + x, boatHeadPos.y - y);
        else
            target.set(boatHeadPos.x + x, boatHeadPos.y + y);

        return target;
    }

    private void stayInLane(float[] predictLimits){

        float laneWidth = predictLimits[1] - predictLimits[0];
        float middleOfLane = predictLimits[0] + laneWidth / 2;

        if (laneChecker.x < predictLimits[0] && boatSprite.getRotation() == 0)
            targetAngle = -15f;

        else if (laneChecker.x < middleOfLane - laneWidth / 4 && boatSprite.getRotation() > 0)
            targetAngle = 0f;

        if (laneChecker.x > predictLimits[1] && boatSprite.getRotation() == 0)
            targetAngle = 15f;

        else if (laneChecker.x > middleOfLane + laneWidth / 4  && boatSprite.getRotation() < 0)
            targetAngle = 0f;

        rotateBoat(targetAngle);
        boatSprite.setRotation((float)Math.toDegrees(boatBody.getAngle()));
    }

    private boolean obstaclesInRange(){
        for (Obstacle obstacle : this.lane.obstacles){
            float width = obstacle.obstacleSprite.getWidth() * obstacle.obstacleSprite.getScaleX();
            float height = obstacle.obstacleSprite.getHeight() * obstacle.obstacleSprite.getScaleY();
            float posX = obstacle.obstacleSprite.getX() + obstacle.obstacleSprite.getWidth() / 2 - width / 2;
            float posY = obstacle.obstacleSprite.getY() + obstacle.obstacleSprite.getHeight() / 2 - height / 2;


            if (objectChecker.x >= posX && objectChecker.x <= posX + width &&
                    objectChecker.y >= posY && objectChecker.y <= posY + height){
                detectedObstacleYPos = posY;
                return true;
            }
        }
        return false;
    }

    private void dodgeObstacles(){
        if (obstaclesInRange()){
            float boatPosX = boatSprite.getX() + boatSprite.getWidth() / 2;
            if (rightLimit - boatPosX < boatPosX - leftLimit){
                targetAngle = 15f;
            }
            else
                targetAngle = -15f;
            dodging = true;
            rotateBoat(targetAngle);
            boatSprite.setRotation((float)Math.toDegrees(boatBody.getAngle()));

        }
    }


    public void updateAI(float metersToPixels) {

        boatSprite.setPosition((boatBody.getPosition().x * metersToPixels) - boatSprite.getWidth() / 2,
                (boatBody.getPosition().y * metersToPixels) - boatSprite.getHeight() / 2);

        //updateLimits();
        laneChecker = getAIPredictionVector(400f, metersToPixels);
        objectChecker = getAIPredictionVector(300f, metersToPixels);
        float[] predictLimits = getLimitsAt(laneChecker.y);

        dodgeObstacles();
        if (dodging){
            rotateBoat(targetAngle);
            boatSprite.setRotation((float)Math.toDegrees(boatBody.getAngle()));

            System.out.println(boatSprite.getRotation());
//            System.out.println("testing");
            float boatBottomLocation = boatSprite.getY() + boatSprite.getHeight() / 2 +
                                        boatSprite.getHeight() / 2 * boatSprite.getScaleY();

            if (boatBottomLocation >= detectedObstacleYPos)
                dodging = false;

        }
        else{

            stayInLane(predictLimits);
        }

        moveBoat(metersToPixels);
        updateLimits();

    }
}
