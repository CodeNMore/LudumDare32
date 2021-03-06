package development.codenmore.ld32.level;

import com.badlogic.gdx.graphics.OrthographicCamera;

import development.codenmore.ld32.Main;
import development.codenmore.ld32.entities.Entity;

public class GameCamera {
	
	private static OrthographicCamera cam, hudCam;
	private static float x, y;
	
	private GameCamera(){}
	
	public static void init(OrthographicCamera cam, OrthographicCamera hudCam){
		GameCamera.cam = cam;
		GameCamera.hudCam = hudCam;
	}
	
	public static void reset(){
		cam.position.set(Main.WIDTH / 2, Main.HEIGHT / 2, 0);
		x = 0;
		y = 0;
	}
	
	public static void centerOn(Entity e){
		x = e.getX();
		y = e.getY();
		cam.position.set(x, y, 0);
	}
	
	public static float getX(){
		return x;
	}
	
	public static float getY(){
		return y;
	}
	
	public static OrthographicCamera getHudCam(){
		return hudCam;
	}
	
	public static OrthographicCamera getCam(){
		return cam;
	}

}
