package com.hardgforgif.dragonboatracing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameData {
    public static boolean mainMenu = true;
    public static boolean choosingBoat = false;
    public static boolean gamePlay = false;
    public static boolean showResults = false;
    public static boolean resetGame = false;
    public static Music music = Gdx.audio.newMusic(Gdx.files.internal("Vibing.ogg"));

    public final static float METERS_TO_PIXELS = 100f;
    public static float TILES_TO_METERS;
    public static float PIXELS_TO_TILES;

    public static List<Float> obstaclesScales = Arrays.asList(-0.8f, 0f, 0f, 0f, 0f, -0.8f);
    public static UI currentUI = new MenuUI();

    public static float[][] startingPoints = new float[][]{{2.3f, 4f}, {4f, 4f}, {7f, 4f},{10f, 4f}};

    public static float[][] boatsStats = new float[][] {{100, 100, 100, 80}, {70, 100, 130, 60},   //robustness, speed, acceleration, maneuverability
                                                        {110, 95, 95, 130}, {80, 110, 90, 55}};
    public static int[] boatTypes = new int[4];

    public static int currentLeg;
    public static float currentTimer = 0f;

    public static float finishLineLocation = 9000f;
    public static List<Pair<Integer,Float>> results = new ArrayList<Pair<Integer,Float>>();
    public static int[] standings = new int[4]; // Standings of the boat on each lane
}
