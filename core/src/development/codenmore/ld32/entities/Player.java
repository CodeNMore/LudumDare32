package development.codenmore.ld32.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import development.codenmore.ld32.assets.Assets;
import development.codenmore.ld32.level.GameCamera;
import development.codenmore.ld32.level.Level;
import development.codenmore.ld32.states.GameInputManager;

public class Player extends Entity {
	
	private static TextureRegion playerUp, playerDown, playerLeft, playerRight;
	
	private Direction lastDir;

	public Player(Level level, float x, float y) {
		super(level, x, y, Entity.BASEWIDTH, Entity.BASEHEIGHT);
		playerUp = Assets.getRegion("playerup");
		playerDown = Assets.getRegion("playerdown");
		playerLeft = Assets.getRegion("playerleft");
		playerRight = Assets.getRegion("playerright");
		
		lastDir = Direction.DOWN;
	}

	@Override
	public void tick(float delta) {
		int dirX = 0;
		int dirY = 0;
		if(GameInputManager.left){
			dirX = -1;
			lastDir = Direction.LEFT;
		}else if(GameInputManager.right){
			dirX = 1;
			lastDir = Direction.RIGHT;
		}
		if(GameInputManager.up){
			dirY = 1;
			lastDir = Direction.UP;
		}else if(GameInputManager.down){
			dirY = -1;
			lastDir = Direction.DOWN;
		}
		move(dirX * speed * delta, dirY * speed * delta);
		
		GameCamera.centerOn(this);
	}

	@Override
	public void render(SpriteBatch batch) {
		switch(lastDir){
		case UP:
			batch.draw(playerUp, x, y, width, height);
			break;
		case DOWN:
			batch.draw(playerDown, x, y, width, height);
			break;
		case LEFT:
			batch.draw(playerLeft, x, y, width, height);
			break;
		case RIGHT:
			batch.draw(playerRight, x, y, width, height);
			break;
		}
	}

}
