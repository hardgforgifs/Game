package com.hardgforgif.dragonboatracing.NewUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class gameStateUI{
    private BitmapFont position_label;
    private BitmapFont robustness_label;
    private BitmapFont stamina_label;
    private Texture stamina;
    private Texture robustness;
    private Sprite r_bar = new Sprite(robustness);
    private Sprite s_bar = new Sprite(stamina);
    //!!!
    private Texture number;
    private Sprite numberSprite;
    private BitmapFont countdown_no;

    //Remove this
    private int player_position;

    public gameStateUI() {
        position_label = new BitmapFont();
        robustness_label = new BitmapFont();
        stamina_label = new BitmapFont();
        stamina = new Texture(Gdx.files.internal("Stamina_bar.png"));
        robustness  = new Texture(Gdx.files.internal("Robustness_bar.png"));
        r_bar = new Sprite(robustness);
        s_bar = new Sprite(stamina);
        r_bar.setPosition(10 ,120);
        s_bar.setPosition(10,60);
        //!!!
        countdown_no = new BitmapFont();
        countdown_no.getData().setScale(3);
        //Remove this
        player_position = 1;

    }

    //Remove the comment here, I had to add it as I don't have the Player class
    public void drawUI(Batch batch/*, Player p_boat*/) {

        //!!!
        //Draws the countdown numbers to the screen in a decreasing manner (so 3,2,1)
        if (GameData.currentTimer < 1) {
            countdown_no.draw(batch,"3",250,250);
        } else if (GameData.currentTimer < 2) {
            countdown_no.draw(batch,"2",250,250);
        } else if (GameData.currentTimer < 3) {
            countdown_no.draw(batch,"1",250,250);
        } else {
            //Changes size of the bar based on the player's stats
            s_bar.setSize(30, 30);
            r_bar.setSize(30, 30);
            //These lines will include the actual functionality for the Game UI, replace the setSize statements above with these
            //r_bar.setSize(60 * (p_boat.robustness/players boats max robustness), 30);
            //s_bar.setSize(60 * (p_boat.stamina/players boats max stamina), 30);
            batch.begin();
            s_bar.draw(batch);
            r_bar.draw(batch);
            robustness_label.draw(batch, "Robustness", 10, 110);
            stamina_label.draw(batch, "Stamina", 10, 170);
            //Replace player position with the player's position in the race
            position_label.draw(batch, player_position + "/8", 1225, 700);
            //!!!
            //Subtract 3 so we sync up the timer displayed with the actual time spent racing
            // as the countdown is displayed for 3 seconds
            timer_label.draw(batch, String.valueOf((Math.round(GameData.currentTimer * 10.0) / 10.0)-3), 10, 700);
            batch.end();

            //!!! Additional closing bracket
        }
    }

    public void getInput() {

    }

    public void dispose() {
        position_label.dispose();
        robustness_label.dispose();
        stamina_label.dispose();
        stamina.dispose();
        robustness.dispose();
        //!!!
        number.dispose();
    }
}
