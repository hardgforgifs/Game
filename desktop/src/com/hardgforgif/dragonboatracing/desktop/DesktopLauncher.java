package com.hardgforgif.dragonboatracing.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hardgforgif.dragonboatracing.DragonBoatRacing;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.foregroundFPS = 60;
		config.width = DragonBoatRacing.WIDTH;
		config.height = DragonBoatRacing.HEIGHT;
		new LwjglApplication(new DragonBoatRacing(), config);
	}
}
