package development.codenmore.ld32.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import development.codenmore.ld32.Main;
import development.codenmore.ld32.assets.Assets;
import development.codenmore.ld32.assets.Shaders;
import development.codenmore.ld32.assets.lighting.LightManager;
import development.codenmore.ld32.entities.EntityManager;
import development.codenmore.ld32.entities.LightPost;
import development.codenmore.ld32.level.tiles.Tile;
import development.codenmore.ld32.utils.Utils;
import development.codenmore.ld32.utils.Vec2;

public class Level {

	private byte[] tiles;
	private Array<Vec2> spawnPoints;
	private int width, height;
	private EntityManager manager;
	private boolean lost = false;

	public Level(String path) {
		manager = new EntityManager(this);
		spawnPoints = new Array<Vec2>();
		load(path);
	}

	public void tick(float delta) {
		if(!lost){
		LightManager.tick(delta);
		manager.tick(delta);
		}else{
			if(Gdx.input.isKeyJustPressed(Keys.P)){
				lost = false;
				manager.getWaveManager().reset();
			}
		}
	}

	public void render(SpriteBatch batch) {
		if (!lost) {
			batch.setShader(Shaders.world);
			int x0 = (int) Math.max(0, (GameCamera.getX() - Main.WIDTH / 2)
					/ Tile.TILEWIDTH);
			int x1 = (int) Math.min(width, GameCamera.getX() / Tile.TILEWIDTH
					+ (Main.WIDTH / 2 / Tile.TILEWIDTH) + 2);
			int y0 = (int) Math.max(0, (GameCamera.getY() - Main.HEIGHT / 2)
					/ Tile.TILEHEIGHT);
			int y1 = (int) Math.min(height, GameCamera.getY() / Tile.TILEHEIGHT
					+ (Main.HEIGHT / 2 / Tile.TILEHEIGHT) + 1);

			for (int y = y1 - 1; y >= y0; --y) {
				for (int x = x0; x < x1; ++x) {
					getTile(x, y).render(batch, x * Tile.TILEWIDTH,
							y * Tile.TILEHEIGHT);
				}
			}
			batch.setShader(Shaders.basic);

			manager.render(batch);
		} else {
			batch.setShader(Shaders.basic);
			batch.setProjectionMatrix(GameCamera.getHudCam().combined);
			Assets.setFontHuge();
			Assets.setFontColor(Color.ORANGE);
			Assets.drawString(batch, "YOU LOST SCORE: " + manager.getWaveManager().score, 32, Main.HEIGHT / 2);
			Assets.drawString(batch, "PRESS P TO PLAY AGAIN", 32, Main.HEIGHT / 3);
			batch.setProjectionMatrix(GameCamera.getCam().combined);
		}
	}

	public Tile getTile(int x, int y) {
		if (x >= width || x < 0 || y < 0 || y >= height)
			return Tile.voidTile;
		return Tile.tiles[tiles[x + y * width]];
	}

	private void load(String path) {
		String[] tokens = Utils.loadFile(path).split("\\s+");
		width = Integer.parseInt(tokens[0]);
		height = Integer.parseInt(tokens[1]);
		tiles = new byte[width * height];
		LightManager.reset();
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				if (tokens[(x + y * width) + 2].length() > 1) {
					tiles[x + y * width] = Byte
							.parseByte(tokens[(x + y * width) + 2].substring(0,
									1));

					if (tokens[(x + y * width) + 2].charAt(1) == 'L') {// Light
																		// Post
						manager.addEntity(new LightPost(this, x
								* Tile.TILEWIDTH, y * Tile.TILEHEIGHT));
					} else if (tokens[(x + y * width) + 2].charAt(1) == 'S') {// Spawn
																				// Point
						spawnPoints.add(new Vec2(x * Tile.TILEWIDTH, y
								* Tile.TILEHEIGHT));
					}
				} else {
					tiles[x + y * width] = Byte
							.parseByte(tokens[(x + y * width) + 2]);
				}
			}
		}

		// TMEP CODE
		// manager.addItem(new Radio(this, 300, 300));
		// manager.addItem(new Bomb(this, 350, 300));
		// manager.addItem(new ThermalItem(this, 500, 500));
		// Zombie zl = new Zombie(this, 570, 650);
		// manager.addEnemy(zl);
		// zl.findPathToTarget();
	}

	public void lose() {
		lost = true;
	}

	public EntityManager getEntityManager() {
		return manager;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Array<Vec2> getSpawnPoints() {
		return spawnPoints;
	}

}
