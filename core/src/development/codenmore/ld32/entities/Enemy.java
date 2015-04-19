package development.codenmore.ld32.entities;

import development.codenmore.ld32.level.Level;

public abstract class Enemy extends Entity {
	
	protected Entity target;
	protected int health;

	public Enemy(Level level, float x, float y, int width, int height) {
		super(level, x, y, width, height);
		health = 5;
		speed = 150;
		target = level.getEntityManager().getPlayer();
	}
	
	public void dumbMoveToTarget(float delta){
		float px = target.getX();
		float py = target.getY();
		float xs = 0, ys = 0;
		
		if(x < px){
			xs = speed * delta;
		}else if(x > px){
			xs = -speed * delta;
		}
		
		if(y < py){
			ys = speed * delta;
		}else if(y > py){
			ys = -speed * delta;
		}
		
		move(xs, ys);
	}
	
	public void hurt(int amt){
		health -= amt;
		if(health <= 0){
			alive = false;
		}
		System.out.println("HURTING ENEMY!");
	}

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.target = target;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

}
