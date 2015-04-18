package development.codenmore.ld32.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import development.codenmore.ld32.Main;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(Main.WIDTH, Main.HEIGHT);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new Main();
        }
}