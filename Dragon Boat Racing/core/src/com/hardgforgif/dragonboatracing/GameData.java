package com.hardgforgif.dragonboatracing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class GameData {
    public static boolean mainMenu = true;
    public static boolean gamePlay = false;
    public static Music music = Gdx.audio.newMusic(Gdx.files.internal("Vibing.ogg"));
}
