package com.hardgforgif.dragonboatracing.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.hardgforgif.dragonboatracing.GameData;
import com.hardgforgif.dragonboatracing.core.Player;

public class GamePlayUI extends UI{
    private BitmapFont position_label;
    private BitmapFont robustness_label;
    private BitmapFont stamina_label;
    private BitmapFont timer_label;
    private BitmapFont leg_label;
    private Texture stamina;
    private Texture robustness;
    private Sprite r_bar;
    private Sprite s_bar;

    public GamePlayUI() {
        position_label = new BitmapFont();
        position_label.getData().setScale(1.4f);
        position_label.setColor(Color.BLACK);

        robustness_label = new BitmapFont();
        stamina_label = new BitmapFont();

        timer_label = new BitmapFont();
        timer_label.getData().setScale(1.4f);
        timer_label.setColor(Color.BLACK);

        leg_label = new BitmapFont();
        leg_label.getData().setScale(1.4f);
        leg_label.setColor(Color.BLACK);

        stamina = new Texture(Gdx.files.internal("Stamina_bar.png"));
        robustness  = new Texture(Gdx.files.internal("Robustness_bar.png"));
        r_bar = new Sprite(robustness);
        s_bar = new Sprite(stamina);
        s_bar.setPosition(10 ,120);
        r_bar.setPosition(10,60);

    }

    @Override
    public void drawUI(Batch batch, Vector2 mousePos, float screenWidth, float delta) {

    }

    @Override
    public void drawPlayerUI(Batch batch, Player playerBoat) {
        // Set the robustness and stamina bars size based on the player boat
        s_bar.setSize(playerBoat.stamina, 30);
        r_bar.setSize(playerBoat.robustness,30);

        batch.begin();
        // Draw the robustness and stamina bars
        s_bar.draw(batch);
        r_bar.draw(batch);
        robustness_label.draw(batch, "Robustness", 10, 110);
        stamina_label.draw(batch, "Stamina", 10,170);

        // Draw the position label, the timer and the leg label
        position_label.draw(batch, GameData.standings[0] + "/4", 1225, 700);
        timer_label.draw(batch, String.valueOf(Math.round(GameData.currentTimer * 10.0) / 10.0), 10, 700);
        leg_label.draw(batch, "Leg: " + (GameData.currentLeg + 1), 10, 650);
        batch.end();

        playMusic();
    }
    @Override
    public void getInput(float screenWidth, Vector2 mousePos) {

    }
}
