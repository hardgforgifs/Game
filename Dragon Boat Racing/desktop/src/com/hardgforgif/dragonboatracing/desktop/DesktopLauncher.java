package com.hardgforgif.dragonboatracing.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hardgforgif.dragonboatracing.DragonBoatRacing;
import com.hardgforgif.dragonboatracing.HelloWorld;
import com.hardgforgif.dragonboatracing.standaloneprojects.moveableplayer.MouseMovementPlayer;
import com.hardgforgif.dragonboatracing.standaloneprojects.moveableplayer.PhysicsPlayer;
import com.hardgforgif.dragonboatracing.standaloneprojects.moveableplayer.Player;
import com.hardgforgif.dragonboatracing.standaloneprojects.moveableplayer.test;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 960;
		config.height = 640;
		//config.fullscreen = true;
		new LwjglApplication(new test(), config);
	}
}
