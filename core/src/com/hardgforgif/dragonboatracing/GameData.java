package com.hardgforgif.dragonboatracing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.hardgforgif.dragonboatracing.UI.MenuUI;
import com.hardgforgif.dragonboatracing.UI.UI;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class GameData {
    // Create the game state variables
    public static boolean mainMenu = true;
    public static boolean choosingBoat = false;
    public static boolean gamePlay = false;
    public static boolean showResults = false;
    public static boolean resetGame = false;
    public static boolean endGame = false;

    // Create the game UI and the game music
    public static Music music = Gdx.audio.newMusic(Gdx.files.internal("Vibing.ogg"));
    public static UI currentUI = new MenuUI();

    // Set the rations between the pixels, meters and tiles
    public final static float METERS_TO_PIXELS = 100f;
    public static float TILES_TO_METERS;
    public static float PIXELS_TO_TILES;


    // Create a list of possible boat stats
    // Ordered by: robustness, speed, acceleration, maneuverability
    public static float[][] boatsStats = new float[][] {{120, 100, 100, 80}, {55, 100, 130, 60},
                                                        {90, 100, 100, 130}, {65, 110, 90, 55}};

    // Store information about each lane's boat
    // Boat's starting location
    public static float[][] startingPoints = new float[][]{{2.3f, 4f}, {4f, 4f}, {7f, 4f},{10f, 4f}};
    // Boat's type
    public static int[] boatTypes = new int[4];
    // Boat's standing
    public static int[] standings = new int[4];
    // Boat's penalties
    public static float[] penalties = new float[4];
    // Result of the boat as a Pair<lane number, result>
    public static List<Pair<Integer,Float>> results = new ArrayList<>();

    // Current leg and the current timer in the leg
    public static int currentLeg = 0;
    public static float currentTimer = 0f;

    // Difficulty constants for the AI
    public static float[] difficulty = new float[]{0.9f, 0.95f, 1f};

    // Save the player's state to check if he
}
