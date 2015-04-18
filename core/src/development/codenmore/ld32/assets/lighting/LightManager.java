package development.codenmore.ld32.assets.lighting;

import com.badlogic.gdx.utils.Array;

import development.codenmore.ld32.Main;
import development.codenmore.ld32.assets.Shaders;
import development.codenmore.ld32.level.GameCamera;

public class LightManager {
	
	private static Array<PointLight> lights = new Array<PointLight>();
	private static float[] temp = new float[2];
	private static int amount = 0;
	
	private LightManager(){}
	
	public static void addLight(PointLight light){
		if(lights.size >= 10)
			System.out.println("ERROR ADDING LIGHT: the maximum light limit has been reached!");
		else{
			lights.add(light);
		}
	}
	
	public static void removeLight(PointLight light){
		lights.removeValue(light, true);
	}
	
	//Uploads / sets world lights!
	public static void tick(float delta){
		Shaders.world.begin();
		{
			amount = 0;
			
			for(PointLight light : lights){
				temp[0] = light.getX() - GameCamera.getX() + Main.WIDTH / 2;
				temp[1] = light.getY() - GameCamera.getY() + Main.HEIGHT / 2;
				
				Shaders.world.setUniform2fv("lightPosition[" + amount + "]", temp, 0, 2);
				Shaders.world.setUniform3fv("lightColor[" + amount + "]", light.getColor(), 0, 3);
				Shaders.world.setUniform4fv("lightMinMax[" + amount + "]", light.getMinMax(), 0, 4);
				Shaders.world.setUniformi("lightIntensity[" + amount + "]", light.getIntensity());
				Shaders.world.setUniformi("lightShowness[" + amount + "]", light.getShowness());
				
				amount++;
			}
			//Amount
			Shaders.world.setUniformi("amountLights", amount);
		}
		Shaders.world.end();
	}
	
	public static void reset(){
		lights.clear();
	}

}
