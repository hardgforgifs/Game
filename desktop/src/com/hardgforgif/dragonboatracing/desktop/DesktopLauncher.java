package com.hardgforgif.dragonboatracing.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hardgforgif.dragonboatracing.DragonBoatRacing2;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
//		config.fullscreen = true;
		new LwjglApplication(new DragonBoatRacing2(), config);
	}
}
