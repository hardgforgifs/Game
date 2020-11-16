package com.hardgforgif.dragonboatracing.NewUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ChoosingUI {
    private Texture background;
    private Sprite background_sprite;

    public ChoosingUI() {
        background = new Texture(Gdx.files.internal("Choosing_background.png"));
        background_sprite = new Sprite(background);
        background_sprite.setPosition(250,200);

    }
}
