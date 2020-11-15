package com.hardgforgif.dragonboatracing;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import javafx.scene.Camera;

public class Map {
    // Map components
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;

    // The size of the screen we will render the map on
    private float screenWidth;

    // The width and the height of the map in tiles, used to calculate ratios
    private int mapWidth;
    private int mapHeight;

    // The width of each tile in Pixels
    private float unitScale;

    Lane[] lanes = new Lane[4];

    public Map(String tmxFile, float width){
        tiledMap = new TmxMapLoader().load(tmxFile);
        screenWidth = width;

        MapProperties prop = tiledMap.getProperties();
        mapWidth = prop.get("width", Integer.class);
        mapHeight = prop.get("height", Integer.class);

        unitScale = screenWidth / mapWidth / 32f;
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale);
    }

    // Returns the ratio between a tile and a meter in the game world
    public float getTilesToMetersRatio(float metersToPixelsRatio) {
        return ((this.screenWidth / metersToPixelsRatio) / this.mapWidth);
    }

    public void createMapCollisions(String collisionLayerName, float metersToPixels, World world) {
        // Get the objects from the object layer in the tilemap
        MapLayer collisionLayer = tiledMap.getLayers().get(collisionLayerName);
        MapObjects objects = collisionLayer.getObjects();

        // Iterate through the rectangles and create their physic bodies
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;

            // Find where we need to place the physics body
            float positionX = (rectangle.getX() * unitScale / metersToPixels) +
                                (rectangle.getWidth() * unitScale / metersToPixels / 2);
            float positionY = (rectangle.getY() * unitScale / metersToPixels) +
                                (rectangle.getHeight() * unitScale / metersToPixels / 2);
            bodyDef.position.set(positionX, positionY);

            Body objectBody = world.createBody(bodyDef);

            // Create the objects fixture, aka shape and physical properties
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(rectangle.getWidth() * unitScale / metersToPixels / 2,
                           rectangle.getHeight() * unitScale / metersToPixels / 2);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 0f;
            fixtureDef.restitution = 0f;
            fixtureDef.friction = 0f;
            Fixture fixture = objectBody.createFixture(fixtureDef);

            shape.dispose();
        }
    }

    // Renders the map on the screen
    public void renderMap(OrthographicCamera camera) {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    public void createLanes(World world, float metersToPixels, float pixelToTiles){
        MapLayer leftLayer = tiledMap.getLayers().get("CollisionLayerLeft");
        MapLayer rightLayer = tiledMap.getLayers().get("Lane1");

        lanes[0] = new Lane(mapHeight, leftLayer, rightLayer, 10);
        lanes[0].constructBoundries(unitScale);
        lanes[0].spawnObstacles(world, mapHeight / pixelToTiles, metersToPixels);

        leftLayer = tiledMap.getLayers().get("Lane1");
        rightLayer = tiledMap.getLayers().get("Lane2");

        lanes[1] = new Lane(mapHeight, leftLayer, rightLayer, 30);
        lanes[1].constructBoundries(unitScale);
        lanes[1].spawnObstacles(world, mapHeight / pixelToTiles, metersToPixels);

        leftLayer = tiledMap.getLayers().get("Lane2");
        rightLayer = tiledMap.getLayers().get("Lane3");

        lanes[2] = new Lane(mapHeight, leftLayer, rightLayer, 10);
        lanes[2].constructBoundries(unitScale);
        lanes[2].spawnObstacles(world, mapHeight / pixelToTiles, metersToPixels);

        leftLayer = tiledMap.getLayers().get("Lane3");
        rightLayer = tiledMap.getLayers().get("CollisionLayerRight");

        lanes[3] = new Lane(mapHeight, leftLayer, rightLayer, 10);
        lanes[3].constructBoundries(unitScale);
        lanes[3].spawnObstacles(world, mapHeight / pixelToTiles, metersToPixels);
    }


}
