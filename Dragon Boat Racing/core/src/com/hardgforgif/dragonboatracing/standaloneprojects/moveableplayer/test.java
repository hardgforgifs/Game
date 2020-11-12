package com.hardgforgif.dragonboatracing.standaloneprojects.moveableplayer;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;

public class test extends ApplicationAdapter implements InputProcessor {
    SpriteBatch batch;
    Sprite playerSprite;
    Texture img;
    World world;
    Body playerBody;
    Body bodyEdgeScreen;
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;
    OrthographicCamera camera;
    BitmapFont font;
    private ShapeRenderer shapeRenderer;
    private Vector2 mousePos = new Vector2();
    private Vector2 playerPos = new Vector2();
    private Vector2 directionVector = new Vector2();
    private Vector2 velocity = new Vector2();
    private Vector2 movement = new Vector2();
    float torque = 0.0f;
    boolean drawSprite = true;
    float turningspeed = 2f;
    float speed = 10f;
    final float METERS_TO_PIXELS = 100f;
    float TILES_TO_METERS;
    float PIXELS_TO_TILES;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    float timer;
    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        timer = 5f;
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        tiledMap = new TmxMapLoader().load("Trying.tmx");


        MapProperties prop = tiledMap.getProperties();

        int mapWidth = prop.get("width", Integer.class);

        TILES_TO_METERS = (w / METERS_TO_PIXELS) / mapWidth;

        PIXELS_TO_TILES = 1/(METERS_TO_PIXELS * TILES_TO_METERS);

        float unitScale = w / mapWidth / 32f;
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        world = new World(new Vector2(0, 0f),true);
        createMapCollisions(unitScale);

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        img = new Texture("arrow2.png");
        playerSprite = new Sprite(img);
        playerSprite.scale(-0.8f);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(32 * TILES_TO_METERS, 35 * TILES_TO_METERS);
        playerBody = world.createBody(bodyDef);

        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("arrow.json"));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0f;
        fixtureDef.restitution = 0f;
        fixtureDef.friction = 0f;

        float scale = playerSprite.getWidth() / METERS_TO_PIXELS * playerSprite.getScaleX();
        loader.attachFixture(playerBody, "Name", fixtureDef, scale);

//        w = Gdx.graphics.getWidth()/ METERS_TO_PIXELS - 50/ METERS_TO_PIXELS;
//        // Set the height to just 50 pixels above the bottom of the screen so we can see the edge in the
//        // debug renderer
//        h = Gdx.graphics.getHeight()/ METERS_TO_PIXELS - 50/ METERS_TO_PIXELS;
//
//        createMapEdge(50/ METERS_TO_PIXELS, h, w, h);
//        createMapEdge(50/ METERS_TO_PIXELS, h, 50/ METERS_TO_PIXELS, 50/ METERS_TO_PIXELS);
//        createMapEdge(50/ METERS_TO_PIXELS, 50/ METERS_TO_PIXELS, w, 50/ METERS_TO_PIXELS);
//        createMapEdge(w, 50/ METERS_TO_PIXELS, w, h);


        debugRenderer = new Box2DDebugRenderer();
