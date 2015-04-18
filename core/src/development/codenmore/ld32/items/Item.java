package development.codenmore.ld32.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import development.codenmore.ld32.entities.Entity;
import development.codenmore.ld32.entities.Player;
import development.codenmore.ld32.level.Level;

public abstract class Item extends Entity{
	
	public static final int ITEMWIDTH = 32, ITEMHEIGHT = 32;
	
	protected String name;

	public Item(Level level, float x, float y, int width, int height, String name) {
		super(level, x, y, width, height);
		this.name = name;
	}
	
	public abstract void onUse(Player player, float delta);
	
	public abstract void onNotUsed(Player player, float delta);
	
	public abstract void render(SpriteBatch batch, float x, float y, int width, int height);
	
	public String getName(){
		return name;
	}

}
