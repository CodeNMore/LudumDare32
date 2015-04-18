package development.codenmore.ld32.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Shaders {

	public static ShaderProgram basic;
	public static ShaderProgram world;
	
	private Shaders(){}
	
	public static void init(){
		basic = new ShaderProgram(Gdx.files.internal("shaders/basic.vert"), Gdx.files.internal("shaders/basic.frag"));
		if (basic.isCompiled() == false) throw new IllegalArgumentException("Error compiling shader: " + basic.getLog());
		
		world = new ShaderProgram(Gdx.files.internal("shaders/world.vert"), Gdx.files.internal("shaders/world.frag"));
		if (world.isCompiled() == false) throw new IllegalArgumentException("Error compiling shader: " + world.getLog());
	}
	
	public static void dispose(){
		basic.dispose();
		world.dispose();
	}
	
}
