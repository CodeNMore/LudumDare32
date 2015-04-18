package development.codenmore.ld32.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import development.codenmore.ld32.assets.Assets;

public class Bar {
	
	private static final TextureRegion color = Assets.getRegion("color");
	
	private float x, y, fill;
	private int width;
	private Color back, fore;
	
	public Bar(float x, float y, int width, Color back, Color fore){
		this.x = x;
		this.y = y;
		this.width = width;
		fill = 10;
		this.back = back;
		this.fore = fore;
	}
	
	public void tick(float delta){
		
	}
	
	public void render(SpriteBatch batch){
		batch.setColor(back);
		batch.draw(color, x, y, width, 16);
		batch.setColor(fore);
		batch.draw(color, x, y, fill, 16);
		batch.setColor(Color.WHITE);
	}
	
	//GETTERS SETTERS
	
	public void fill(){
		fill = width;
	}
	
	public void incFillByPercent(float amount){
		setFillByPercent(getFillPercent() + amount);
	}
	
	public void setFillByPercent(float amount){
		if(amount < 0)
			amount = 0;
		else if(amount > 1.0f)
			amount = 1.0f;
		
		fill = width * amount;
	}
	
	public float getFillPercent(){
		return fill / width;
	}
	
	public void increment(float amount){
		fill += amount;
		if(fill < 0){
			fill = 0;
		}else if(fill > width){
			fill = width;
		}
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public float getFill() {
		return fill;
	}

	public void setFill(float fill) {
		this.fill = fill;
	}

}
