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

public class BoatChoosingScreen implements Screen {

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

    private BitmapFont acceleration_label = new BitmapFont();
    private BitmapFont maneuverability_label = new BitmapFont();
    private BitmapFont stamina_label = new BitmapFont();
    private BitmapFont robustness_label = new BitmapFont();
    private Texture background = new Texture(Gdx.files.internal("Choosing_background.png"));
    private Sprite background_sprite = new Sprite(background);
    private Texture stamina = new Texture(Gdx.files.internal("Stamina_bar.png"));;
    private Texture robustness  = new Texture(Gdx.files.internal("Robustness_bar.png"));;
    private Sprite r_bar = new Sprite(robustness);
    private Sprite s_bar = new Sprite(stamina);
    private Sprite a_bar = new Sprite(stamina);
    private Sprite m_bar = new Sprite(stamina);


    final DragonBoatRacing game;

    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture logo;
    //Music current_music;

    public BoatChoosingScreen(final DragonBoatRacing game) {
        this.game = game;

        //game.current_music = Gdx.audio.newMusic(Gdx.files.internal("Vibing.ogg"));

        game.scrollingBackground.setSpeedFixed(true);
        game.scrollingBackground.setSpeed(ScrollingBackground.DEFAULT_SPEED);

        final BoatChoosingScreen boatChoosingScreen = this;

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
                    boatChoosingScreen.dispose();
                    Gdx.app.exit();
                }

                //Play game button
                x = DragonBoatRacing.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2;
                if (
                        game.cam.getInputInGameWorld().x < x + PLAY_BUTTON_WIDTH && game.cam.getInputInGameWorld().x > x &&
                                game.cam.getInputInGameWorld().y < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT &&
                                game.cam.getInputInGameWorld().y > PLAY_BUTTON_Y
                ) {
                    boatChoosingScreen.dispose();
                    game.setScreen(new MainGameScreen(game));
                }

                return super.touchUp(screenX, screenY, pointer, button);
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        background_sprite.setPosition(250, 200);
        float full_bar_width = DragonBoatRacing.WIDTH /3;
        s_bar.setSize(full_bar_width,30);
        r_bar.setSize(full_bar_width,30);
        m_bar.setSize(full_bar_width,30);
        a_bar.setSize(full_bar_width,30);
        r_bar.setPosition(510,350);
        s_bar.setPosition(510,480);
        a_bar.setPosition(510,450);
        m_bar.setPosition(510,400);


        game.batch.begin();

        //Do we want this removed?
        game.scrollingBackground.updateAndRender(delta, game.batch);

        //Draws the components of the UI to the batch
            background_sprite.draw(game.batch);
            stamina_label.draw(game.batch, "Stamina: ", 420, 500);
            s_bar.draw(game.batch);
            r_bar.draw(game.batch);
            a_bar.draw(game.batch);
            m_bar.draw(game.batch);

        game.batch.end();

        //Keeps music looping whilst on this screen
        if (game.current_music.isPlaying() == false) {
            game.current_music.play();
        }
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
        Gdx.input.setInputProcessor(null);
    }
}
