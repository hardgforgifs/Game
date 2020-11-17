package com.hardgforgif.dragonboatracing.NewUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class ResultsUI {
    private BitmapFont first_place;
    private BitmapFont second_place;
    private BitmapFont third_place;
    private BitmapFont fourth_place;
    private Texture background;
    private Sprite background_sprite;
    private Texture continue_button_active;
    private Texture continue_button_inactive;
    private Sprite continue_button_sprite;
    private static final int CONTINUE_BUTTON_WIDTH = 250;
    private static final int CONTINUE_BUTTON_HEIGHT = 120;
    private static final int CONTINUE_BUTTON_Y = 100;


    public ResultsUI() {
        first_place = new BitmapFont();
        second_place = new BitmapFont();
        third_place = new BitmapFont();
        fourth_place = new BitmapFont();
        background = new Texture(Gdx.files.internal("Background.png"));
        background_sprite = new Sprite(background);
        background_sprite.setPosition(250,200);
        continue_button_active = new Texture(Gdx.files.internal("ContinueSelected.png"));
        continue_button_inactive = new Texture(Gdx.files.internal("ContinueUnselected.png"));
    }


    public void drawUI(Batch batch, float screenWidth, Vector2 mousePos) {
        float x = screenWidth / 2 - CONTINUE_BUTTON_WIDTH / 2;
        //Changes the button texture drawn to the screen based in response to hovering the mouse over the button
        //Checks if the mouse is within the dimensions of the button
        if (mousePos.x < x + CONTINUE_BUTTON_WIDTH &&
                mousePos.x > x &&
                mousePos.y < CONTINUE_BUTTON_Y+CONTINUE_BUTTON_HEIGHT &&
                mousePos.y > CONTINUE_BUTTON_Y)
            //Draws the
            batch.draw(continue_button_active, x, CONTINUE_BUTTON_Y, CONTINUE_BUTTON_WIDTH, CONTINUE_BUTTON_HEIGHT);
        else {
            batch.draw(continue_button_inactive, x, CONTINUE_BUTTON_Y, CONTINUE_BUTTON_WIDTH, CONTINUE_BUTTON_HEIGHT);
        }

        //Draws components to the screen
        batch.begin();
            //Add the actual times for each boat
            background_sprite.draw(batch);
            first_place.draw(batch, "Team 1                                                             "+"Team1time", 450, 500);
            second_place.draw(batch, "Team 2                                                             "+"Team2time", 450, 450);
            third_place.draw(batch, "Team 3                                                             "+"Team3time", 450, 400);
            fourth_place.draw(batch, "Team 4                                                          "+"Team4time", 450, 350);
        batch.end();

        //Keeps music on repeat
        //Dont have the GameData class on my end, so remove comments below
        /*if (GameData.music.isPlaying() == false) {
            GameData.music.play();
        }*/

    }


    /*public void getInput(Vector2 clickPos, float screenWidth) {
        float x = screenWidth / 2 - CONTINUE_BUTTON_WIDTH / 2;
        if (clickPos.x < x + CONTINUE_BUTTON_WIDTH && clickPos.x > x &&
                clickPos.y < CONTINUE_BUTTON_Y + CONTINUE_BUTTON_HEIGHT &&
                clickPos.y > CONTINUE_BUTTON_Y
        ) {
            GameData.legResults = false;
            //Only sets GameData.gamePlay to true if there are further legs to play
            if leg_no < 3 {
                GameData.gamePlay = true;
            }
        }
    } */


    public void dispose() {
        first_place.dispose();
        second_place.dispose();
        third_place.dispose();
        fourth_place.dispose();
        background.dispose();
    }
}
