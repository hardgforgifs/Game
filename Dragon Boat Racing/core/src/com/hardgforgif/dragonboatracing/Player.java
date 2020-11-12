package com.hardgforgif.dragonboatracing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.hardgforgif.dragonboatracing.standaloneprojects.moveableplayer.BodyEditorLoader;
import javafx.scene.Camera;

public class Player extends Boat{
    private OrthographicCamera camera;

    public Player(float robustness, float stamina, float handling, float speed, String textureName, OrthographicCamera camera, Lane lane) {
        super(robustness, stamina, handling, speed, textureName, lane);
        this.camera = camera;
    }

    @Override
    public void moveBoat(float metersToPixels) {
        // Get the coordinates of the center of the boat
        float originX = boatBody.getPosition().x * metersToPixels;
        float originY = boatBody.getPosition().y * metersToPixels;

        // First we need to calculate the position of the playear's head (the front of the boat)
        // So we can move him based on this and not the center of the boat
        Vector2 playerHeadPos = new Vector2();
        float radius = boatSprite.getHeight()/2;
        playerHeadPos.set(originX + radius * MathUtils.cosDeg(boatSprite.getRotation() + 90),
                originY + radius * MathUtils.sinDeg(boatSprite.getRotation() + 90));

        // Create the vector that shows which way we need to move
        Vector2 target = new Vector2();

        // Calculate the x and y positions of the direction vector, based on the rotation of the player
        double auxAngle = boatSprite.getRotation() % 90;
        if (boatSprite.getRotation() < 90 || boatSprite.getRotation() >= 180 && boatSprite.getRotation() < 270)
            auxAngle = 90 - auxAngle;
        auxAngle = auxAngle * MathUtils.degRad;
        float x = (float) (Math.cos(auxAngle) * speed);
        float y = (float) (Math.sin(auxAngle) * speed);

        // Build the direction vector based on the position of the player's head
        if (boatSprite.getRotation() < 90)
            target.set(playerHeadPos.x - x, playerHeadPos.y + y);
        else if (boatSprite.getRotation() < 180)
            target.set(playerHeadPos.x - x, playerHeadPos.y - y);
        else if (boatSprite.getRotation() < 270)
            target.set(playerHeadPos.x + x, playerHeadPos.y - y);
        else
            target.set(playerHeadPos.x + x, playerHeadPos.y + y);

        Vector2 direction = new Vector2();
        Vector2 velocity = new Vector2();
        Vector2 movement = new Vector2();

        direction.set(target).sub(playerHeadPos).nor();
        velocity.set(direction).scl(speed);
        movement.set(velocity).scl(Gdx.graphics.getDeltaTime());

        boatBody.setLinearVelocity(movement);

    }

    @Override
    public void rotateBoat(float mouseAngle) {
        // Calculate the difference between the angle of the mouse and the current rotation of the boat
        float angleDifference = mouseAngle - boatBody.getAngle() * MathUtils.radDeg;

        // If the angle is really small, set the player to face the mouse
        if (Math.abs(angleDifference) < turningSpeed) {
            boatBody.setTransform(boatBody.getPosition(), mouseAngle * MathUtils.degRad);
        }

        else {
            // Create the new angle we want the player to be rotated to every frame, based on the turning speed
            float newAngle = boatSprite.getRotation();

            // We need to manage turning direction based on the difference in angles
            // If the difference is smaller than -180, it's faster to rotate clockwise
            if (angleDifference < -180)
                newAngle += turningSpeed;
            // if the difference is greater than 180, it's faster to rotate counter-clockwise
            else if (angleDifference > 180)
                newAngle += turningSpeed * (-1);
            // otherwise rotate in the direction given by the sign of angleDifference
            else
                newAngle += turningSpeed * (angleDifference / Math.abs(angleDifference));

            // we want to keep the player rotation between 0 and 360 at all times
            if(newAngle < 0)
            {
                newAngle = 360 + newAngle;
            }
            if(newAngle > 360)
            {
                newAngle = newAngle - 360;
            }

            boatBody.setTransform(boatBody.getPosition(), newAngle * MathUtils.degRad);
        }
    }

    // Returns the angle between the mouse and the player's center
    private float getMouseAngle(Vector2 mousePos, float originX, float originY) {
        float mouseAngle = MathUtils.atan2(mousePos.y - originY, mousePos.x - originX) * MathUtils.radDeg - 90;
        if(mouseAngle < 0)
        {
            mouseAngle = 360 + mouseAngle;
        }
        return mouseAngle;
    }

    public void updatePlayer(Vector2 mousePos, float metersToPixels) {
        float originX = boatBody.getPosition().x * metersToPixels;
        float originY = boatBody.getPosition().y * metersToPixels;

        float mouseAngle = getMouseAngle(mousePos, originX, originY);
        rotateBoat(mouseAngle);

        boatSprite.setRotation((float)Math.toDegrees(boatBody.getAngle()));
        boatSprite.setPosition((boatBody.getPosition().x * metersToPixels) - boatSprite.getWidth() / 2,
                (boatBody.getPosition().y * metersToPixels) - boatSprite.getHeight() / 2);


        moveBoat(metersToPixels);
        updateLimits();
    }
}
