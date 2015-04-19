package development.codenmore.ld32.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import development.codenmore.ld32.Main;
import development.codenmore.ld32.assets.Shaders;
import development.codenmore.ld32.assets.lighting.LightManager;
import development.codenmore.ld32.entities.EntityManager;
import development.codenmore.ld32.entities.LightPost;
import development.codenmore.ld32.entities.Zombie;
import development.codenmore.ld32.items.Flashlight;
import development.codenmore.ld32.items.Lazer;
import development.codenmore.ld32.items.Radio;
import development.codenmore.ld32.level.tiles.Tile;
import development.codenmore.ld32.utils.Utils;

public class Level {
	
	private byte[] tiles;
	private int width, height;
	private EntityManager manager;
	
	public Level(String path){
		manager = new EntityManager(this);
		load(path);
	}
	
	public void tick(float delta){
		LightManager.tick(delta);
		manager.tick(delta);
	}
	
	public void render(SpriteBatch batch){
		batch.setShader(Shaders.world);
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
		LightManager.reset();
		for(int y = 0;y < height;++y){
			for(int x = 0;x < width;++x){
				if(tokens[(x + y * width) + 2].length() > 1){
					tiles[x + y * width] = Byte.parseByte(tokens[(x + y * width) + 2].substring(0, 1));
					if(tokens[(x + y * width) + 2].charAt(1) == 'L'){
						manager.addEntity(new LightPost(this, x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT));
					}
				}else{
					tiles[x + y * width] = Byte.parseByte(tokens[(x + y * width) + 2]);
				}
			}
		}
		
		manager.getPlayer().getInventory().clear();
		manager.getPlayer().getInventory().addItem(new Flashlight(this, 0, 0));
		manager.getPlayer().getInventory().addItem(new Lazer(this, 0, 0));
		manager.addItem(new Radio(this, 300, 300));
		
		manager.addEntity(new Zombie(this, 170, 100));
		manager.addEntity(new Zombie(this, 370, 100));
		manager.addEntity(new Zombie(this, 570, 100));
		manager.addEntity(new Zombie(this, 770, 100));
		manager.addItem(new SunItem(this, 500, 500));
	}
	
	public EntityManager getEntityManager(){
		return manager;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}

}
