package com.dmartelly.culttower.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dmartelly.culttower.CultTower;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Cult Tower";
		config.width = 1280;
		config.height = 960;
		new LwjglApplication(new CultTower(), config);
	}
}
