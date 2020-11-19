package com.hardgforgif.dragonboatracing;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

public class DragonBoatRacing2 extends ApplicationAdapter implements InputProcessor {
	Player player;
	AI[] opponents = new AI[3];
	Map map;
	Batch batch;
	Batch UIbatch;
	OrthographicCamera camera;
	World world;


	public Vector2 mousePos = new Vector2();
	public Vector2 clickPos = new Vector2();
	private ShapeRenderer shapeRenderer;

	private boolean[] pressedKeys = new boolean[4]; // W, A, S, D buttons status
//	Matrix4 debugMatrix;

	private ArrayList<Body> toBeRemovedBodies = new ArrayList<>();
	private ArrayList<Body> toBeUpdatedHealthBoats = new ArrayList<>();
//	Box2DDebugRenderer debugRenderer;

	@Override
	public void create() {
		shapeRenderer = new ShapeRenderer();
		// Initialize the sprite batch
		batch = new SpriteBatch();
		UIbatch = new SpriteBatch();

		// Initialize the physics gameWorld
		world = new World(new Vector2(0f, 0f), true);

		// Get the values of the screen dimensions
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		// Initialize the map
		map = new Map("Map1/Map1.tmx", w);
		map.createMapCollisions("CollisionLayerLeft", GameData.METERS_TO_PIXELS, world);
		map.createMapCollisions("CollisionLayerRight", GameData.METERS_TO_PIXELS, world);


		// Calculate the ratio between pixels, meters and tiles
		GameData.TILES_TO_METERS = map.getTilesToMetersRatio(GameData.METERS_TO_PIXELS);
		GameData.PIXELS_TO_TILES = 1/(GameData.METERS_TO_PIXELS * GameData.TILES_TO_METERS);

		map.createLanes(world);
		map.createFinishLine("finishLine.png");



		// Initialize the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);



		Gdx.input.setInputProcessor(this);
		createContactListener();
//		debugRenderer = new Box2DDebugRenderer();
	}


