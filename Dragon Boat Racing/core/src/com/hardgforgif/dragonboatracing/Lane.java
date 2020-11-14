package com.hardgforgif.dragonboatracing;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Arrays;
import java.util.Comparator;

import static com.badlogic.gdx.math.MathUtils.random;

public class Lane {
    float[][] leftBoundry;
    int leftIterator = 0;
    float[][] rightBoundry;
    int rightIterator = 0;
    MapLayer leftLayer;
    MapLayer rightLayer;

    Obstacle[] obstacles;

    public Lane(int mapHeight, MapLayer left, MapLayer right, int nrObstacles){
        leftBoundry = new float[mapHeight][2];
        rightBoundry = new float[mapHeight][2];

        leftLayer = left;
        rightLayer = right;

        obstacles = new Obstacle[nrObstacles];

    }

    public void constructBoundries(float unitScale){
        MapObjects objects = leftLayer.getObjects();

        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)){
            Rectangle rectangle = rectangleObject.getRectangle();
            float height = rectangle.getY() * unitScale;
            float limit = (rectangle.getX() * unitScale) + (rectangle.getWidth() * unitScale);
            leftBoundry[leftIterator][0] = height;
            leftBoundry[leftIterator++][1] = limit;
        }

        objects = rightLayer.getObjects();

        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)){
            Rectangle rectangle = rectangleObject.getRectangle();
            float height = rectangle.getY() * unitScale;
            float limit = rectangle.getX() * unitScale;
            rightBoundry[rightIterator][0] = height;
            rightBoundry[rightIterator++][1] = limit;
        }
    }

    public float[] getLimitsAt(float yPosition){
        float[] lst = new float[2];
        int i;
        for (i = 1; i < leftIterator; i++){
            if (leftBoundry[i][0] > yPosition) {
                break;
            }
        }
        lst[0] = leftBoundry[i - 1][1];

        for (i = 1; i < rightIterator; i++){
            if (rightBoundry[i][0] > yPosition) {
                break;
            }
        }
        lst[1] = rightBoundry[i - 1][1];
        return lst;
    }

    public void spawnObstacles(World world, float mapHeight, float metersToPixels){
        int nrObstacles = obstacles.length;
        float segmentLength = mapHeight / nrObstacles;
        for (int i = 0; i < nrObstacles; i++){
            obstacles[i] = new Obstacle("badlogic.jpg");
            float segmentStart = i * segmentLength;
            float yPos = (float) (segmentStart + Math.random() * segmentLength);

            float[] limits = this.getLimitsAt(yPos);
            float leftLimit = limits[0] + 50;
            float rightLimit = limits[1];
            float xPos = (float) (leftLimit + Math.random() * (rightLimit - leftLimit));


            obstacles[i].createObstacleBody(world, xPos / metersToPixels, yPos / metersToPixels, "obstacle.json", metersToPixels);
        }
    }

}
