package development.codenmore.ld32.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;


public abstract class InputManager implements InputProcessor {

	private static OrthographicCamera cam;
	private static Viewport viewport;
	private static Vector3 touchPos = new Vector3();
	
	public InputManager(){}
	
	public abstract boolean touchDownU(int x, int y, int pointer, int button);
	
	public abstract boolean touchUpU(int x, int y, int pointer, int button);
	
	//WRAPPERS
	
	public boolean touchDown(int screenX, int screenY, int pointer, int button){
		unproject(screenX, screenY);
		return touchDownU((int) touchPos.x, (int)touchPos.y, pointer, button);
	}
	
	public boolean touchUp(int screenX, int screenY, int pointer, int button){
		unproject(screenX, screenY);
		return touchUpU((int) touchPos.x, (int)touchPos.y, pointer, button);
	}
	
	private void unproject(int x, int y){
		if(cam == null || viewport == null)
			throw new IllegalStateException("INPUT MANAGER: CAM OR VIEWPORT IS NULL!");
		touchPos.x = x;
		touchPos.y = y;
		cam.unproject(touchPos, viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());
	}
	
	//STAT GETTERS SETTERS

	public static OrthographicCamera getCam() {
		return cam;
	}

	public static void setCam(OrthographicCamera cam) {
		InputManager.cam = cam;
	}

	public static Viewport getViewport() {
		return viewport;
	}

	public static void setViewport(Viewport viewport) {
		InputManager.viewport = viewport;
	}
	
}
