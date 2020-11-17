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
    float robustness;
    float stamina;
    float handling;
    float speed;
    float acceleration;
    float turningSpeed = 2f;
    float targetAngle = 0f;

    Sprite boatSprite;
    Texture boatTexture;
    Body boatBody;

    TextureAtlas textureAtlas;
    Animation animation;

    Lane lane;
    float leftLimit;
    float rightLimit;


    public Boat(float robustness, float stamina, float handling, float speed, String textureName, Lane lane) {
        this.robustness = robustness;
        this.stamina = stamina;
        this.handling = handling;
        this.speed = speed;

        boatTexture = new Texture(textureName);
        textureAtlas = new TextureAtlas(Gdx.files.internal("Boats/Boat2.atlas"));
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

    public void loseRobustness(){
        this.robustness -= 10f;
    }

    // We need to apply forces so we will need to work in meters, so we need to pass the ratio
    public abstract void moveBoat();

    public abstract void rotateBoat(float angle);
}
