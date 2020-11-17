package com.hardgforgif.dragonboatracing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class GamePlayUI extends UI{
    private BitmapFont position_label;
    private BitmapFont robustness_label;
    private BitmapFont stamina_label;
    private Texture stamina;
    private Texture robustness;
    private Sprite r_bar;
    private Sprite s_bar;
    //Remove this
    private int player_position;

    public GamePlayUI() {
        position_label = new BitmapFont();
        robustness_label = new BitmapFont();
        stamina_label = new BitmapFont();
        stamina = new Texture(Gdx.files.internal("Stamina_bar.png"));
        robustness  = new Texture(Gdx.files.internal("Robustness_bar.png"));
        r_bar = new Sprite(robustness);
        s_bar = new Sprite(stamina);
        s_bar.setPosition(10 ,120);
        r_bar.setPosition(10,60);


        //Remove this
        player_position = 1;

    }

    @Override
    public void drawUI(Batch batch, Vector2 mousePos, float screenWidth, float delta) {

    }

    @Override
    public void drawUI(Batch batch, Player playerBoat, float delta) {
        s_bar.setSize(playerBoat.stamina, 30);
        r_bar.setSize(100,30);
        //Changes size of the bar based on the player's stats
        //These lines will include the actual functionality for the Game UI, replace the setSize statements above with these
        //r_bar.setSize(60 * (p_boat.robustness/players boats max robustness), 30);
        //s_bar.setSize(60 * (p_boat.stamina/players boats max stamina), 30);
        batch.begin();
        s_bar.draw(batch);
        r_bar.draw(batch);
        robustness_label.draw(batch, "Robustness", 10, 110);
        stamina_label.draw(batch, "Stamina", 10,170);
        //Replace player position with the player's position in the race
        position_label.draw(batch, player_position + "/8", 1225, 700);
        batch.end();
    }

    public void dispose() {
        position_label.dispose();
        robustness_label.dispose();
        stamina_label.dispose();
        stamina.dispose();
        robustness.dispose();
    }



    @Override
    public void getInput(float screenWidth, Vector2 mousePos) {

    }
}
