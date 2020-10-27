package com.hardgforgif.dragonboatracing.standaloneprojects.moveableplayer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;

public class PhysicsPlayer implements ApplicationListener, InputProcessor {
    private SpriteBatch batch;
    private Texture texture;
    private Sprite playerSprite;

    private float posX, posY; //player starting coordinates

    private Vector2 playerPos = new Vector2();
    private Vector2 mousePos = new Vector2();
    private Vector2 directionVector = new Vector2();
    private Vector2 velocity = new Vector2(); // Vector after adding the speed to the direction vector
    private Vector2 movement = new Vector2(); // Final movement vector to modify player position
    float speed = 15000;
    float turningspeed = 2;

    // Box components
    private Sprite boxSprite;
    private float boxX, boxY; // box position

    // Physics components
    World world;
    Body playerBody;
    Body boxBody;
    Body bodyEdgeScreen;
    Box2DDebugRenderer debugRenderer;

    Matrix4 debugMatrix;
    OrthographicCamera camera;

    // For testing purposes
    private ShapeRenderer shapeRenderer;
    final float PIXELS_TO_METERS = 100f;
    @Override
    public void create() {
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);
        shapeRenderer = new ShapeRenderer();

        // Create the world physics, with no gravity
        world = new World(new Vector2(0f, 0), true);

        // Create the player
        // Create the sprite and position it in the center of the screen
        texture = new Texture("arrow.png");
        playerSprite = new Sprite(texture);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        posX = w/2 - playerSprite.getWidth()/2;
        posY = h/2 - playerSprite.getHeight()/2;

        // Create the playerBody definition, and then the playerBody at the sprite's position
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(posX, posY);
        playerBody = world.createBody(bodyDef);

        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("arrow.json"));

        // Create the FixtureDef
        FixtureDef fd = new FixtureDef();
        fd.density = 1f;
        fd.restitution = 0f;

        // Attach the fixture to the body
        float scale = playerSprite.getWidth();
        loader.attachFixture(playerBody, "Name", fd, scale);

        // Create the box
//        texture = new Texture("badlogic.jpg");
//        boxSprite = new Sprite(texture);
//
//        bodyDef.type = BodyDef.BodyType.StaticBody;
//        bodyDef.position.set(100, 200);
//        boxBody = world.createBody(bodyDef);
//        loader = new BodyEditorLoader(Gdx.files.internal("box.json"));
//        FixtureDef fd2 = new FixtureDef();
//        fd.density = 0.1f;
//        fd.restitution = 0f;
//        scale = boxSprite.getWidth();
//        loader.attachFixture(boxBody, "Name", fd2, scale);
//
//        // Match the playerSprite transforms with the playerBody transforms
//        boxSprite.setRotation(boxBody.getAngle() * MathUtils.radDeg);
//        //playerSprite.setOrigin(playerSprite.getWidth() / 2, playerSprite.getHeight() / 2);
//        boxSprite.setPosition(boxBody.getPosition().x, boxBody.getPosition().y);

        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.StaticBody;
        w = Gdx.graphics.getWidth()/PIXELS_TO_METERS;
        // Set the height to just 50 pixels above the bottom of the screen so we can see the edge in the
        // debug renderer
        h = Gdx.graphics.getHeight()/PIXELS_TO_METERS- 50/PIXELS_TO_METERS;
        bodyDef2.position.set(0,0);

        FixtureDef fixtureDef2 = new FixtureDef();
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(0,h,w,h);
        fixtureDef2.shape = edgeShape;
        bodyEdgeScreen = world.createBody(bodyDef2);
        bodyEdgeScreen.createFixture(fixtureDef2);
        edgeShape.dispose();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void resize(int width, int height) {

    }

    private void rotatePlayer(float mouseAngle){
        // getOrigin return the position of the center of the image, relative to the bottom left corner
        // in order to make the player rotate around it's center we need to find the coordinates of the image center


        // Calculate the angle between the player and the mouse
        float angleDifference = mouseAngle - playerSprite.getRotation();

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
        if (playerHeadPos.dst2(mousePos) > movement.len2() / 10000) {
            playerBody.setLinearVelocity(movement);
        } else {
            playerBody.setLinearVelocity(0, 0);
        }
    }

    @Override
    public void render() {
        camera.update();

        world.step(1f/60f, 6, 2);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float originX = playerSprite.getOriginX() + playerSprite.getX();
        float originY = playerSprite.getOriginY() + playerSprite.getY();

        // rotate the player
        // First we need to find the mouse angle compared to the center of the player
        float mouseAngle = MathUtils.atan2(mousePos.y - originY, mousePos.x - originX) * MathUtils.radDeg - 90;
        if(mouseAngle < 0)
        {
            mouseAngle = 360 + mouseAngle;
        }

        rotatePlayer(mouseAngle);


        // we then need to calculate the position of the player's head
        Vector2 playerHeadPos = new Vector2();
        float radius = playerSprite.getHeight()/2;
        playerHeadPos.set(originX + radius * MathUtils.cosDeg(mouseAngle + 90),
                originY + radius * MathUtils.sinDeg(mouseAngle + 90));

        // move the player
        movePlayer(playerHeadPos);

        // Match the playerSprite transforms with the playerBody transforms
        playerSprite.setRotation(playerBody.getAngle() * MathUtils.radDeg);
        //playerSprite.setOrigin(playerSprite.getWidth() / 2, playerSprite.getHeight() / 2);
        playerSprite.setPosition(playerBody.getPosition().x - playerSprite.getWidth() / 2,
                                 playerBody.getPosition().y - playerSprite.getHeight() / 2);
        System.out.println(playerSprite.getX());
        System.out.println(playerSprite.getY());

        debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS,
                PIXELS_TO_METERS, 0);
        batch.begin();
        playerSprite.draw(batch);
        //boxSprite.draw(batch);
        batch.end();


        debugRenderer.render(world, debugMatrix);

//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.circle(boxBody.getPosition().x, boxBody.getPosition().y, 5);
//        shapeRenderer.end();
//
//        shapeRenderer.setColor(Color.BLUE);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.circle(boxBody.getPosition().x + boxSprite.getWidth(), boxBody.getPosition().y, 5);
//        shapeRenderer.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

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
        return false;
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
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
