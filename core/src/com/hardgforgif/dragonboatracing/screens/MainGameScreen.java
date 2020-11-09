package com.hardgforgif.dragonboatracing.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.hardgforgif.dragonboatracing.DragonBoatRacing;
import com.hardgforgif.dragonboatracing.entities.Boat;
import com.hardgforgif.dragonboatracing.entities.Obstacle;

import java.util.ArrayList;

public class MainGameScreen implements Screen {
    public static final float SPEED = 300;

    public static final float BOAT_ANIMATION_SPEED = 0.5f;
    public static final int BOAT_WIDTH_PIXEL = 17;
    public static final int BOAT_HEIGHT_PIXEL = 32;
    public static final int BOAT_WIDTH = BOAT_WIDTH_PIXEL * 3;
    public static final int BOAT_HEIGHT = BOAT_HEIGHT_PIXEL * 3;

    ArrayList<Boat> boats;
    ArrayList<Obstacle> obstacles;


    DragonBoatRacing game;

    public MainGameScreen(DragonBoatRacing game) {
        this.game = game;

        boats = new ArrayList<Boat>();
        boats.add(new Boat(15, "boat1.png", true));

        obstacles = new ArrayList<Obstacle>();
        obstacles.add(new Obstacle(50));

        game.scrollingBackground.setSpeedFixed(false);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {



        for (Boat boat: boats) {
            if (boat.isPrimary()) {
                if (isUp()) boat.moveUp(Gdx.graphics.getDeltaTime());
                if (isDown()) boat.moveDown(Gdx.graphics.getDeltaTime());
                if (isLeft()) boat.moveLeft(Gdx.graphics.getDeltaTime());
                if (isRight()) boat.moveRight(Gdx.graphics.getDeltaTime());
            }
        }

        for (Obstacle obstacle: obstacles) {
            obstacle.update(Gdx.graphics.getDeltaTime());
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        game.scrollingBackground.updateAndRender(delta, game.batch);

        for (Boat boat: boats) {
            boat.render(game.batch);
        }

        for (Obstacle obstical: obstacles) {
            obstical.render(game.batch);
        }

        game.batch.end();
    }

    private boolean isRight() {
        return Gdx.input.isKeyPressed(Input.Keys.UP) || (Gdx.input.isTouched());
    }

    private boolean isLeft () {
        return Gdx.input.isKeyPressed(Input.Keys.LEFT) || (Gdx.input.isTouched());
    }

    private boolean isUp () {
        return Gdx.input.isKeyPressed(Input.Keys.UP) || (Gdx.input.isTouched());
    }

    private boolean isDown () {
        return Gdx.input.isKeyPressed(Input.Keys.DOWN) || (Gdx.input.isTouched());
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