//        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.
//                getHeight());
    }

    Rectangle[] rectangles = new Rectangle[100];
    private void createMapCollisions(float unitScale) {
        int objectLayerId = 2;
        MapLayer collisionLayer = tiledMap.getLayers().get("CollisionLayer");
        MapObjects objects = collisionLayer.getObjects();
        int k = 0;
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX() * unitScale / METERS_TO_PIXELS) + (rectangle.getWidth() * unitScale / METERS_TO_PIXELS / 2), (rectangle.getY() * unitScale / METERS_TO_PIXELS) + (rectangle.getHeight() * unitScale / METERS_TO_PIXELS / 2));
            //System.out.println("rectangle! at position" + bodyDef.position.toString());
            Body objectBody = world.createBody(bodyDef);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(rectangle.getWidth() * unitScale / METERS_TO_PIXELS / 2, rectangle.getHeight() * unitScale / METERS_TO_PIXELS / 2);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 0f;
            fixtureDef.restitution = 0f;
            fixtureDef.friction = 0f;
            Fixture fixture = objectBody.createFixture(fixtureDef);

            rectangles[k] = new Rectangle();
            rectangles[k].x = rectangle.getX() * unitScale;
            rectangles[k].y = rectangle.getY() * unitScale;
            rectangles[k].width = rectangle.getWidth() * unitScale;
            rectangles[k].height = rectangle.getHeight() * unitScale;
            k++;


            shape.dispose();
        }
    }

    private void rotatePlayer(float mouseAngle){
        // getOrigin return the position of the center of the image, relative to the bottom left corner
        // in order to make the player rotate around it's center we need to find the coordinates of the image center


        // Calculate the angle between the player and the mouse
        float angleDifference = mouseAngle - playerBody.getAngle() * MathUtils.radDeg;


        // If the angle is really small, set the player to face the mouse
        if (Math.abs(angleDifference) < turningspeed)
            playerBody.setTransform(playerBody.getPosition(), mouseAngle * MathUtils.degRad);

        else {
            // create the new angle we want the player to be rotated to every frame, based on the turning speed
            float newAngle = playerSprite.getRotation();
            // we need to manage turning rotation based on the difference in angles

            // if the difference is smaller than -180, it's faster to rotate clockwise
            if (angleDifference < -180)
                newAngle += turningspeed;
                // if the difference is greater than 180, it's faster to rotate counter-clockwise
            else if (angleDifference > 180)
                newAngle += turningspeed * (-1);
                // otherwise rotate in the direction given by the sign of angleDifference
            else
                newAngle += turningspeed * (angleDifference / Math.abs(angleDifference));

            // we want to keep the player rotation between 0 and 360 at all times
            if(newAngle < 0)
            {
                newAngle = 360 + newAngle;
            }
            if(newAngle > 360)
            {
                newAngle = newAngle - 360;
            }
            playerBody.setTransform(playerBody.getPosition(), newAngle * MathUtils.degRad);
        }
    }

    private void movePlayer(Vector2 playerHeadPos) {
        Vector2 directionPosition = new Vector2();
        double auxAngle = playerSprite.getRotation() % 90;
        if (playerSprite.getRotation() < 90 || playerSprite.getRotation() >= 180 && playerSprite.getRotation() < 270)
            auxAngle = 90 - auxAngle;
        auxAngle = auxAngle * MathUtils.degRad;
        float x = (float) (Math.cos(auxAngle) * speed);
        float y = (float) (Math.sin(auxAngle) * speed);

        if (playerSprite.getRotation() < 90)
            directionPosition.set(playerHeadPos.x - x, playerHeadPos.y + y);
        else if (playerSprite.getRotation() < 180)
            directionPosition.set(playerHeadPos.x - x, playerHeadPos.y - y);
        else if (playerSprite.getRotation() < 270)
            directionPosition.set(playerHeadPos.x + x, playerHeadPos.y - y);
        else
            directionPosition.set(playerHeadPos.x + x, playerHeadPos.y + y);

        playerPos.set(playerSprite.getX(), playerSprite.getY());
        directionVector.set(directionPosition).sub(playerHeadPos).nor();
        velocity.set(directionVector).scl(speed);
        movement.set(velocity).scl(Gdx.graphics.getDeltaTime());
        if (playerHeadPos.dst2(mousePos) > movement.len2() / METERS_TO_PIXELS) {
            playerBody.setLinearVelocity(movement);
        } else {
            playerBody.setLinearVelocity(0, 0);
        }
    }

    private void updateCamera(Sprite player) {

        camera.position.set(player.getX() + 30,player.getY() + 20, 0);
        camera.update();
    }
    @Override
    public void render() {
        timer += Gdx.graphics.getDeltaTime();
        if (timer > 5f) {
            camera.zoom = 0.15f;
            // Step the physics simulation forward at a rate of 60hz
            world.step(1f/60f, 6, 2);
            //body.applyTorque(torque,true);
            float originX = playerBody.getPosition().x * METERS_TO_PIXELS;
            float originY = playerBody.getPosition().y * METERS_TO_PIXELS;

            float mouseAngle = MathUtils.atan2(mousePos.y - originY, mousePos.x - originX) * MathUtils.radDeg - 90;
            if(mouseAngle < 0)
            {
                mouseAngle = 360 + mouseAngle;
            }
            rotatePlayer(mouseAngle);
            //playerSprite.setOrigin(playerSprite.getWidth() / 2, playerSprite.getHeight() / 2);
            playerSprite.setPosition((playerBody.getPosition().x * METERS_TO_PIXELS) - playerSprite.getWidth() / 2,
                    (playerBody.getPosition().y * METERS_TO_PIXELS) - playerSprite.getHeight() / 2);
//            System.out.println(playerSprite.getX());
//            System.out.println(playerSprite.getY());
            playerSprite.setRotation((float)Math.toDegrees(playerBody.getAngle()));
            updateCamera(playerSprite);

            Vector2 playerHeadPos = new Vector2();
            float radius = playerSprite.getHeight()/2;
            playerHeadPos.set(originX + radius * MathUtils.cosDeg(mouseAngle + 90),
                    originY + radius * MathUtils.sinDeg(mouseAngle + 90));

            movePlayer(playerHeadPos);


            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



            batch.setProjectionMatrix(camera.combined);
            shapeRenderer.setProjectionMatrix(camera.combined);
            tiledMapRenderer.setView(camera);
            tiledMapRenderer.render();


            batch.begin();
            if(drawSprite)
                batch.draw(playerSprite, playerSprite.getX(), playerSprite.getY(), playerSprite.getOriginX(),
                        playerSprite.getOriginY(),
                        playerSprite.getWidth(), playerSprite.getHeight(), playerSprite.getScaleX(), playerSprite.
                                getScaleY(), playerSprite.getRotation());
            batch.end();

//            for (Rectangle rectangle : rectangles) {
//                if (rectangle == null)
//                    break;
//                System.out.println(rectangle.x);
//                System.out.println(rectangle.y);
//                System.out.println(rectangle.width);
//                System.out.println(rectangle.height);
//                shapeRenderer.setColor(Color.BLUE);
//                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//                shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
//                shapeRenderer.end();
//            }




//            debugMatrix = batch.getProjectionMatrix().cpy().scale(METERS_TO_PIXELS,
//                    METERS_TO_PIXELS, 0);
//
//            debugRenderer.render(world, debugMatrix);
//        System.out.print(mousePos.x);
//        System.out.print(" ");
//        System.out.println(mousePos.y);

//        shapeRenderer.setColor(Color.BLUE);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.circle(playerHeadPos.x, playerHeadPos.y, 5);
//        shapeRenderer.end();
        }
        else {
            tiledMapRenderer.setView(camera);
            tiledMapRenderer.render();
        }

    }
    @Override
    public void dispose() {
        img.dispose();
        world.dispose();
    }
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }
    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.RIGHT)
            camera.translate(20f,0f);
        if(keycode == Input.Keys.LEFT)
            camera.translate(-20f,0f);
        if(keycode == Input.Keys.UP)
            camera.translate(0f,20f);
        if(keycode == Input.Keys.DOWN)
            camera.translate(0f,-20f);
        return false;
    }
    @Override
    public boolean keyTyped(char character) {
        return false;
    }
    // On touch we apply force from the direction of the users touch.
    // This could result in the object "spinning"
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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