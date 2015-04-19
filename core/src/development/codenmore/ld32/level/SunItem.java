package development.codenmore.ld32.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import development.codenmore.ld32.assets.Assets;
import development.codenmore.ld32.assets.lighting.LightManager;
import development.codenmore.ld32.assets.lighting.PointLight;
import development.codenmore.ld32.entities.Player;
import development.codenmore.ld32.items.Item;
import development.codenmore.ld32.level.tiles.Tile;

public class SunItem extends Item {
	
	private TextureRegion texture = Assets.getRegion("thermalimager");//TODO: MAKE SUN ICON!!!
	
	private PointLight light;
	private boolean occur = false;
	private float timer = 0;
	private float lifeTime = 6.0f;

	public SunItem(Level level, float x, float y) {
		super(level, x, y, ITEMWIDTH, ITEMHEIGHT, "SUN");
		
		light = new PointLight(level.getWidth() * Tile.TILEWIDTH / 2, level.getHeight() * Tile.TILEHEIGHT / 2, 2000, 2000);
		light.setColor(1.0f, 0.0f, 1.0f);
	}

	@Override
	public void onUse(Player player, Direction action, float delta) {
		player.getInventory().removeItem(player.getInventory().getCurrentSelectionNumber());
		level.getEntityManager().addItem(this);
		LightManager.addLight(light);
		timer = 0;
		collectable = false;
		occur = true;
		alive = true;
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
		if(occur){
			timer += delta;
			if(timer > lifeTime){
				LightManager.removeLight(light);
				alive = false;
			}
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		if(!occur)
			batch.draw(texture, x, y, width, height);
	}

}
