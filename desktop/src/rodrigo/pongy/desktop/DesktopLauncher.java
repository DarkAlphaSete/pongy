package rodrigo.pongy.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import rodrigo.pongy.Pongy;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Pongy";
		config.resizable = false;
		config.width = 480;
		config.height = 320;
		config.vSyncEnabled = true;

		new LwjglApplication(new Pongy(), config);
	}
}
