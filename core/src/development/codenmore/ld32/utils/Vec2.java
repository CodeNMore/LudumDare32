package development.codenmore.ld32.utils;

public class Vec2 {

	public float x, y;
	
	public Vec2(){
		this(0, 0);
	}
	
	public Vec2(float x, float y){
		set(x, y);
	}
	
	public void set(float x, float y){
		this.x = x;
		this.y = y;
	}
	
}
