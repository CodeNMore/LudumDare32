package development.codenmore.ld32.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import development.codenmore.ld32.assets.Assets;
import development.codenmore.ld32.assets.lighting.LightManager;
import development.codenmore.ld32.assets.lighting.PointLight;
import development.codenmore.ld32.level.Level;
import development.codenmore.ld32.level.tiles.Tile;

public class LightPost extends Entity{
	
	private static TextureRegion texture = Assets.getRegion("post");
	private PointLight light;

	public LightPost(Level level, float x, float y) {
		super(level, x + Tile.TILEWIDTH / 2 - 8, y + Tile.TILEHEIGHT / 2 - 12, 16, 64);
		
		light = new PointLight(x + width + 8, y + height + 8, 7, 1);
		light.setColor(1.0f, 1.0f, 1.0f);
		LightManager.addLight(light);
	}

	@Override
	public void tick(float delta) {
		
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(texture, x, y, width, height);
	}
	
}
