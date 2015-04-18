package development.codenmore.ld32.states;

import com.badlogic.gdx.Input.Keys;

import development.codenmore.ld32.input.InputManager;

public class GameInputManager extends InputManager {
	
	public static boolean up, down, left, right, action;

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.W){
			up = true;
		}else if(keycode == Keys.S){
			down = true;
		}else if(keycode == Keys.A){
			left = true;
		}else if(keycode == Keys.D){
			right = true;
		}else if(keycode == Keys.SPACE){
			action = true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.W){
			up = false;
		}else if(keycode == Keys.S){
			down = false;
		}else if(keycode == Keys.A){
			left = false;
		}else if(keycode == Keys.D){
			right = false;
		}else if(keycode == Keys.SPACE){
			action = false;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public boolean touchDownU(int x, int y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUpU(int x, int y, int pointer, int button) {
		return false;
	}

}