	private void createContactListener(){
		world.setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				Fixture fixtureA = contact.getFixtureA();
				Fixture fixtureB = contact.getFixtureB();
				if (fixtureA.getBody().getUserData() instanceof Obstacle) {
					toBeRemovedBodies.add(fixtureA.getBody());
				} else if (fixtureB.getBody().getUserData() instanceof Obstacle) {
					toBeRemovedBodies.add(fixtureB.getBody());
				}

				if (fixtureA.getBody().getUserData() instanceof Boat) {
					toBeUpdatedHealthBoats.add(fixtureA.getBody());
				} else if (fixtureB.getBody().getUserData() instanceof Boat) {
					toBeUpdatedHealthBoats.add(fixtureB.getBody());
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

	private void updateStandings(){
		if(!player.hasFinished()){
			GameData.standings[0] = 1;
			for (Boat boat: opponents)
				if  (boat.boatSprite.getY() + boat.boatSprite.getHeight() / 2 > player.boatSprite.getY() + player.boatSprite.getHeight() / 2){
					GameData.standings[0]++;
				}

		}


		for (int i = 0; i < 3; i++)
			if(!opponents[i].hasFinished()){
				GameData.standings[i + 1] = 1;
				if (player.boatSprite.getY() > opponents[i].boatSprite.getY())
					GameData.standings[i + 1]++;
				for (int j = 0; j < 3; j++)
					if(opponents[j].boatSprite.getY() > opponents[i].boatSprite.getY())
						GameData.standings[i + 1]++;
			}

//		float[] boatsPosition = new float[4];
//		boatsPosition[0] = player.boatSprite.getY();
//		for (int i = 1; i < 4; i++){
//				boatsPosition[i] = opponents[i - 1].boatSprite.getY();
//		}
//
//		for (int i = 0; i < 4; i++){
//			for (int j = 0; j < 4; j++){
//				if(boatsPosition[i] < boatsPosition[j])
//					GameData.standings[i]++;
//			}
//		}
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (GameData.mainMenu){
			GameData.currentUI.drawUI(UIbatch, mousePos, Gdx.graphics.getWidth(), Gdx.graphics.getDeltaTime());
			GameData.currentUI.getInput(Gdx.graphics.getWidth(), clickPos);
		}

		else if (GameData.choosingBoat){
			GameData.currentUI.drawUI(UIbatch, mousePos, Gdx.graphics.getWidth(), Gdx.graphics.getDeltaTime());
			GameData.currentUI.getInput(Gdx.graphics.getWidth(), clickPos);
		}

		else if(GameData.gamePlay){
			if (player == null){
				// Create the player boat
				int playerBoatType = GameData.boatTypes[0];

				player = new Player(GameData.boatsStats[playerBoatType][0], GameData.boatsStats[playerBoatType][1],
						GameData.boatsStats[playerBoatType][2], GameData.boatsStats[playerBoatType][3],
						playerBoatType, map.lanes[0]);
				player.createBoatBody(world, GameData.startingPoints[0][0], GameData.startingPoints[0][1], "Boat1.json");

				// Create the AI boats
				for (int i = 1; i <= 3; i++){
					int AIBoatType = GameData.boatTypes[i];

					opponents[i - 1] = new AI(GameData.boatsStats[AIBoatType][0], GameData.boatsStats[AIBoatType][1],
							GameData.boatsStats[AIBoatType][2], GameData.boatsStats[AIBoatType][3],
							AIBoatType, camera, map.lanes[i]);
					opponents[i - 1].createBoatBody(world, GameData.startingPoints[i][0], GameData.startingPoints[i][1], "Boat1.json");
				}
			}

			for (Body body : toBeRemovedBodies){
				for (Lane lane : map.lanes)
					for (Obstacle obstacle : lane.obstacles)
						if (obstacle.obstacleBody == body) {
							obstacle.obstacleBody = null;
						}
				world.destroyBody(body);
			}
			for (Body body : toBeUpdatedHealthBoats){
				if (player.boatBody == body){
					player.robustness -= 10f;
					player.current_speed -= 30f;

					if(player.robustness < 0){
						world.destroyBody(player.boatBody);
						GameData.results.add(Float.MAX_VALUE);
						GameData.showResults = true;
						GameData.currentUI = new ResultsUI();
					}
				}


				for (Boat boat : opponents){
					if (boat.boatBody == body) {
						boat.robustness -= 10f;
						boat.current_speed -= 30f;
					}
					if(boat.robustness < 0){
						world.destroyBody(boat.boatBody);
						GameData.results.add(Float.MAX_VALUE);
					}
				}

			}

			toBeRemovedBodies.clear();
			toBeUpdatedHealthBoats.clear();

			// Advance the game world physics
			world.step(1f/60f, 6, 2);

			GameData.currentTimer += Gdx.graphics.getDeltaTime();
			player.updatePlayer(pressedKeys, Gdx.graphics.getDeltaTime());

//			System.out.println(player.boatSprite.getY());
			for (AI opponent : opponents)
				opponent.updateAI();



			batch.setProjectionMatrix(camera.combined);
			map.renderMap(camera, batch);
			player.drawBoat(batch);
			for (AI opponent : opponents)
				opponent.drawBoat(batch);
//			System.out.println(player.boatSprite.getY());
//			System.out.println(mousePos.y);
			shapeRenderer.setProjectionMatrix(camera.combined);

			for (Lane lane : map.lanes)
				for (Obstacle obstacle : lane.obstacles){
					if (obstacle.obstacleBody != null)
						obstacle.drawObstacle(batch);
				}

			updateStandings();

//			shapeRenderer.setColor(Color.BLACK);
//			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//			shapeRenderer.circle(opponents[0].leftLimit, opponents[0].boatSprite.getY() + opponents[0].boatSprite.getHeight() / 2, 5);
//			shapeRenderer.circle(opponents[0].rightLimit, opponents[0].boatSprite.getY() + opponents[0].boatSprite.getHeight() / 2, 5);
//			shapeRenderer.end();
			if(!GameData.showResults)
				GameData.currentUI.drawPlayerUI(UIbatch, player);
			else{
				GameData.currentUI.drawUI(UIbatch, mousePos, Gdx.graphics.getWidth(), Gdx.graphics.getDeltaTime());
				GameData.currentUI.getInput(Gdx.graphics.getWidth(), clickPos);
			}


			updateCamera(player);

//			debugMatrix = batch.getProjectionMatrix().cpy().scale(METERS_TO_PIXELS, METERS_TO_PIXELS, 0);

//			debugRenderer.render(world, debugMatrix);
		}
		else if(GameData.resetGame){
			if (GameData.currentLeg == 2)
				Gdx.app.exit();
			player = null;

			world = new World(new Vector2(0f, 0f), true);

			map = new Map("Map1/Map1.tmx", Gdx.graphics.getWidth());
			map.createMapCollisions("CollisionLayerLeft", GameData.METERS_TO_PIXELS, world);
			map.createMapCollisions("CollisionLayerRight", GameData.METERS_TO_PIXELS, world);


			// Calculate the ratio between pixels, meters and tiles
			GameData.TILES_TO_METERS = map.getTilesToMetersRatio(GameData.METERS_TO_PIXELS);
			GameData.PIXELS_TO_TILES = 1/(GameData.METERS_TO_PIXELS * GameData.TILES_TO_METERS);

			map.createLanes(world);
			map.createFinishLine("finishLine.png");

			createContactListener();

			GameData.resetGame = false;
			GameData.gamePlay = true;
		}
		if(clickPos.x != 0f && clickPos.y != 0f)
			clickPos.set(0f,0f);


	}

	public void dispose() {
		world.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.W)
			pressedKeys[0] = true;
		if (keycode == Input.Keys.A)
			pressedKeys[1] = true;
		if (keycode == Input.Keys.S)
			pressedKeys[2] = true;
		if (keycode == Input.Keys.D)
			pressedKeys[3] = true;
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.W)
			pressedKeys[0] = false;
		if (keycode == Input.Keys.A)
			pressedKeys[1] = false;
		if (keycode == Input.Keys.S)
			pressedKeys[2] = false;
		if (keycode == Input.Keys.D)
			pressedKeys[3] = false;
		return true;
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
