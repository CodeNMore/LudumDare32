package development.codenmore.ld32.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import development.codenmore.ld32.Main;
import development.codenmore.ld32.assets.Shaders;
import development.codenmore.ld32.assets.lighting.PointLight;
import development.codenmore.ld32.entities.EntityManager;
import development.codenmore.ld32.level.tiles.Tile;
import development.codenmore.ld32.utils.Utils;

public class Level {
	
	private byte[] tiles;
	private int width, height;
	private EntityManager manager;
	
	private PointLight light;
	
	public Level(String path){
		load(path);
		manager = new EntityManager(this);
		
		light = new PointLight(200, 200, 100, 40);
	}
	
	public void tick(float delta){
		setupLights();
		manager.tick(delta);
	}
	
	public void render(SpriteBatch batch){
		batch.setShader(Shaders.tile);
		int x0 = (int) Math.max(0, (GameCamera.getX() - Main.WIDTH / 2) / Tile.TILEWIDTH);
		int x1 = (int) Math.min(width, GameCamera.getX() / Tile.TILEWIDTH + (Main.WIDTH / 2 / Tile.TILEWIDTH) + 2);
		int y0 = (int) Math.max(0, (GameCamera.getY() - Main.HEIGHT / 2) / Tile.TILEHEIGHT);
		int y1 = (int) Math.min(height, GameCamera.getY() / Tile.TILEHEIGHT + (Main.HEIGHT / 2 / Tile.TILEHEIGHT) + 1);
		
		for(int y = y1 - 1;y >= y0;--y){
			for(int x = x0;x < x1;++x){
				getTile(x, y).render(batch, x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT);
			}
		}
		batch.setShader(Shaders.basic);
		
		manager.render(batch);
	}
	
	private void setupLights(){
		Shaders.tile.begin();
		{
			Shaders.tile.setUniform2fv("lightPosition", light.getPosition(), 0, 2);
			Shaders.tile.setUniform3fv("lightColor", light.getColor(), 0, 3);
			Shaders.tile.setUniformi("lightIntensity", light.getIntensity());
			Shaders.tile.setUniformi("lightShowness", light.getShowness());
		}
		Shaders.tile.end();
	}
	
	public Tile getTile(int x, int y){
		if(x >= width || x < 0 || y < 0 || y >= height)
			return Tile.voidTile;
		return Tile.tiles[tiles[x + y * width]];
	}
	
	private void load(String path){
		String[] tokens = Utils.loadFile(path).split("\\s+");
		width = Integer.parseInt(tokens[0]);
		height = Integer.parseInt(tokens[1]);
		tiles = new byte[width * height];
		for(int i = 0;i < width * height;++i){
			tiles[i] = Byte.parseByte(tokens[i + 2]);
		}
	}

}
