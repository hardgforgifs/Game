package com.hardgforgif.dragonboatracing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.hardgforgif.dragonboatracing.tools.ScrollingBackground;

public class MenuUI extends UI {

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

    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture logo;

    ScrollingBackground scrollingBackground = new ScrollingBackground();


    public MenuUI(){
        scrollingBackground.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        scrollingBackground.setSpeedFixed(true);
        scrollingBackground.setSpeed(ScrollingBackground.DEFAULT_SPEED);

        playButtonActive = new Texture("PlaySelected.png");
        playButtonInactive = new Texture("PlayUnselected.png");
        exitButtonActive = new Texture("ExitSelected.png");
        exitButtonInactive = new Texture("ExitUnselected.png");
        logo = new Texture("Title.png");
    }

    @Override
    public void drawUI(Batch batch, Vector2 mousePos, float screenWidth, float delta) {
        batch.begin();
        scrollingBackground.updateAndRender(delta, batch);
        batch.draw(logo, screenWidth / 2 - LOGO_WIDTH / 2, LOGO_Y, LOGO_WIDTH, LOGO_HEIGHT);

        float x = screenWidth / 2 - PLAY_BUTTON_WIDTH / 2;
        if (
                mousePos.x < x + PLAY_BUTTON_WIDTH && mousePos.x > x &&
                        // cur pos < top_height
                        mousePos.y < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT &&
                        mousePos.y > PLAY_BUTTON_Y
        ) {
            batch.draw(playButtonActive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        } else {
            batch.draw(playButtonInactive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }

        x = screenWidth / 2 - EXIT_BUTTON_WIDTH / 2;
        if (
                mousePos.x < x + EXIT_BUTTON_WIDTH && mousePos.x > x &&
                        mousePos.y < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT &&
                        mousePos.y > EXIT_BUTTON_Y
        ) {
            batch.draw(exitButtonActive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        } else {
            batch.draw(exitButtonInactive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }
        batch.end();

        if (GameData.music.isPlaying() == false) {
            GameData.music.play();
        }
    }

    @Override
    public void drawUI(Batch batch, Player playerBoat) {

    }

    @Override
    public void getInput(float screenWidth, Vector2 clickPos) {
        float x = screenWidth / 2 - PLAY_BUTTON_WIDTH / 2;
        if (
                clickPos.x < x + PLAY_BUTTON_WIDTH && clickPos.x > x &&
                        // cur pos < top_height
                        clickPos.y < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT &&
                        clickPos.y > PLAY_BUTTON_Y
        ) {
            GameData.mainMenu = false;
            GameData.choosingBoat = true;
            GameData.music.stop();
            GameData.music = Gdx.audio.newMusic(Gdx.files.internal("Love_Drama.ogg"));
            GameData.currentUI = new ChoosingUI();
        }

        x = screenWidth / 2 - EXIT_BUTTON_WIDTH / 2;
        if (clickPos.x < x + EXIT_BUTTON_WIDTH && clickPos.x > x &&
                clickPos.y < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT &&
                clickPos.y > EXIT_BUTTON_Y
        ) {
            Gdx.app.exit();
        }



    }
}
