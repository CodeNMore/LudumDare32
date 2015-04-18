package development.codenmore.ld32.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import development.codenmore.ld32.Main;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		
		config.hideStatusBar = true;
		config.useAccelerometer = false;
		config.useCompass = false;
		
		initialize(new Main(), config);
	}
}
