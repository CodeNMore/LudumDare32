package development.codenmore.ld32.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import development.codenmore.ld32.level.Level;
import development.codenmore.ld32.level.tiles.Tile;

public abstract class Entity {
	
	public enum Direction {
		LEFT, RIGHT, DOWN, UP
	};
	
	public static final int BASEWIDTH = 25, BASEHEIGHT = 32;
	
	protected float x, y;
	protected float speed;
	protected int width, height;
	private Level level;
	private Rectangle bounds;
	
	public Entity(Level level, float x, float y, int width, int height){
		this.level = level;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		speed = 180.0f;
		
		bounds = new Rectangle(x, y, width, height);
	}
	
	public void move(float ax, float ay){
		if(!collision(x + ax, y)){
			x += ax;
		}
		if(!collision(x, y + ay)){
			y += ay;
		}
	}
	
	public boolean collision(float x, float y){
		if(level.getTile((int) (x / Tile.TILEWIDTH), (int) (y / Tile.TILEHEIGHT)).isSolid())
			return true;
		if(level.getTile((int) ((x + width) / Tile.TILEWIDTH), (int) (y / Tile.TILEHEIGHT)).isSolid())
			return true;
		if(level.getTile((int) (x / Tile.TILEWIDTH), (int) ((y + height) / Tile.TILEHEIGHT)).isSolid())
			return true;
		if(level.getTile((int) ((x + width) / Tile.TILEWIDTH), (int) ((y + height) / Tile.TILEHEIGHT)).isSolid())
			return true;
		return false;
	}
	
	public abstract void tick(float delta);
	
	public abstract void render(SpriteBatch batch);
	
	public Rectangle getBounds(){
		bounds.x = x;
		bounds.y = y;
		bounds.width = width;
		bounds.height = height;
		return bounds;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public Level getLevel(){
		return level;
	}

}
