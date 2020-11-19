package com.hardgforgif.dragonboatracing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.hardgforgif.dragonboatracing.standaloneprojects.moveableplayer.BodyEditorLoader;

public abstract class Boat {
    // Boat properties
    public float robustness;
    public float stamina = 120f;
    public float maneuverability;
    public float speed;
    public float acceleration;

    public float current_speed = 20f;
    public float turningSpeed = 0.25f;
    public float targetAngle = 0f;

    public Sprite boatSprite;
    public Texture boatTexture;
    public Body boatBody;

    private TextureAtlas textureAtlas;
    private Animation animation;

    public Lane lane;
    public float leftLimit;
    public float rightLimit;


    public Boat(float robustness, float speed, float acceleration, float maneuverability, int boatType, Lane lane) {
        this.robustness = robustness;
        this.speed = speed;
        this.acceleration = acceleration;
        this.maneuverability = maneuverability;
        turningSpeed *= this.maneuverability / 100;


        boatTexture = new Texture("Boat" + (boatType + 1) + ".png");

        textureAtlas = new TextureAtlas(Gdx.files.internal("Boats/Boat" + (boatType + 1) +  ".atlas"));
        animation = new Animation(1/15f, textureAtlas.getRegions());

        this.lane = lane;
    }

    // Creates a new boat at posX, posY meters
    public void createBoatBody(World world, float posX, float posY, String bodyFile){
        boatSprite = new Sprite(boatTexture);
        boatSprite.scale(-0.8f);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(posX, posY);
        boatBody = world.createBody(bodyDef);

        boatBody.setUserData(this);

        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal(bodyFile));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0f;
        fixtureDef.restitution = 0f;
        fixtureDef.friction = 0f;

        float scale = boatSprite.getWidth() / GameData.METERS_TO_PIXELS * boatSprite.getScaleX();
        loader.attachFixture(boatBody, "Name", fixtureDef, scale);
    }

    public void drawBoat(Batch batch){
        batch.begin();
        batch.draw((TextureRegion) animation.getKeyFrame(GameData.currentTimer, true), boatSprite.getX(), boatSprite.getY(), boatSprite.getOriginX(),
                boatSprite.getOriginY(),
                boatSprite.getWidth(), boatSprite.getHeight(), boatSprite.getScaleX(), boatSprite.
                        getScaleY(), boatSprite.getRotation());
        batch.end();
    }

    public void updateLimits(){
        int i;
        for (i = 1; i < lane.leftIterator; i++){
            if (lane.leftBoundry[i][0] > boatSprite.getY() + (boatSprite.getHeight() / 2)) {
                break;
            }
        }
        leftLimit = lane.leftBoundry[i - 1][1];

        for (i = 1; i < lane.rightIterator; i++){
            if (lane.rightBoundry[i][0] > boatSprite.getY() + (boatSprite.getHeight() / 2)) {
                break;
            }
        }
        rightLimit = lane.rightBoundry[i - 1][1];
    }

    public float[] getLimitsAt(float yPosition){
        float[] lst = new float[2];
        int i;
        for (i = 1; i < lane.leftIterator; i++){
            if (lane.leftBoundry[i][0] > yPosition) {
                break;
            }
        }
        lst[0] = lane.leftBoundry[i - 1][1];

        for (i = 1; i < lane.rightIterator; i++){
            if (lane.rightBoundry[i][0] > yPosition) {
                break;
            }
        }
        lst[1] = lane.rightBoundry[i - 1][1];
        return lst;
    }

    public boolean hasFinished(){
        if (boatSprite.getY() + boatSprite.getHeight() / 2 > GameData.finishLineLocation)
            return true;
        return false;
    }

    public abstract void moveBoat();

    public abstract void rotateBoat(float angle);
}
