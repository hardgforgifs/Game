package com.hardgforgif.dragonboatracing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.hardgforgif.dragonboatracing.tools.ScrollingBackground;

import java.util.ArrayList;
import java.util.Collections;

public class ChoosingUI extends UI{
    private Texture background;
    private Sprite background_sprite;

    private Texture bar;
    private Sprite[] bar_sprites = new Sprite[4];
    public static float[][] boatsStatsBarSizes = new float[][] {{100, 100, 100, 100}, {70, 100, 120, 80},   //robustness, speed, acceleration, maneuverability
                                            {110, 110, 90, 90}, {100, 90, 90, 130}};

    private Texture boatTexture;
    private Sprite[] boat_sprites = new Sprite[4];

    private BitmapFont label;

    private float[] current_stats = new float[4];

    ScrollingBackground scrollingBackground = new ScrollingBackground();


    public ChoosingUI() {
        scrollingBackground.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        scrollingBackground.setSpeedFixed(true);
        scrollingBackground.setSpeed(ScrollingBackground.DEFAULT_SPEED);

        background = new Texture(Gdx.files.internal("Background.png"));
        background_sprite = new Sprite(background);
        background_sprite.setPosition(200,410);
        background_sprite.setSize(900,260);


        label = new BitmapFont();
        label.getData().setScale(1.4f);
        label.setColor(Color.BLACK);



        bar = new Texture("Robustness_bar.png");
        bar_sprites[0] = new Sprite(bar);
        bar_sprites[0].setPosition(430,600);

        bar = new Texture("Speed_bar.png");
        bar_sprites[1] = new Sprite(bar);
        bar_sprites[1].setPosition(430,550);

        bar = new Texture("Acceleration_bar.png");
        bar_sprites[2] = new Sprite(bar);
        bar_sprites[2].setPosition(430,500);

        bar = new Texture("Maneuverability_bar.png");
        bar_sprites[3] = new Sprite(bar);
        bar_sprites[3].setPosition(430,450);

        for (int i = 1; i <= 4; i++){
            boatTexture = new Texture("Boat" + i + ".png");
            boat_sprites[i - 1] = new Sprite(boatTexture);
            boat_sprites[i - 1].scale(-0.6f);
            boat_sprites[i - 1].setPosition(150 + 300f * (i - 1), -200f);
        }
    }

    @Override
    public void drawUI(Batch batch, Vector2 mousePos, float screenWidth, float delta) {
        //Checks if the mouse is hovering over a boat and updates the bars displayed as appropriate
        for (int i = 0; i < 4; i++){
            float boatX = boat_sprites[i].getX() + boat_sprites[i].getWidth() / 2 -
                    boat_sprites[i].getWidth() / 2 * boat_sprites[i].getScaleX();
            float boatY = boat_sprites[i].getY() + boat_sprites[i].getHeight() / 2 -
                    boat_sprites[i].getHeight() / 2 * boat_sprites[i].getScaleY();
            float boatWidth = boat_sprites[i].getWidth() * boat_sprites[i].getScaleX();
            float boatHeight = boat_sprites[i].getHeight() * boat_sprites[i].getScaleY();

            if (mousePos.x > boatX && mousePos.x < boatX + boatWidth &&
                    mousePos.y > boatY && mousePos.y < boatY + boatHeight){
                current_stats[0] = GameData.boatsStats[i][0];
                current_stats[1] = GameData.boatsStats[i][1];
                current_stats[2] = GameData.boatsStats[i][2];
                current_stats[3] = GameData.boatsStats[i][3];
            }
        }

        float full_bar = screenWidth / 3;
        batch.begin();
        scrollingBackground.updateAndRender(delta, batch);
        background_sprite.draw(batch);

        for (int i = 0; i < 4; i++){
            bar_sprites[i].setSize(full_bar * (current_stats[i]/100), 30);
            bar_sprites[i].draw(batch);
        }

        label.draw(batch, "Robustness:", 260, 625);
        label.draw(batch, "Speed:", 260, 575);
        label.draw(batch, "Acceleration:", 260, 525);
        label.draw(batch, "Maneuverability:", 260, 475);

        for (int i = 0; i < 4; i++){
            batch.draw(boat_sprites[i], boat_sprites[i].getX(), boat_sprites[i].getY(), boat_sprites[i].getOriginX(),
                    boat_sprites[i].getOriginY(),
                    boat_sprites[i].getWidth(), boat_sprites[i].getHeight(), boat_sprites[i].getScaleX(),
                    boat_sprites[i].getScaleY(), boat_sprites[i].getRotation());
        }
        batch.end();

        playMusic();
    }

    @Override
    public void getInput(float screenWidth, Vector2 mousePos) {
        for (int i = 0; i < 4; i++){
            float boatX = boat_sprites[i].getX() + boat_sprites[i].getWidth() / 2 -
                    boat_sprites[i].getWidth() / 2 * boat_sprites[i].getScaleX();
            float boatY = boat_sprites[i].getY() + boat_sprites[i].getHeight() / 2 -
                    boat_sprites[i].getHeight() / 2 * boat_sprites[i].getScaleY();
            float boatWidth = boat_sprites[i].getWidth() * boat_sprites[i].getScaleX();
            float boatHeight = boat_sprites[i].getHeight() * boat_sprites[i].getScaleY();

            if (mousePos.x > boatX && mousePos.x < boatX + boatWidth &&
                    mousePos.y > boatY && mousePos.y < boatY + boatHeight){
                GameData.boatTypes[0] = i;

                ArrayList<Integer> intList = new ArrayList<Integer>(){{add(0); add(1); add(2); add(3);}};
                intList.remove(new Integer(i));
                Collections.shuffle(intList);

                GameData.boatTypes[1] = intList.get(0);
                GameData.boatTypes[2] = intList.get(1);
                GameData.boatTypes[3] = intList.get(2);

                GameData.music.stop();
                GameData.music = Gdx.audio.newMusic(Gdx.files.internal("Love_Drama.ogg"));

                GameData.choosingBoat = false;
                GameData.gamePlay = true;
                GameData.currentUI = new GamePlayUI();
            }
        }
    }

    @Override
    public void drawPlayerUI(Batch batch, Player playerBoat) {

    }


//    public void dispose() {
//        label.dispose();
//        boat.dispose();
//        background.dispose();
//        bar.dispose();
//        background.dispose();
//    }
}
