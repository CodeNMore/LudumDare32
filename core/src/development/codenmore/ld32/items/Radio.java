package development.codenmore.ld32.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import development.codenmore.ld32.assets.Assets;
import development.codenmore.ld32.entities.Player;
import development.codenmore.ld32.level.Level;

public class Radio extends Item{
	
	private static TextureRegion texture = Assets.getRegion("radio");

	public Radio(Level level, float x, float y) {
		super(level, x, y, ITEMWIDTH, (int) (ITEMHEIGHT / 1.25f), "RADIO");
	}

	@Override
	public void onUse(Player player, float delta) {
		if(player.getInventory().getEnergyBar().getFillPercent() < 0.7f) return;
		player.getInventory().getEnergyBar().incFillByPercent(-0.7f);
		player.getInventory().removeItem(player.getInventory().getCurrentSelectionNumber());
		x = player.getX();
		y = player.getY();
		player.getLevel().getEntityManager().addItem(this);
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
		
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(texture, x, y, width, height);
	}

}
