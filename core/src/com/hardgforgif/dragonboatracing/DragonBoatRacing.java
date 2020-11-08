package com.hardgforgif.dragonboatracing;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hardgforgif.dragonboatracing.screens.MainMenuScreen;
import com.hardgforgif.dragonboatracing.tools.ScrollingBackground;

public class DragonBoatRacing extends Game {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	public SpriteBatch batch;
	public ScrollingBackground scrollingBackground;

	@Override
	public void create () {
		batch = new SpriteBatch();
		this.scrollingBackground = new ScrollingBackground();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		this.scrollingBackground.resize(width, height);
		super.resize(width, height);
	}
}
