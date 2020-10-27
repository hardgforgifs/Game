package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite implements InputProcessor{
	
	private Vector2 velocity = new Vector2();
	private float speed = 60 * 2;
	
	Sound wavSound = Gdx.audio.newSound(Gdx.files.internal("laugh.wav")); // this is just my "debugger" to make sure something works or doesn't lmao	
	
	private TiledMapTileLayer collisionLayer;				//The layer in which you have the things that will collide. If you only have 1 layer, put 0
	
	private String blockedKey = "blocked";
	
	
	
	public Player(Sprite sprite, TiledMapTileLayer collisionLayer) {
		super(sprite);
		this.collisionLayer = collisionLayer;
	}
	
	
	public void draw(SpriteBatch spriteBatch) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);		
	}


	public void update(float delta) {
		
		//Saving old position
		float oldX = getX(), oldY = getY();
		boolean collisionX = false, collisionY = false;
		
		
		setX(getX() + velocity.x * delta);  // It's this line and the setY one that aren't working. Takes the input, but doesn't run the update
		
		//Collision Check
		if(velocity.x < 0) // going left
			collisionX = collidesLeft();
		else if(velocity.x > 0) // going right
			collisionX = collidesRight();
		
		//react to x collision
		if(collisionX) { 
			setX(oldX);
			velocity.x = 0;
		}
		
		setY(getY() + velocity.y * delta); // It's this line and the setX one that aren't working. Takes the input, but doesn't run the update
		
		//Collision check
		if(velocity.y < 0) // going down
			collisionY = collidesBottom();
		else if(velocity.y > 0) // going up
			collisionY = collidesTop();
		
		//react to y collision
		if(collisionY) {
			setY(oldY);
			velocity.y = 0;
		}
		
	}
	
	
	private boolean isCellBlocked(float x, float y) {
		Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(blockedKey);
	}
	
	public boolean collidesRight() {
	    for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
	        if(isCellBlocked(getX() + getWidth(), getY() + step))
	            return true;
	    return false;
	}
	 
	public boolean collidesLeft() {
	    for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
	        if(isCellBlocked(getX(), getY() + step))
	            return true;
	    return false;
	}
	 
	public boolean collidesTop() {
	    for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
	        if(isCellBlocked(getX() + step, getY() + getHeight()))
	            return true;
	    return false;
	}
	 
	public boolean collidesBottom() {
	    for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
	        if(isCellBlocked(getX() + step, getY()))
	            return true;
	    return false;
	}


	public Vector2 getVelocity() {
		return velocity;
	}


	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}


	public float getSpeed() {
		return speed;
	}


	public void setSpeed(float speed) {
		this.speed = speed;
	}


	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}


	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}


	public String getBlockedKey() {
		return blockedKey;
	}


	public void setBlockedKey(String blockedKey) {
		this.blockedKey = blockedKey;
	}


	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.Z) {
			velocity.y = speed;
			wavSound.play();
		}
		if (keycode == Keys.Q) {
			velocity.x = -speed;
		}
		if (keycode == Keys.D) {
			velocity.x = speed;
		}

		return true;
		
	}


	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
		case Keys.Z:
			velocity.y = 0;
		case Keys.Q:
		case Keys.D:
			velocity.x = 0;
		}
		return true;
	}


	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
