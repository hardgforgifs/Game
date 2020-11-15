package com.hardgforgif.dragonboatracing.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.hardgforgif.dragonboatracing.DragonBoatRacing;
import com.hardgforgif.dragonboatracing.entities.Boat;
import com.hardgforgif.dragonboatracing.entities.Obstacle;

import java.util.ArrayList;

public class MainGameScreen implements Screen {
    DragonBoatRacing game;

    public MainGameScreen(DragonBoatRacing game) {
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
        //Dispose of components?
    }
}
