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

    public Player(float robustness, float stamina, float handling, float speed, String textureName, Lane lane) {
        super(robustness, stamina, handling, speed, textureName, lane);
        this.turningSpeed = 0.25f;
    }

    @Override
    public void moveBoat() {
        // Get the coordinates of the center of the boat
        float originX = boatBody.getPosition().x * GameData.METERS_TO_PIXELS;
        float originY = boatBody.getPosition().y * GameData.METERS_TO_PIXELS;

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

    public void updatePlayer(boolean[] pressedKeys, float delta) {
        if (pressedKeys[1])
            targetAngle = 90f;
        else if (pressedKeys[3])
            targetAngle = -90f;
        else
            targetAngle = 0f;

        rotateBoat(targetAngle);

        boatSprite.setRotation((float)Math.toDegrees(boatBody.getAngle()));
        boatSprite.setPosition((boatBody.getPosition().x * GameData.METERS_TO_PIXELS) - boatSprite.getWidth() / 2,
                (boatBody.getPosition().y * GameData.METERS_TO_PIXELS) - boatSprite.getHeight() / 2);

        moveBoat();
        updateLimits();
        stamina -= 0.8 * delta;
    }
}
