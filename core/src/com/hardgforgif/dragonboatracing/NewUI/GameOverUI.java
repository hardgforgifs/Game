package com.hardgforgif.dragonboatracing.NewUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameOverUI extends UI{
    private Texture text;
    private Sprite text_sprite;

    public GameOverUI() {
        text = new Texture (Gdx.files.internal("GameOver.png"));
        text_sprite = new Sprite(text);
    }

    public void drawUI(Batch batch) {
        batch.begin();
            text_sprite.setSize(200,150);
            text_sprite.setPosition(250,200);
        batch.end();

        //Keeps music on repeat
        //Dont have the GameData class on my end, so remove comments below
        /*if (GameData.music.isPlaying() == false) {
            GameData.music.play();
        }*/
    }

    public void getInput() {

    }


    public void dispose()
    {
        text.dispose();
    }
}
