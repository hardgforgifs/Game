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
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
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
    float speed = 50f;
    final float PIXELS_TO_METERS = 100f;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;

    @Override
    public void create() {
        tiledMap = new TmxMapLoader().load("Trying.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        img = new Texture("arrow.png");
        playerSprite = new Sprite(img);
        world = new World(new Vector2(0, 0f),true);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(5, 1);
        playerBody = world.createBody(bodyDef);

        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("arrow.json"));
//        PolygonShape shape = new PolygonShape();
//        shape.setAsBox(playerSprite.getWidth()/2 / PIXELS_TO_METERS, playerSprite.getHeight()
//                /2 / PIXELS_TO_METERS);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0f;
        fixtureDef.restitution = 0f;
        fixtureDef.friction = 0f;

        float scale = playerSprite.getWidth() / PIXELS_TO_METERS;
        loader.attachFixture(playerBody, "Name", fixtureDef, scale);

        w = Gdx.graphics.getWidth()/PIXELS_TO_METERS - 50/PIXELS_TO_METERS;
        // Set the height to just 50 pixels above the bottom of the screen so we can see the edge in the
        // debug renderer
        h = Gdx.graphics.getHeight()/PIXELS_TO_METERS - 50/PIXELS_TO_METERS;

        createMapEdge(50/PIXELS_TO_METERS, h, w, h);
        createMapEdge(50/PIXELS_TO_METERS, h, 50/PIXELS_TO_METERS, 50/PIXELS_TO_METERS);
        createMapEdge(50/PIXELS_TO_METERS, 50/PIXELS_TO_METERS, w, 50/PIXELS_TO_METERS);
        createMapEdge(w, 50/PIXELS_TO_METERS, w, h);

        Gdx.input.setInputProcessor(this);
        debugRenderer = new Box2DDebugRenderer();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.
                getHeight());
    }

    private void createMapEdge(float x1, float y1, float x2, float y2) {
        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.StaticBody;

        //bodyDef2.position.set(0,
//                h-10/PIXELS_TO_METERS);
        bodyDef2.position.set(0,0);
        FixtureDef fixtureDef2 = new FixtureDef();
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(x1,y1,x2,y2);
        fixtureDef2.shape = edgeShape;
        bodyEdgeScreen = world.createBody(bodyDef2);
        bodyEdgeScreen.createFixture(fixtureDef2);
        edgeShape.dispose();
    }

    private float elapsed = 0;

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
//            playerSprite.setRotation(newAngle);
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
        if (playerHeadPos.dst2(mousePos) > movement.len2()) {
            playerBody.setLinearVelocity(movement);
        } else {
            playerBody.setLinearVelocity(0, 0);
        }
    }

    @Override
    public void render() {
        camera.update();
        // Step the physics simulation forward at a rate of 60hz
        world.step(1f/60f, 6, 2);
        //body.applyTorque(torque,true);
        float originX = playerBody.getPosition().x * PIXELS_TO_METERS;
        float originY = playerBody.getPosition().y * PIXELS_TO_METERS;

        float mouseAngle = MathUtils.atan2(mousePos.y - originY, mousePos.x - originX) * MathUtils.radDeg - 90;
        if(mouseAngle < 0)
        {
            mouseAngle = 360 + mouseAngle;
        }
        rotatePlayer(mouseAngle);
        //playerSprite.setOrigin(playerSprite.getWidth() / 2, playerSprite.getHeight() / 2);
        playerSprite.setPosition((playerBody.getPosition().x * PIXELS_TO_METERS) - playerSprite.getWidth() / 2,
                (playerBody.getPosition().y * PIXELS_TO_METERS) - playerSprite.getHeight() / 2);
        playerSprite.setRotation((float)Math.toDegrees(playerBody.getAngle()));

        Vector2 playerHeadPos = new Vector2();
        float radius = playerSprite.getHeight()/2;
        playerHeadPos.set(originX + radius * MathUtils.cosDeg(mouseAngle + 90),
                originY + radius * MathUtils.sinDeg(mouseAngle + 90));

        movePlayer(playerHeadPos);


        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS,
                PIXELS_TO_METERS, 0);
        batch.begin();
        if(drawSprite)
            batch.draw(playerSprite, playerSprite.getX(), playerSprite.getY(), playerSprite.getOriginX(),
                    playerSprite.getOriginY(),
                    playerSprite.getWidth(), playerSprite.getHeight(), playerSprite.getScaleX(), playerSprite.
                            getScaleY(), playerSprite.getRotation());
        font.draw(batch,
                "Restitution: " + playerBody.getFixtureList().first().getRestitution(),
                -Gdx.graphics.getWidth()/2,
                Gdx.graphics.getHeight()/2 );
        batch.end();
        debugRenderer.render(world, debugMatrix);

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

//        shapeRenderer.setColor(Color.BLUE);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.circle(playerHeadPos.x, playerHeadPos.y, 5);
//        shapeRenderer.end();
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
            playerBody.setLinearVelocity(1f, 0f);
        if(keycode == Input.Keys.LEFT)
            playerBody.setLinearVelocity(-1f,0f);
        if(keycode == Input.Keys.UP)
            playerBody.applyForceToCenter(0f,50f,true);
        if(keycode == Input.Keys.DOWN)
            playerBody.applyForceToCenter(0f, -10f, true);
        // On brackets ( [ ] ) apply torque, either clock or counterclockwise
        if(keycode == Input.Keys.RIGHT_BRACKET)
            torque += 0.1f;
        if(keycode == Input.Keys.LEFT_BRACKET)
            torque -= 0.1f;
        // Remove the torque using backslash /
        if(keycode == Input.Keys.BACKSLASH)
            torque = 0.0f;
        // If user hits spacebar, reset everything back to normal
        if(keycode == Input.Keys.SPACE|| keycode == Input.Keys.NUM_2) {
            playerBody.setLinearVelocity(0f, 0f);
            playerBody.setAngularVelocity(0f);
            torque = 0f;
            playerSprite.setPosition(0f,0f);
            playerBody.setTransform(0f,0f,0f);
        }
        if(keycode == Input.Keys.COMMA) {
            playerBody.getFixtureList().first().setRestitution(playerBody.getFixtureList().first().getRestitution()-0.1f);
        }
        if(keycode == Input.Keys.PERIOD) {
            playerBody.getFixtureList().first().setRestitution(playerBody.getFixtureList().first().getRestitution()+0.1f);
        }
        if(keycode == Input.Keys.ESCAPE || keycode == Input.Keys.NUM_1)
            drawSprite = !drawSprite;
        return true;
    }
    @Override
    public boolean keyTyped(char character) {
        return false;
    }
    // On touch we apply force from the direction of the users touch.
    // This could result in the object "spinning"
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        playerBody.applyForce(1f,1f,screenX,screenY,true);
        //body.applyTorque(0.4f,true);
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
        mousePos.set(screenX, Gdx.graphics.getHeight() - screenY);
        return false;
    }
    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}