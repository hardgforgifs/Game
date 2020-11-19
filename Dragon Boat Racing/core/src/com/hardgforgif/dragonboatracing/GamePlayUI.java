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
    private BitmapFont timer_label;
    private Texture stamina;
    private Texture robustness;
    private Sprite r_bar;
    private Sprite s_bar;

    public GamePlayUI() {
        position_label = new BitmapFont();
        robustness_label = new BitmapFont();
        stamina_label = new BitmapFont();
        timer_label = new BitmapFont();
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
        s_bar.setSize(playerBoat.stamina, 30);
        r_bar.setSize(playerBoat.robustness,30);

        batch.begin();
        s_bar.draw(batch);
        r_bar.draw(batch);
        robustness_label.draw(batch, "Robustness", 10, 110);
        stamina_label.draw(batch, "Stamina", 10,170);

        position_label.draw(batch, GameData.standings[0] + "/4", 1225, 700);
        timer_label.draw(batch, String.valueOf(Math.round(GameData.currentTimer * 10.0) / 10.0), 10, 700);
        batch.end();

        playMusic();
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
