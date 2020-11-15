package com.hardgforgif.dragonboatracing;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public abstract class UI {

    public abstract void drawUI(Batch batch, Vector2 mousePos, float screenWidth, float delta);

    public abstract void getInput(float screenWidth, Vector2 mousePos);


}
