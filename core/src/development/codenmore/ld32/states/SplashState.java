package development.codenmore.ld32.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import development.codenmore.ld32.Main;
import development.codenmore.ld32.assets.Assets;

public class SplashState extends State {
	
	private Texture loadingTex;
	private final float minTime = 0.0f;//TODO: RESET TO 2.0f
	private float timer = 0;
	
	public SplashState(){
		
	}

	@Override
	public void tick(float delta) {
		timer += delta;
		
		if(Assets.step()){
			if(timer > minTime){
				Assets.postInit();
				State.postInit();
			}
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.begin();
		{
			if(loadingTex != null){
				batch.draw(loadingTex, 0, 0, Main.WIDTH, Main.HEIGHT);
			}
		}
		batch.end();
	}

	@Override
	public void onShow() {
		loadingTex = new Texture(Gdx.files.internal("loading/loading.png"));
		timer = 0;
	}

	@Override
	public void onHide() {
		loadingTex.dispose();
		loadingTex = null;
	}

}
