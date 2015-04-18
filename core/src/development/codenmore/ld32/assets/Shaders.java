package development.codenmore.ld32.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Shaders {

	public static ShaderProgram basic;
	public static ShaderProgram tile;
	
	private Shaders(){}
	
	public static void init(){
		basic = new ShaderProgram(Gdx.files.internal("shaders/basic.vert"), Gdx.files.internal("shaders/basic.frag"));
		if (basic.isCompiled() == false) throw new IllegalArgumentException("Error compiling shader: " + basic.getLog());
		
		tile = new ShaderProgram(Gdx.files.internal("shaders/tile.vert"), Gdx.files.internal("shaders/tile.frag"));
		if (tile.isCompiled() == false) throw new IllegalArgumentException("Error compiling shader: " + tile.getLog());
	}
	
	public static void dispose(){
		basic.dispose();
		tile.dispose();
	}
	
}
