package development.codenmore.ld32.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class State {
	
	public static State splashState, gameState;
	
	private static State state = null;
	
	public static void init(){
		splashState = new SplashState();
		setState(splashState);
	}
	
	public static void postInit(){
		gameState = new GameState();
		
		setState(gameState);
	}
	
	public static void setState(State s){
		if(state != null)
			state.onHide();
		state = s;
		if(s != null)
			s.onShow();
	}
	
	public static State getState(){
		return state;
	}
	
	//CLASS

	public abstract void tick(float delta);
	
	public abstract void render(SpriteBatch batch);
	
	public abstract void onShow();
	
	public abstract void onHide();
	
	public void pause(){}
	
	public void resume(){}
	
}
