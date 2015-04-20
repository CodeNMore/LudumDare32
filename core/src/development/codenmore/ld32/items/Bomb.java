package development.codenmore.ld32.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import development.codenmore.ld32.assets.Assets;
import development.codenmore.ld32.assets.lighting.LightManager;
import development.codenmore.ld32.assets.lighting.PointLight;
import development.codenmore.ld32.entities.Enemy;
import development.codenmore.ld32.entities.Player;
import development.codenmore.ld32.level.Level;

public class Bomb extends Item {
	
	private static TextureRegion texture = Assets.getRegion("color");
	
	private PointLight light;
	private Rectangle bombBounds;
	private float lifeTimer = 0.0f, lifeTime = 7.0f;

	public Bomb(Level level, float x, float y){
		super(level, x, y, ITEMWIDTH, ITEMHEIGHT, "EMIT");
		light = new PointLight(x, y, 80, 10);
		light.setColor(1.0f, 1.0f, 0.0f);
		bombBounds = new Rectangle(x, y, 200, 200);
		collectable = true;
	}
	
	@Override
	public void onUse(Player player, Direction action, float delta) {
		if(player.getInventory().getEnergyBar().getFillPercent() < 0.8f) return;
		player.getInventory().getEnergyBar().incFillByPercent(-0.8f);
		player.getInventory().removeItem(player.getInventory().getCurrentSelectionNumber());
		x = player.getX();
		y = player.getY();
		bombBounds.x = x - bombBounds.width / 2;
		bombBounds.y = y - bombBounds.height / 2;
		light.setPosition(x, y);
		player.getLevel().getEntityManager().addItem(this);
		collectable = false;
		lifeTimer = 0.0f;
		LightManager.addLight(light);
	}

	@Override
	public void onNotUsed(Player player, float delta) {
		
	}

	@Override
	public void render(SpriteBatch batch, float x, float y, int width, int height) {
		batch.draw(texture, x, y, width, height);
	}

	@Override
	public void tick(float delta) {
		if(!collectable){
			lifeTimer += delta;
			if(lifeTimer > lifeTime){
				LightManager.removeLight(light);
				alive = false;
			}else{
				for(Enemy e : level.getEntityManager().getEnemies()){
					if(e.getBounds().overlaps(bombBounds)){
						e.hurt(1);
					}
				}
			}
		}
	}

	@Override
	public void render(SpriteBatch batch) {}

}
