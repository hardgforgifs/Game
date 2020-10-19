package com.hardgforgif.dragonboatracing.standaloneprojects.moveableplayer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;

public class MouseMovementPlayer implements ApplicationListener, InputProcessor {
    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    private Sprite reddot;
    private Sprite greendot;

    private float posX, posY; //player starting coordinates

    private Vector2 playerPos = new Vector2();
    private Vector2 mousePos = new Vector2();
    private Vector2 directionVector = new Vector2();
    private Vector2 velocity = new Vector2();
    private Vector2 movement = new Vector2();
    float speed = 150;

    private ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        batch = new SpriteBatch();

        texture = new Texture("arrow.png");
        sprite = new Sprite(texture);
        posX = w/2 - sprite.getWidth()/2;
        posY = h/2 - sprite.getHeight()/2;
        sprite.setPosition(posX,posY);
        Gdx.input.setInputProcessor(this);
        System.out.println(sprite.getOriginX());
        System.out.println(sprite.getOriginY());
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // getOrigin return the position of the center of the image, relative to the bottom left corner
        // in order to make the player roatet around it's center we need to find the coordinates of the image center
        float originX = sprite.getOriginX() + sprite.getX();
        float originY = sprite.getOriginY() + sprite.getY();

        // rotate the player
        float angle = MathUtils.atan2(mousePos.y - originY, mousePos.x - originX) * MathUtils.radDeg - 90;
        if(angle < 0)
        {
            angle = 360 + angle;
        }
        sprite.setRotation(angle);

        // we then need to calculate the position of the player's head
        Vector2 playerHeadPos = new Vector2();
        float radius = sprite.getHeight()/2;
        playerHeadPos.set(originX + radius * MathUtils.cosDeg(angle + 90),
                          originY + radius * MathUtils.sinDeg(angle + 90));

        // move the player
        // we move the player based on the direction vector between the player head and the mouse position
        playerPos.set(sprite.getX(), sprite.getY());
        directionVector.set(mousePos).sub(playerHeadPos).nor();
        velocity.set(directionVector).scl(speed);
        movement.set(velocity).scl(Gdx.graphics.getDeltaTime());
        if (playerHeadPos.dst2(mousePos) > movement.len2()) {
            playerPos.add(movement);
        } else {
            playerHeadPos.set(mousePos);
        }
        sprite.setX(playerPos.x);
        sprite.setY(playerPos.y);

        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mousePos.set(screenX, Gdx.graphics.getHeight() - screenY);
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
