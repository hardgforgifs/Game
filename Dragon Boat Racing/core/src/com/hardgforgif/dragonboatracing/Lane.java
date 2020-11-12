package com.hardgforgif.dragonboatracing;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;

import java.util.Arrays;
import java.util.Comparator;

public class Lane {
    float[][] leftBoundry;
    int leftIterator = 0;
    float[][] rightBoundry;
    int rightIterator = 0;
    MapLayer leftLayer;
    MapLayer rightLayer;

    public Lane(int mapHeight, MapLayer left, MapLayer right){
        leftBoundry = new float[mapHeight][2];
        rightBoundry = new float[mapHeight][2];

        leftLayer = left;
        rightLayer = right;

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

//        java.util.Arrays.sort(leftBoundry, new java.util.Comparator<float[]>() {
//            public int compare(float[] a, float[] b) {
//                return Float.compare(a[0], b[0]);
//            }
//        });

        objects = rightLayer.getObjects();

        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)){
            Rectangle rectangle = rectangleObject.getRectangle();
            float height = rectangle.getY() * unitScale;
            float limit = rectangle.getX() * unitScale;
            rightBoundry[rightIterator][0] = height;
            rightBoundry[rightIterator++][1] = limit;
        }

//        java.util.Arrays.sort(rightBoundry, new java.util.Comparator<float[]>() {
//            public int compare(float[] a, float[] b) {
//                return Float.compare(a[0], b[0]);
//            }
//        });
    }

}
