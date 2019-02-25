package com.cescristorey.invaders3d.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.cescristorey.invaders3d.Invaders3D;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                                   config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
                                   config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
                                   config.fullscreen = true;
		config.title = "Naves 80 - 3D";
                                   config.vSyncEnabled = true;
		new LwjglApplication(new Invaders3D(), config);
	}
}
