package com.hardgforgif.dragonboatracing.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hardgforgif.dragonboatracing.tools.CollisionRect;

public class Boat {

    public static final int SPEED = 250;
    public static final int WIDTH = 75;
    public static final int HEIGHT = 150;
    private static Texture texture;

    float x, y;
    boolean primary;
    CollisionRect rect;

    public Boat(float x, String vessel, boolean primary) {
        this.x = x;
        this.y = 15; // start all boats at the bottom
        this.primary = primary;
        this.rect = new CollisionRect(x, y, WIDTH, HEIGHT);
        texture = new Texture(vessel);
    }

    public void moveLeft(float deltaTime) {
        x -= SPEED * deltaTime;
    }

    public void moveRight(float deltaTime) {
        x += SPEED * deltaTime;
    }

    public void moveUp(float deltaTime) {
        y += SPEED * deltaTime;
    }

    public  void moveDown(float deltaTime) {
        y -= SPEED * deltaTime;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, WIDTH, HEIGHT);
    }

    public CollisionRect getCollisionRect () {
        return rect;
    }

    public float getX () {
        return x;
    }

    public float getY () {
        return y;
    }

    public boolean isPrimary() {
        return primary;
    }
}
