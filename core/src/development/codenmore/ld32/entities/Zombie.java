package development.codenmore.ld32.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import development.codenmore.ld32.Main;
import development.codenmore.ld32.assets.Assets;
import development.codenmore.ld32.level.Level;

public class Zombie extends Enemy{
	
	private static TextureRegion zombieUp = Assets.getRegion("enemyup"), 
								zombieDown = Assets.getRegion("enemydown"), 
								zombieLeft = Assets.getRegion("enemyleft"), 
								zombieRight = Assets.getRegion("enemyright");
	
	private float findWaitTimer = 0;
	private boolean lazerZombie;

	public Zombie(Level level, float x, float y, boolean lazerZombie, boolean superZomb) {
		super(level, x, y, BASEWIDTH, BASEHEIGHT);
		this.lazerZombie = lazerZombie;
		if(superZomb)
			distance = (int) (Main.WIDTH / 2.85f);
		speed = 90;
		findPathToTarget();
	}

	@Override
	public void tick(float delta) {
//		dumbMoveToTarget(delta);
		findWaitTimer += delta;
		if(!followPath(delta)){
			if(findWaitTimer > 1.0f){
				findPathToTarget();
				findWaitTimer = 0;
			}
		}
		
		if(lazerZombie)
			beamAttack(delta);
		
		if(!canTouchAttack){
			touchAttackedTimer += delta;
			if(touchAttackedTimer > 0.3f)
				canTouchAttack = true;
		}
		if(level.getEntityManager().getPlayer().getBounds().overlaps(getBounds())){
			onTouchHurt();
		}
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
		default://RIGHT
			batch.draw(zombieRight, x, y, width, height);
			break;
		}
	}
	
	public Direction getLastDir(){
		return lastDir;
	}

}
