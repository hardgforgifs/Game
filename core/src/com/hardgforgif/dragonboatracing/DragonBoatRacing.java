package com.hardgforgif.dragonboatracing;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hardgforgif.dragonboatracing.screens.MainMenuScreen;
import com.hardgforgif.dragonboatracing.tools.GameCamera;
import com.hardgforgif.dragonboatracing.tools.ScrollingBackground;
import com.badlogic.gdx.audio.Music;

public class DragonBoatRacing extends Game {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	public SpriteBatch batch;
	public ScrollingBackground scrollingBackground;
	public GameCamera cam;
	public Music current_music;

	@Override
	public void create () {
		batch = new SpriteBatch();
		cam = new GameCamera(WIDTH, HEIGHT);
		this.scrollingBackground = new ScrollingBackground();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		batch.setProjectionMatrix(cam.combined());
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		this.scrollingBackground.resize(width, height);
		cam.update(width, height);
		super.resize(width, height);
	}
}
