package development.codenmore.ld32;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import development.codenmore.ld32.assets.Assets;
import development.codenmore.ld32.assets.Shaders;
import development.codenmore.ld32.input.InputManager;
import development.codenmore.ld32.level.GameCamera;
import development.codenmore.ld32.states.State;

public class Main extends ApplicationAdapter {
	
	//Globals
	public static final int WIDTH = 640, HEIGHT = 480;
	public static final String TITLE = "Light'em Up!";
	//Rendering
	private SpriteBatch batch;
	private OrthographicCamera cam, hudCam;
	private Viewport viewport;
	private FPSLogger fpsl;
	
	@Override
	public void create(){
		fpsl = new FPSLogger();
		//Rendering
		batch = new SpriteBatch();
		cam = new OrthographicCamera(WIDTH, HEIGHT);
		cam.position.set(WIDTH / 2, HEIGHT / 2, 0);
		hudCam = new OrthographicCamera(WIDTH, HEIGHT);
		hudCam.position.set(WIDTH / 2, HEIGHT / 2, 0);
		viewport = new FitViewport(WIDTH, HEIGHT, cam);
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		//Input Managers
		InputManager.setCam(cam);
		InputManager.setViewport(viewport);
		//Cam
		GameCamera.init(cam, hudCam);
		//Shaders
		Shaders.init();
		//Assets
		Assets.init();
		//States - this will init the whole loading process
		State.init();
	}
	
	@Override
	public void render(){
		//Reset
		cam.update();
		hudCam.update();
		batch.setProjectionMatrix(cam.combined);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Render
		if(State.getState() != null){
			State.getState().tick(Gdx.graphics.getDeltaTime());
			State.getState().render(batch);
		}
		
		fpsl.log();
	}
	
	@Override
	public void dispose(){
		//Rendering
		batch.dispose();
		//Assets
		Assets.dispose();
		//Shaders
		Shaders.dispose();
	}
	
	@Override
	public void resize(int width, int height){
		//View
		viewport.update(width, height);
	}
	
	@Override
	public void pause(){
		if(State.getState() != null)
			State.getState().pause();
	}
	
	@Override
	public void resume(){
		if(State.getState() != null)
			State.getState().resume();
	}
	
}
