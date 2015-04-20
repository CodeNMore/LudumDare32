package development.codenmore.ld32.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import development.codenmore.ld32.assets.Assets;
import development.codenmore.ld32.entities.Enemy;
import development.codenmore.ld32.entities.Player;
import development.codenmore.ld32.level.Level;

public class Radio extends Item{
	
	private static TextureRegion texture = Assets.getRegion("radio");
	
	private float timer = 0.0f;
	private float lifeTime = 10.0f;
	private int radius = 500;

	public Radio(Level level, float x, float y) {
		super(level, x, y, ITEMWIDTH, ITEMHEIGHT, "RADIO");
	}

	@Override
	public void onUse(Player player, Direction action, float delta) {
		if(player.getInventory().getEnergyBar().getFillPercent() < 0.7f) return;
		player.getInventory().getEnergyBar().incFillByPercent(-0.7f);
		player.getInventory().removeItem(player.getInventory().getCurrentSelectionNumber());
		x = player.getX();
		y = player.getY();
		player.getLevel().getEntityManager().addItem(this);
		collectable = false;
	}
	
	@Override
	public void collect(){
		super.collect();
		timer = 0.0f;
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
		timer += delta;
		if(!collectable && timer > lifeTime){
			alive = false;
			for(Enemy e : level.getEntityManager().getEnemies()){
				if(e.getX() > x - radius && e.getX() < x + radius){
					if(e.getY() > y - radius && e.getY() < y + radius){
						e.setTarget(level.getEntityManager().getPlayer());
					}
				}
			}
		}
		//CODE
		if(!collectable && alive){
			for(Enemy e : level.getEntityManager().getEnemies()){
				if(e.getX() > x - radius && e.getX() < x + radius){
					if(e.getY() > y - radius && e.getY() < y + radius){
						e.setTarget(this);
					}
				}
			}
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		if(collectable){
			batch.setColor(0.6f, 0.6f, 1.0f, 0.8f);
			batch.draw(texture, x, y, width, height);
			batch.setColor(Color.WHITE);
		}else
			batch.draw(texture, x, y, width, height);
		
	}

}
