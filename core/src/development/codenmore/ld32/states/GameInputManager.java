package development.codenmore.ld32.states;

import com.badlogic.gdx.Input.Keys;

import development.codenmore.ld32.entities.Entity.Direction;
import development.codenmore.ld32.input.InputManager;

public class GameInputManager extends InputManager {
	
	public static boolean up, down, left, right;
	public static Direction action = Direction.NONE;

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
		}else if(keycode == Keys.UP){
			action = Direction.UP;
		}else if(keycode == Keys.DOWN){
			action = Direction.DOWN;
		}else if(keycode == Keys.LEFT){
			action = Direction.LEFT;
		}else if(keycode == Keys.RIGHT){
			action = Direction.RIGHT;
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
		}else if(action == Direction.UP && keycode == Keys.UP){
			action = Direction.NONE;
		}else if(action == Direction.DOWN && keycode == Keys.DOWN){
			action = Direction.NONE;
		}else if(action == Direction.LEFT && keycode == Keys.LEFT){
			action = Direction.NONE;
		}else if(action == Direction.RIGHT && keycode == Keys.RIGHT){
			action = Direction.NONE;
		}
		return false;
	}
	
	public static boolean actionPressed(){
		return !(action == Direction.NONE);
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
