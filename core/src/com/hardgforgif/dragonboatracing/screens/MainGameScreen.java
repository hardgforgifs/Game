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
    public static final float SPEED = 300;

    public static final float BOAT_ANIMATION_SPEED = 0.5f;
    public static final int BOAT_WIDTH_PIXEL = 17;
    public static final int BOAT_HEIGHT_PIXEL = 32;
    public static final int BOAT_WIDTH = BOAT_WIDTH_PIXEL * 3;
    public static final int BOAT_HEIGHT = BOAT_HEIGHT_PIXEL * 3;

    private BitmapFont position_font = new BitmapFont();
    private BitmapFont robustness_font = new BitmapFont();
    private BitmapFont stamina_font = new BitmapFont();
    private Texture stamina = new Texture(Gdx.files.internal("Stamina_bar.png"));;
    private Texture robustness  = new Texture(Gdx.files.internal("Robustness_bar.png"));;
    private Sprite r_bar = new Sprite(robustness);
    private Sprite s_bar = new Sprite(stamina);

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

        //Change bar widths based on player's statistics

        float full_bar_width = DragonBoatRacing.WIDTH / 4;
        //Scaling factor will need to be applied
        int players_stamina = 90;
        s_bar.setSize(full_bar_width,30);
        r_bar.setSize(full_bar_width,30);
        r_bar.setPosition(10 ,120);
        s_bar.setPosition(10,60);
        //position_font.getData().setScale()

        game.batch.begin();

            game.scrollingBackground.updateAndRender(delta, game.batch);

            for (Boat boat: boats) {
                boat.render(game.batch);
            }

            for (Obstacle obstical: obstacles) {
                obstical.render(game.batch);
            }

            //Draws the components of the UI to the batch
            int player_position = 1;
            //! Change this to accurately represent the player's position
            position_font.draw(game.batch, player_position + "/8", 1225, 700);
            robustness_font.draw(game.batch, "Robustness", 10, 110);
            stamina_font.draw(game.batch, "Stamina", 10,170);
            r_bar.draw(game.batch);
            s_bar.draw(game.batch);

        game.batch.end();

        //Keeps music looping whilst on this screen
        if (game.current_music.isPlaying() == false) {
            game.current_music.play();
        }
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
