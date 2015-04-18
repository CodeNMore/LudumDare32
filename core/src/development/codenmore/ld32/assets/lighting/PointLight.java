package development.codenmore.ld32.assets.lighting;



public class PointLight {

	private float[] position;
	private float[] color;
	private int intensity;
	private int showness;
	
	public PointLight(float x, float y, int intensity, int showness){
		position = new float[2];
		position[0] = x;
		position[1] = y;
		
		color = new float[3];
		color[0] = 1.0f;
		color[1] = 1.0f;
		color[2] = 1.0f;
		
		this.showness = showness;
		this.intensity = intensity;
	}
	
	public void setX(int x){
		position[0] = x;
	}
	
	public void setY(int y){
		position[1] = y;
	}
	
	public void incX(float x){
		position[0] += x;
	}
	
	public void incY(float y){
		position[1] += y;
	}
	
	public int getShowness(){
		return showness;
	}
	
	public void setShowness(int showness){
		this.showness = showness;
	}
	
	public int getIntensity(){
		return intensity;
	}
	
	public void setIntensity(int intensity){
		this.intensity = intensity;
	}
	
	public void setColor(float r, float g, float b){
		color[0] = r;
		color[1] = g;
		color[2] = b;
	}
	
	public float[] getColor(){
		return color;
	}
	
	public float[] getPosition(){
		return position;
	}
	
}
