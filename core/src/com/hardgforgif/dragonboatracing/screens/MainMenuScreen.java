package com.hardgforgif.dragonboatracing.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.hardgforgif.dragonboatracing.DragonBoatRacing;
import com.hardgforgif.dragonboatracing.tools.ScrollingBackground;

public class MainMenuScreen implements Screen {

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


    final DragonBoatRacing game;

    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture logo;
    //Music current_music;

    public MainMenuScreen(final DragonBoatRacing game) {
        this.game = game;
        playButtonActive = new Texture("PlaySelected.png");
        playButtonInactive = new Texture("PlayUnselected.png");
        exitButtonActive = new Texture("ExitSelected.png");
        exitButtonInactive = new Texture("ExitUnselected.png");
        logo = new Texture("Title.png");
        game.current_music = Gdx.audio.newMusic(Gdx.files.internal("Vibing.ogg"));

        game.scrollingBackground.setSpeedFixed(true);
        game.scrollingBackground.setSpeed(ScrollingBackground.DEFAULT_SPEED);

        final MainMenuScreen mainMenuScreen = this;

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
                    mainMenuScreen.dispose();
                    Gdx.app.exit();
                }

                //Play game button
                x = DragonBoatRacing.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2;
                if (
                        game.cam.getInputInGameWorld().x < x + PLAY_BUTTON_WIDTH && game.cam.getInputInGameWorld().x > x &&
                        game.cam.getInputInGameWorld().y < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT &&
                        game.cam.getInputInGameWorld().y > PLAY_BUTTON_Y
                ) {
                    mainMenuScreen.dispose();
                    //CHANGE THIS BACK TO MAINGAMESCREEN
                    game.setScreen(new BoatChoosingScreen(game));
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
        game.batch.begin();

        game.scrollingBackground.updateAndRender(delta, game.batch);

        game.batch.draw(logo, DragonBoatRacing.WIDTH / 2 - LOGO_WIDTH / 2, LOGO_Y, LOGO_WIDTH, LOGO_HEIGHT);


        int x = DragonBoatRacing.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2;
        if (
                game.cam.getInputInGameWorld().x < x + PLAY_BUTTON_WIDTH &&
                game.cam.getInputInGameWorld().x > x &&
                // cur pos < top_height
                game.cam.getInputInGameWorld().y < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT &&
                game.cam.getInputInGameWorld().y > PLAY_BUTTON_Y
        ) {
            game.batch.draw(playButtonActive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        } else {
            game.batch.draw(playButtonInactive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }

        x = DragonBoatRacing.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2;
        if (
                game.cam.getInputInGameWorld().x < x + EXIT_BUTTON_WIDTH && game.cam.getInputInGameWorld().x > x &&
                game.cam.getInputInGameWorld().y < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT &&
                game.cam.getInputInGameWorld().y > EXIT_BUTTON_Y
        ) {
            game.batch.draw(exitButtonActive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        } else {
            game.batch.draw(exitButtonInactive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }



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