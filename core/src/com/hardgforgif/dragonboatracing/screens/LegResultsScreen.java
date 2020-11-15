package com.hardgforgif.dragonboatracing.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.hardgforgif.dragonboatracing.DragonBoatRacing;
import com.hardgforgif.dragonboatracing.tools.ScrollingBackground;

public class LegResultsScreen implements Screen {

    //Sets the dimensions for all the UI components
    private static final int LOGO_WIDTH = 400;
    private static final int LOGO_HEIGHT = 200;
    private static final int LOGO_Y = 450;

    private static final int PLAY_BUTTON_WIDTH = 300;
    private static final int PLAY_BUTTON_HEIGHT = 120;
    private static final int PLAY_BUTTON_Y = 230;

    private static final int EXIT_BUTTON_WIDTH = 250;
    private static final int EXIT_BUTTON_HEIGHT = 120;
    private static final int EXIT_BUTTON_Y = 100;

    private BitmapFont first_place = new BitmapFont();
    private BitmapFont second_place = new BitmapFont();
    private BitmapFont third_place = new BitmapFont();
    private BitmapFont fourth_place = new BitmapFont();
    private Texture background = new Texture(Gdx.files.internal("Choosing_background.png"));
    private Sprite background_sprite = new Sprite(background);

    final DragonBoatRacing game;

    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture logo;
    //Music current_music;

    public LegResultsScreen(final DragonBoatRacing game) {
        this.game = game;

        //game.current_music = Gdx.audio.newMusic(Gdx.files.internal("Vibing.ogg"));

        game.scrollingBackground.setSpeedFixed(true);
        game.scrollingBackground.setSpeed(ScrollingBackground.DEFAULT_SPEED);

        final LegResultsScreen legResultsScreen = this;

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                //Exit button
                int x = DragonBoatRacing.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2;
                if (
                        game.cam.getInputInGameWorld().x < x + EXIT_BUTTON_WIDTH && game.cam.getInputInGameWorld().x > x &&
                                game.cam.getInputInGameWorld().y < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT &&
                                game.cam.getInputInGameWorld().y > EXIT_BUTTON_Y
                ) {
                    legResultsScreen.dispose();
                    Gdx.app.exit();
                }

                //Play game button
                x = DragonBoatRacing.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2;
                if (
                        game.cam.getInputInGameWorld().x < x + PLAY_BUTTON_WIDTH && game.cam.getInputInGameWorld().x > x &&
                                game.cam.getInputInGameWorld().y < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT &&
                                game.cam.getInputInGameWorld().y > PLAY_BUTTON_Y
                ) {
                    legResultsScreen.dispose();
                    game.setScreen(new MainGameScreen(game));
                }

                return super.touchUp(screenX, screenY, pointer, button);
            }
        });
    }
    @Override
    public void show () {

    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        background_sprite.setPosition(250,200);

        game.batch.begin();

            //Do we want this removed?
            game.scrollingBackground.updateAndRender(delta, game.batch);

            //Draws the components of the UI to the batch
            background_sprite.draw(game.batch);
            first_place.draw(game.batch, "Team 1                                                             "+"Team1time", 450, 500);
            second_place.draw(game.batch, "Team 2                                                             "+"Team2time", 450, 450);
            third_place.draw(game.batch, "Team 3                                                             "+"Team3time", 450, 400);
            fourth_place.draw(game.batch, "Team 4                                                             "+"Team4time", 450, 350);

        game.batch.end();

        //Keeps music looping whilst on this screen
        if (game.current_music.isPlaying() == false) {
            game.current_music.play();
        }
    }

    @Override
    public void resize (int width, int height) {

    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
    }
}