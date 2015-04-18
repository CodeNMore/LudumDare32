package development.codenmore.ld32.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import development.codenmore.ld32.assets.Assets;
import development.codenmore.ld32.level.Level;

public class Zombie extends Enemy{
	
	private static TextureRegion zombieUp = Assets.getRegion("enemyup"), 
								zombieDown = Assets.getRegion("enemydown"), 
								zombieLeft = Assets.getRegion("enemyleft"), 
								zombieRight = Assets.getRegion("enemyright");
	
	private Direction lastDir;

	public Zombie(Level level, float x, float y) {
		super(level, x, y, BASEWIDTH, BASEHEIGHT);
		lastDir = Direction.DOWN;
	}

	@Override
	public void tick(float delta) {
		
	}

	@Override
	public void render(SpriteBatch batch) {
		switch(lastDir){
		case UP:
			batch.draw(zombieUp, x, y, width, height);
			break;
		case DOWN:
			batch.draw(zombieDown, x, y, width, height);
			break;
		case LEFT:
			batch.draw(zombieLeft, x, y, width, height);
			break;
		case RIGHT:
			batch.draw(zombieRight, x, y, width, height);
			break;
		}
	}
	
	public Direction getLastDir(){
		return lastDir;
	}

}
