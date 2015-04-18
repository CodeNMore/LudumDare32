package development.codenmore.ld32.utils;

import com.badlogic.gdx.Gdx;

public class Utils {
	
	private Utils(){}
	
	public static String loadFile(String path){
		return Gdx.files.internal(path).readString();
	}

}
