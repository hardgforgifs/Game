package com.hardgforgif.dragonboatracing.UI;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class OverallUI {
    SpriteBatch batch;

    //Call this in the create subroutine
    public abstract void create();

    //Call this in the render subroutine
    public abstract void display();

    //Call this in the dispose subroutine
    public abstract void dispose();
}
