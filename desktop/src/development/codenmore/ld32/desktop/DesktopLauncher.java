package development.codenmore.ld32.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import development.codenmore.ld32.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = Main.WIDTH;
		config.height = Main.HEIGHT;
		config.title = Main.TITLE;
		config.resizable = true;
		
		//DEBUG!!!!!
		config.foregroundFPS = 30;
		
		new LwjglApplication(new Main(), config);
	}
}
