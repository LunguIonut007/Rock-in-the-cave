package com.lungunaiman.rockinthecave.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lungunaiman.rockinthecave.GameClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Rock in the cave";
		config.addIcon("ic_launcher.png", Files.FileType.Internal);
		new LwjglApplication(new GameClass(), config);
	}
}
