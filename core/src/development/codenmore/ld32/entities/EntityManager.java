package development.codenmore.ld32.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import development.codenmore.ld32.level.Level;

public class EntityManager {
	
	private Player player;
	
	public EntityManager(Level level){
		player = new Player(level, 64, 64);
	}
	
	public void tick(float delta){
		player.tick(delta);
	}
	
	public void render(SpriteBatch batch){
		player.render(batch);
	}

}
