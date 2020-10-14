package com.hardgforgif.dragonboatracing.standaloneprojects.moveableplayer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Player implements ApplicationListener, InputProcessor {
    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    private float posX, posY;
    private float moveRight = 0, moveLeft = 0;

    // the speed of the player, this can be a dependency on the Club Penguin Game
    private float speed = 1;

    @Override
    public boolean keyDown(int keycode) {
        float moveAmount = 1.0f * speed;
        if (keycode == Input.Keys.LEFT)
            moveLeft = moveAmount * (-1);
        if (keycode == Input.Keys.RIGHT)
            moveRight = moveAmount;
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT)
            moveLeft = 0;
        if (keycode == Input.Keys.RIGHT)
            moveRight = 0;
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
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        batch = new SpriteBatch();

        texture = new Texture("badlogic.jpg");
        sprite = new Sprite(texture);
        posX = w/2 - sprite.getWidth()/2;
        posY = h/2 - sprite.getHeight()/2;
        sprite.setPosition(posX,posY);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        posX += moveLeft + moveRight;
        sprite.setPosition(posX,posY);
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
        batch.dispose();
        texture.dispose();
    }
}
