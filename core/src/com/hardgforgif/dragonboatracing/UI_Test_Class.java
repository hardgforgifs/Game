package com.hardgforgif.dragonboatracing;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hardgforgif.dragonboatracing.screens.MainMenuScreen;
import com.hardgforgif.dragonboatracing.tools.GameCamera;
import com.hardgforgif.dragonboatracing.tools.ScrollingBackground;
import com.badlogic.gdx.audio.Music;
import com.hardgforgif.dragonboatracing.NewUI.ChoosingUI;

public class UI_Test_Class extends ApplicationAdapter {

    public SpriteBatch batch;
    public Music current_music;
    private ChoosingUI ui_test;

    @Override
    public void create () {
        batch = new SpriteBatch();
        ui_test = new ChoosingUI();
    }

    @Override
    public void render () {
        ui_test.drawUI();
    }

    @Override
    public void resize(int width, int height) {

    }
}
