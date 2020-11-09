package com.hardgforgif.dragonboatracing.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.hardgforgif.dragonboatracing.DragonBoatRacing;
import com.hardgforgif.dragonboatracing.tools.CollisionRect;

public class MainGameScreen implements Screen {
    public static final float SPEED = 300;

    public static final float BOAT_ANIMATION_SPEED = 0.5f;
    public static final int BOAT_WIDTH_PIXEL = 17;
    public static final int BOAT_HEIGHT_PIXEL = 32;
    public static final int BOAT_WIDTH = BOAT_WIDTH_PIXEL * 3;
    public static final int BOAT_HEIGHT = BOAT_HEIGHT_PIXEL * 3;

    float x;
    float y;

    Texture img;
    CollisionRect playerRect;

    DragonBoatRacing game;

    public MainGameScreen(DragonBoatRacing game) {
        this.game = game;
        x = DragonBoatRacing.WIDTH / 2 - BOAT_WIDTH / 2;
        y = 15;

        img = new Texture("boat1.png");

        playerRect = new CollisionRect(0, 0, BOAT_WIDTH, BOAT_HEIGHT);

        game.scrollingBackground.setSpeedFixed(false);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (isUp()) y += SPEED * Gdx.graphics.getDeltaTime();
        if (isDown()) y -= SPEED * Gdx.graphics.getDeltaTime();
        if (isLeft()) x -= SPEED * Gdx.graphics.getDeltaTime();
        if (isRight()) x += SPEED * Gdx.graphics.getDeltaTime();

        playerRect.move(x, y);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        game.scrollingBackground.updateAndRender(delta, game.batch);

        game.batch.draw(img, x, y);
        game.batch.end();
    }

    private boolean isRight() {
        return Gdx.input.isKeyPressed(Input.Keys.UP) || (Gdx.input.isTouched() && game.cam.getInputInGameWorld().x >= x);
    }

    private boolean isLeft () {
        return Gdx.input.isKeyPressed(Input.Keys.LEFT) || (Gdx.input.isTouched() && game.cam.getInputInGameWorld().x < x);
    }

    private boolean isUp () {
        return Gdx.input.isKeyJustPressed(Input.Keys.UP) || (Gdx.input.isTouched() && game.cam.getInputInGameWorld().y >= y);
    }

    private boolean isDown () {
        return Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || (Gdx.input.isTouched() && game.cam.getInputInGameWorld().y < y);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
