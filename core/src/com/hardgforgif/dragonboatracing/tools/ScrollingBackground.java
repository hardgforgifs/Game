package com.hardgforgif.dragonboatracing.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScrollingBackground {

    public static final int DEFAULT_SPEED = 80;
    public static final int ACCELERATION = 50;
    public static final int TARGET_AQUIRED_ACCELERATION = 200;

    Texture image;
    float y1, y2;
    int speed; // in pixels/sec
    int targetSpeed;
    boolean speedFixed;
    float imageScale;
    float scaledHeight;

    public ScrollingBackground() {
        image = new Texture("square.png");

        y1 = 0;
        y2 = image.getHeight();
        speed = 0;
        targetSpeed = DEFAULT_SPEED;
        imageScale = 0;
        speedFixed = true;
    }

    public void updateAndRender (float deltaTime, SpriteBatch batch) {
        // speed adjustment to reach goal
        if (speed < targetSpeed) {
            speed += TARGET_AQUIRED_ACCELERATION * deltaTime;
            if (speed > targetSpeed) speed = targetSpeed;
        }
        else if (speed > targetSpeed) {
            speed -= TARGET_AQUIRED_ACCELERATION * deltaTime;
            if (speed < targetSpeed) speed = targetSpeed;
        }

        if (!speedFixed) speed += ACCELERATION * deltaTime;

        y1 -= speed * deltaTime;
        y2 -= speed * deltaTime;

        // if image reaches the bottom of screen and is not visible,
        // put it back on top

        scaledHeight = image.getHeight() * imageScale;

        if (y1 + scaledHeight <= 0) y1 = y2 + scaledHeight;
        if (y2 + scaledHeight <= 0) y2 = y1 + scaledHeight;

        // render
        batch.draw(image, 0, y1, Gdx.graphics.getWidth(), scaledHeight);
        batch.draw(image, 0, y2, Gdx.graphics.getWidth(), scaledHeight);
    }

    public void resize(int width, int height) {
        imageScale = width / image.getWidth();
    }

    public void setSpeed(int targetSpeed) {
        this.targetSpeed = targetSpeed;
    }

    public void setSpeedFixed(boolean speedFixed) {
        this.speedFixed = speedFixed;
    }
}
