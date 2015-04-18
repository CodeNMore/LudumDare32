package development.codenmore.ld32.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;

public class Assets {
	
	private static AssetManager manager;
	private static ObjectMap<String, TextureRegion> regions;
	private static TextureAtlas atlas;
//	private BitmapFont font;
	
	private Assets(){}
	
	public static void init(){
		//SETUP
		manager = new AssetManager();
		regions = new ObjectMap<String, TextureRegion>();
		//LOADS
//		manager.load("font/font.fnt", BitmapFont.class);
//		manager.load("music/song1.wav", Music.class);
		manager.load("textures/atlas.pack", TextureAtlas.class);
	}
	
	public static void postInit(){
		//SETS
		atlas = manager.get("textures/atlas.pack");
//		font = manager.get("font/font.fnt");
	}
	
	//HELPERS
	
	public static TextureRegion getRegion(String name){
		if(regions.containsKey(name))
			return regions.get(name);
		regions.put(name, atlas.findRegion(name));
		return regions.get(name);
	}
	
	public static void dispose(){
		manager.dispose();
	}
	
	//OTHER
	
	public static float getProgress(){
		return manager.getProgress();
	}
	
	public static boolean step(){
		return manager.update();
	}
	
	public static void fullLoad(){
		manager.finishLoading();
	}

}
