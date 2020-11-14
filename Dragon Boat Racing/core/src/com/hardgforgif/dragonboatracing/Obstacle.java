package com.hardgforgif.dragonboatracing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.hardgforgif.dragonboatracing.standaloneprojects.moveableplayer.BodyEditorLoader;

public class Obstacle {
    Sprite obstacleSprite;
    Texture obstacleTexture;
    Body obstacleBody;

    // Create an obstacle at pos (x,y)
    public Obstacle(String textureName){
        obstacleTexture = new Texture(textureName);
    }

    public void createObstacleBody(World world, float posX, float posY, String bodyFile, float metersToPixels){
        obstacleSprite = new Sprite(obstacleTexture);
        obstacleSprite.scale(-0.8f);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(posX, posY);
        obstacleBody = world.createBody(bodyDef);

        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal(bodyFile));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0f;
        fixtureDef.restitution = 0f;
        fixtureDef.friction = 0f;

        float scale = obstacleSprite.getWidth() / metersToPixels * obstacleSprite.getScaleX();
        loader.attachFixture(obstacleBody, "Name", fixtureDef, scale);

        obstacleSprite.setPosition((obstacleBody.getPosition().x * metersToPixels) - obstacleSprite.getWidth() / 2,
                (obstacleBody.getPosition().y * metersToPixels) - obstacleSprite.getHeight() / 2);
    }

    public void drawObstacle(Batch batch, float metersToPixels){
        obstacleSprite.setPosition((obstacleBody.getPosition().x * metersToPixels) - obstacleSprite.getWidth() / 2,
                (obstacleBody.getPosition().y * metersToPixels) - obstacleSprite.getHeight() / 2);
        batch.begin();
        batch.draw(obstacleSprite, obstacleSprite.getX(), obstacleSprite.getY(), obstacleSprite.getOriginX(),
                obstacleSprite.getOriginY(),
                obstacleSprite.getWidth(), obstacleSprite.getHeight(), obstacleSprite.getScaleX(),
                obstacleSprite.getScaleY(), obstacleSprite.getRotation());
        batch.end();
    }
}
