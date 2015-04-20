package development.codenmore.ld32.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import development.codenmore.ld32.assets.Assets;
import development.codenmore.ld32.level.GameCamera;
import development.codenmore.ld32.level.Level;

public class GameState extends State {
	
	private Level level;
	private GameInputManager inputManager;
	
	public GameState(){
		inputManager = new GameInputManager();
		Gdx.input.setInputProcessor(inputManager);
		
		level = new Level("levels/main.lvl");
	}

	@Override
	public void tick(float delta) {
		level.tick(delta);
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.begin();
		{
			level.render(batch);
		}
		batch.end();
	}

	@Override
	public void onShow() {
		Assets.getMusic().setLooping(true);
		Assets.getMusic().play();
	}

	@Override
	public void onHide() {
		GameCamera.reset();
		Assets.getMusic().stop();
	}

}
