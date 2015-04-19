package development.codenmore.ld32.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import development.codenmore.ld32.Main;
import development.codenmore.ld32.assets.Assets;
import development.codenmore.ld32.assets.lighting.LightManager;
import development.codenmore.ld32.assets.lighting.PointLight;
import development.codenmore.ld32.entities.Enemy;
import development.codenmore.ld32.entities.Entity;
import development.codenmore.ld32.entities.Player;
import development.codenmore.ld32.level.Level;

public class Lazer extends Item {

	private static final TextureRegion texture = Assets.getRegion("lazer");
	private static final int beamWidth = 4;
	private static final float pauseTime = 0.5f, waitTime = 0.1f;

	private Rectangle beamBounds;
	private float timer;
	private boolean mustAdd = true;
	private PointLight light;
	private int distance;
	private boolean canFire = true;
	
	public Lazer(Level level, float x, float y) {
		super(level, x, y, ITEMWIDTH, ITEMHEIGHT, "LAZER");
		light = new PointLight(x, y, 800, 50);
		light.setColor(1.0f, 0.0f, 0.0f);

		distance = Main.WIDTH / 2 + 10;
		beamBounds = new Rectangle(x, y, width, height);
	}

	@Override
	public void onUse(Player player, Direction action, float delta) {
		timer += delta;
		player.getInventory().getEnergyBar().incFillByPercent(-0.1f * delta);

		if (timer < waitTime && canFire) {
			if (mustAdd) {
				light.setPosition(player.getX() + width / 2, player.getY()
						+ height / 2);
				LightManager.addLight(light);
				mustAdd = false;
			} else {
				light.setPosition(player.getX() + width / 2, player.getY()
						+ height / 2);
			}

			switch (action) {
			case UP:
				light.setMinMax(beamWidth + 3, beamWidth - 3, -8, distance);
				beamBounds.width = beamWidth * 2;
				beamBounds.height = distance;
				beamBounds.x = light.getX() - beamWidth;
				beamBounds.y = light.getY() - beamWidth;
				break;
			case DOWN:
				light.setMinMax(beamWidth + 3, beamWidth - 3, distance, -2);
				beamBounds.width = beamWidth * 2;
				beamBounds.height = distance;
				beamBounds.x = light.getX() - beamWidth;
				beamBounds.y = light.getY() - beamWidth - distance;
				break;
			case LEFT:
				light.setMinMax(distance, -8, beamWidth, beamWidth);
				beamBounds.height = beamWidth * 2;
				beamBounds.width = distance;
				beamBounds.x = light.getX() - beamWidth - distance;
				beamBounds.y = light.getY() - beamWidth;
				break;
			default:// RIGHT
				light.setMinMax(-2, distance, beamWidth, beamWidth);
				beamBounds.height = beamWidth * 2;
				beamBounds.width = distance;
				beamBounds.x = light.getX() - beamWidth;
				beamBounds.y = light.getY() - beamWidth;
				break;
			}
			//ENTITY COLLISION HURT
			for(Entity e : level.getEntityManager().getEntities()){
				if(e instanceof Enemy){
					if(beamBounds.overlaps(e.getBounds())){
						((Enemy) e).hurt(1);
						canFire = false;
						break;
					}
				}
			}
			
		}else{
			if (!mustAdd) {
				LightManager.removeLight(light);
				mustAdd = true;
			}
			if(timer > waitTime + pauseTime){
				timer = 0;
				canFire = true;
			}
		}
	}

	@Override
	public void onNotUsed(Player player, float delta) {
		if (!mustAdd) {
			LightManager.removeLight(light);
			mustAdd = true;
		}
	}

	@Override
	public void render(SpriteBatch batch, float x, float y, int width,
			int height) {
		batch.draw(texture, x, y, width, height);
	}

	@Override
	public void tick(float delta) {

	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(texture, x, y, width, height);
	}

}
