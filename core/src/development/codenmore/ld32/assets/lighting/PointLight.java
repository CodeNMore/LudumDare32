package development.codenmore.ld32.assets.lighting;



public class PointLight {

	private float[] position;
	private float[] color;
	private float[] minMax;
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
		
		minMax = new float[4];
		minMax[0] = 0;
		minMax[1] = 0;
		minMax[2] = 0;
		minMax[3] = 0;
		
		this.showness = showness;
		this.intensity = intensity;
	}
	
	public void setPosition(float x, float y){
		position[0] = x;
		position[1] = y;
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
	
	public float getX(){
		return position[0];
	}
	
	public float getY(){
		return position[1];
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
	
	public float[] getMinMax(){
		return minMax;
	}
	
	public void setMinMax(float xmin, float xmax, float ymin, float ymax){
		minMax[0] = xmin;
		minMax[1] = xmax;
		minMax[2] = ymin;
		minMax[3] = ymax;
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
