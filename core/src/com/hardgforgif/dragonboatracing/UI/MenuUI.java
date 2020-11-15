package com.hardgforgif.dragonboatracing.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuUI extends OverallUI{
    private BitmapFont title;

    public void create() {
        batch = new SpriteBatch();
        title = new BitmapFont();
        //Set the colour and font of the title here if necessary
    }

    public void display() {
        //Borrowed this line from Rhys's work on github
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
            title.draw(batch, "Dragon Boat Racing", 200, 400);
            title.getData().setScale(3);
        batch.end();
    }


    public void dispose() {
        batch.dispose();
        title.dispose();
    }
}
