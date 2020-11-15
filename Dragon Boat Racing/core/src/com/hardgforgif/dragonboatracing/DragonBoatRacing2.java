package com.hardgforgif.dragonboatracing;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

public class DragonBoatRacing2 extends ApplicationAdapter implements InputProcessor {
	Player player;
	AI[] opponents = new AI[3];
	Map map;
	Batch batch;
	OrthographicCamera camera;
	World world;

	private final float METERS_TO_PIXELS = 100f;
	private float TILES_TO_METERS;
	private float PIXELS_TO_TILES;

	public Vector2 mousePos = new Vector2();
	public Vector2 clickPos = new Vector2();
	private ShapeRenderer shapeRenderer;
	Matrix4 debugMatrix;
//	Box2DDebugRenderer debugRenderer;

	UI gameUI;

	@Override
	public void create() {
		shapeRenderer = new ShapeRenderer();
		// Initialize the sprite batch
		batch = new SpriteBatch();

		// Initialize the physics gameWorld
		world = new World(new Vector2(0f, 0f), true);

		// Get the values of the screen dimensions
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		// Initialize the map
		map = new Map("Map1/Map1.tmx", w);
		map.createMapCollisions("CollisionLayerLeft", METERS_TO_PIXELS, world);
		map.createMapCollisions("CollisionLayerRight", METERS_TO_PIXELS, world);


		// Calculate the ratio between pixels, meters and tiles
		TILES_TO_METERS = map.getTilesToMetersRatio(METERS_TO_PIXELS);
		PIXELS_TO_TILES = 1/(METERS_TO_PIXELS * TILES_TO_METERS);

		map.createLanes(world, METERS_TO_PIXELS, PIXELS_TO_TILES);


		// Initialize the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);


		// Create the player boat
		player = new Player(100, 100, 100, 100f, "Boat1.png", camera, map.lanes[0]);
		player.createBoatBody(world, 2.3f, 4f, "Boat1.json", METERS_TO_PIXELS);

		// Create the AI boat
		opponents[0] = new AI(100, 100, 100, 100f, "Boat1.png", camera, map.lanes[1]);
		opponents[0].createBoatBody(world, 4f, 4f, "Boat1.json", METERS_TO_PIXELS);

		Gdx.input.setInputProcessor(this);
		createContactListener();
//		debugRenderer = new Box2DDebugRenderer();
	}

	private final ArrayList<Body> toBeRemovedBodies = new ArrayList<>();

	private void createContactListener(){
		world.setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				Fixture fixtureA = contact.getFixtureA();
				Fixture fixtureB = contact.getFixtureB();
				if (fixtureA.getBody().getUserData() instanceof Obstacle) {
					toBeRemovedBodies.add(fixtureA.getBody());
				} else if (fixtureA.getBody().getUserData() instanceof Obstacle) {
					toBeRemovedBodies.add(fixtureB.getBody());
				}
			}

			@Override
			public void endContact(Contact contact) {

			}

			@Override
			public void preSolve(Contact contact, Manifold manifold) {

			}

			@Override
			public void postSolve(Contact contact, ContactImpulse contactImpulse) {

			}
		});
	}

	private void updateCamera(Player player) {
		camera.position.set(camera.position.x, player.boatSprite.getY() + 600, 0);
		camera.update();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (GameData.mainMenu){
			gameUI = new MenuUI();

			gameUI.drawUI(batch, mousePos, Gdx.graphics.getWidth(), Gdx.graphics.getDeltaTime());
			gameUI.getInput(Gdx.graphics.getWidth(), clickPos);
		}

		else if(GameData.gamePlay){
			for (Body body : toBeRemovedBodies){
				for (Lane lane : map.lanes)
					for (Obstacle obstacle : lane.obstacles)
						if (obstacle.obstacleBody == body) {
							obstacle.obstacleBody = null;
						}
				world.destroyBody(body);
			}
			toBeRemovedBodies.clear();
//			gameUI = new RaceUI();
//			gameUI.drawUI(batch);
			// Advance the game world physics
			world.step(1f/60f, 6, 2);


			player.updatePlayer(mousePos, METERS_TO_PIXELS);
//		System.out.println(player.boatSprite.getRotation());
			opponents[0].updateAI(METERS_TO_PIXELS);

			batch.setProjectionMatrix(camera.combined);
			map.renderMap(camera);
			player.drawBoat(batch);
			opponents[0].drawBoat(batch);

			for (Lane lane : map.lanes)
				for (Obstacle obstacle : lane.obstacles){
					if (obstacle.obstacleBody != null)
						obstacle.drawObstacle(batch, METERS_TO_PIXELS);
				}
//			System.out.println(player.boatSprite.getX());

//		shapeRenderer.setProjectionMatrix(camera.combined);
//		shapeRenderer.setColor(Color.BLACK);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//		shapeRenderer.circle(opponents[0].laneChecker.x, opponents[0].laneChecker.y, 5);
//		shapeRenderer.end();
//
//        shapeRenderer.circle(player.leftLimit, player.boatSprite.getY() + player.boatSprite.getHeight() / 2, 5);
//		shapeRenderer.circle(player.rightLimit, player.boatSprite.getY() + player.boatSprite.getHeight() / 2, 5);
//        shapeRenderer.end();

			updateCamera(player);

//			debugMatrix = batch.getProjectionMatrix().cpy().scale(METERS_TO_PIXELS, METERS_TO_PIXELS, 0);

//			debugRenderer.render(world, debugMatrix);
		}



	}

	public void dispose() {
		world.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 position = camera.unproject(new Vector3(screenX, screenY, 0));
		clickPos.set(position.x, position.y);
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		Vector3 position = camera.unproject(new Vector3(screenX, screenY, 0));
		mousePos.set(position.x, position.y);
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
