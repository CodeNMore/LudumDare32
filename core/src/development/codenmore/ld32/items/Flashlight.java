package development.codenmore.ld32.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import development.codenmore.ld32.Main;
import development.codenmore.ld32.assets.Assets;
import development.codenmore.ld32.assets.lighting.LightManager;
import development.codenmore.ld32.assets.lighting.PointLight;
import development.codenmore.ld32.entities.Player;
import development.codenmore.ld32.level.Level;

public class Flashlight extends Item {
	
	private static TextureRegion texture = Assets.getRegion("flashlight");
	private static final int beamWidth = 18;
	
	private PointLight light;
	private boolean mustAdd = true;
	private int distance;

	public Flashlight(Level level, float x, float y) {
		super(level, x, y, ITEMWIDTH, ITEMHEIGHT, "LIGHT");
		light = new PointLight(x, y, 10, 5);
		light.setColor(0.3f, 0.8f, 0.8f);
		
		distance = Main.WIDTH / 2 + 10;
	}
	
	@Override
	public void onUse(Player player, float delta){
		player.getInventory().getEnergyBar().incFillByPercent(-0.1f * delta);
		if(mustAdd){
			light.setPosition(player.getX() + width / 2, player.getY() + height / 2);
			LightManager.addLight(light);
			mustAdd = false;
		}else{
			light.setPosition(player.getX() + width / 2, player.getY() + height / 2);
		}
		
		switch(player.getLastDir()){
		case UP:
			light.setMinMax(beamWidth, beamWidth, -8, distance);
			break;
		case DOWN:
			light.setMinMax(beamWidth, beamWidth, distance, 4);
			break;
		case LEFT:
			light.setMinMax(distance, -8, beamWidth, beamWidth);
			break;
		default://RIGHT
			light.setMinMax(-2, distance, beamWidth, beamWidth);
			break;
		}
	}
	
	@Override
	public void onNotUsed(Player player, float delta){
		if(!mustAdd){
			LightManager.removeLight(light);
			mustAdd = true;
		} 
	}

	@Override
	public void tick(float delta) {
		
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(texture, x, y, width, height);
	}
	
	@Override
	public void render(SpriteBatch batch, float x, float y, int width, int height) {
		batch.draw(texture, x, y, width, height);
	}

}
