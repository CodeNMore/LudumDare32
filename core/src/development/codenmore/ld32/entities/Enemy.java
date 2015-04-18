package development.codenmore.ld32.entities;

import development.codenmore.ld32.level.Level;

public abstract class Enemy extends Entity {

	public Enemy(Level level, float x, float y, int width, int height) {
		super(level, x, y, width, height);
	}

}
