package com.hardgforgif.dragonboatracing.NewUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

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


    public ResultsUI() {
        first_place = new BitmapFont();
        second_place = new BitmapFont();
        third_place = new BitmapFont();
        fourth_place = new BitmapFont();
        background = new Texture(Gdx.files.internal("Choosing_background.png"));
        background_sprite = new Sprite(background);
        background_sprite.setPosition(250,200);
        continue_button_active = new Texture(Gdx.files.internal("ContinueSelected.png"));
        continue_button_inactive = new Texture(Gdx.files.internal("ContinueUnselected.png"));
    }

    public void drawUI(Batch batch) {
        batch.begin();
            //Add the actual times for each boat
            background_sprite.draw(batch);
            first_place.draw(batch, "Team 1                                                             "+"Team1time", 450, 500);
            second_place.draw(batch, "Team 2                                                             "+"Team2time", 450, 450);
            third_place.draw(batch, "Team 3                                                             "+"Team3time", 450, 400);
            fourth_place.draw(batch, "Team 4                                                          "+"Team4time", 450, 350);
        batch.end();

    }

    public void getInput(Mouse mouse_pos) {
        if mouse_pos.x >
    }

    public void dispose() {
        first_place.dispose();
        second_place.dispose();
        third_place.dispose();
        fourth_place.dispose();
        background.dispose();
    }
}
