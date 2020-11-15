package com.hardgforgif.dragonboatracing.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hardgforgif.dragonboatracing.DragonBoatRacing;
import com.hardgforgif.dragonboatracing.tools.CollisionRect;

public class Obstacle {
    public static final int SPEED = 250;
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;
    private static Texture texture;

    float x, y;
    CollisionRect rect;
    public boolean remove = false;

    public Obstacle(float x) {
        this.x = x;
        this.y = DragonBoatRacing.HEIGHT - 100;
        this.rect = new CollisionRect(x, y, WIDTH, HEIGHT);

        texture = new Texture("bottle.png");
    }

    public void update(float deltaTime) {
        y -= SPEED * deltaTime;
        if (y < -HEIGHT)
            remove = true;

        rect.move(x, y);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, WIDTH, HEIGHT);
    }

    public CollisionRect getCollisionRect () {
        return rect;
    }
}

