package com.hardgforgif.dragonboatracing.NewUI;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class UI {
    SpriteBatch batch;

    //Use this for button interaction on relevant screens
    public abstract void getInput();

    //Call this in the render subroutine to draw the UI components to the screen
    public abstract void drawUI();

    //public abstract void dispose(); ??
}
