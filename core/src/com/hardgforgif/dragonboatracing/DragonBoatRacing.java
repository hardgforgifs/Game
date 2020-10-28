package com.hardgforgif.dragonboatracing;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DragonBoatRacing extends ApplicationAdapter {

	private SpriteBatch batch;
	private BitmapFont position_font;
	private BitmapFont robustness_font;
	private BitmapFont stamina_font;
	private Texture stamina;
	private Texture robustness;
	private Sprite r_bar;
	private Sprite s_bar;

	private int frame = 0;
	private int position = 1;


	@Override
	public void create () {
		batch = new SpriteBatch();
		position_font = new BitmapFont();
		robustness_font = new BitmapFont();
		robustness = new Texture(Gdx.files.internal("Robustness_bar.png"));
		stamina_font = new BitmapFont();
		stamina = new Texture(Gdx.files.internal("Stamina_bar.png"));
		r_bar = new Sprite(robustness);
		s_bar = new Sprite(stamina);

	}


	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0.5f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		/*
		!Note: The following code produces a stand-in value, in the actual game, the position will be based on how well
		the player is doing, this just increments a value over time to test it's functionality

		Increases frame, when it reaches 150, meaning roughly 3 seconds have passed
		it is reset, and we change the position, which is also reset when it is above 8
		 */
		frame++;
		if (frame >= 150) {
			frame = 0;
			position++;
			if (position > 8) {
				position = 1;
			}
		}

		//Sets the position and size of elements
		//Note: The following line also uses a stand-in value
		r_bar.setSize(10 * position,30);
		r_bar.setPosition(10,10);
		//Note: The following line also uses a stand-in value
		s_bar.setSize(10 * position, 30);
		s_bar.setPosition(10,60);
		position_font.getData().setScale(1.5f,1.5f);


		String str_position = Integer.toString(position);

		//Draws the elements to the Spritebatch
		batch.begin();
		position_font.draw(batch, str_position + "/8", 590, 470);
		robustness_font.draw(batch, "Robustness", 10, 55);
		r_bar.draw(batch);
		s_bar.draw(batch);
		stamina_font.draw(batch, "Stamina", 10,105);
		batch.end();
	}


	@Override
	public void dispose () {
		batch.dispose();
		position_font.dispose();
		robustness.dispose();

	}
}
