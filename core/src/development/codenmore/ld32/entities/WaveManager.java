package development.codenmore.ld32.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import development.codenmore.ld32.Main;
import development.codenmore.ld32.assets.Assets;
import development.codenmore.ld32.assets.Shaders;
import development.codenmore.ld32.items.Bomb;
import development.codenmore.ld32.items.Flashlight;
import development.codenmore.ld32.items.Lazer;
import development.codenmore.ld32.items.Radio;
import development.codenmore.ld32.level.GameCamera;
import development.codenmore.ld32.level.ThermalItem;
import development.codenmore.ld32.level.tiles.Tile;
import development.codenmore.ld32.utils.Vec2;

public class WaveManager {

	private EntityManager manager;
	private int wave;
	public int score;
	private boolean inBetweenWaves;
	private float waveWaitTimer = 0.0f;
	private float waveWaitTime = 5.0f;
	private boolean displayWaveInfo = false;
	private Vec2 spawnRate;
	private int enemiesToSpawn;
	private int enemyTypes;
	private float enemySpawnTimer;
	private float nextEnemySpawnTime;

	public WaveManager(EntityManager manager) {
		this.manager = manager;
		spawnRate = new Vec2();

		reset();
	}

	public void tick(float delta) {
		if (!inBetweenWaves && enemiesToSpawn <= 0
				&& manager.getEnemies().size <= 0) {
			inBetweenWaves = true;
			waveWaitTimer = 0.0f;
		} else if (inBetweenWaves) {
			waveWaitTimer += delta;

			if (waveWaitTimer > waveWaitTime) {
				inBetweenWaves = false;
				displayWaveInfo = false;
				score += wave * 40;
				nextWave();
			} else {
				displayWaveInfo = true;
			}
		} else if (!inBetweenWaves) {// CURRENTLY IN A WAVE WORKING
			enemySpawnTimer += delta;
			if (enemiesToSpawn > 0 && enemySpawnTimer >= nextEnemySpawnTime) {
				enemySpawnTimer = 0.0f;
				nextEnemySpawnTime = MathUtils.random(spawnRate.x, spawnRate.y);
				spawnEnemy(manager.getLevel().getSpawnPoints().random());
				enemiesToSpawn--;
			}
		}
	}

	public void render(SpriteBatch batch) {
		batch.setShader(Shaders.basic);
		batch.setProjectionMatrix(GameCamera.getHudCam().combined);

		if (displayWaveInfo) {
			Assets.setFontHuge();
			Assets.setFontColor(Color.ORANGE);
			Assets.drawString(batch, "WAVE  " + (wave + 1) + "  STARTING IN  "
					+ (int) (waveWaitTime - waveWaitTimer), 10,
					Main.HEIGHT / 2.5f);
		}
		// Display wave # at top and score
		Assets.setFontColor(Color.WHITE);
		Assets.setFontMed();
		Assets.drawString(batch, "WAVE: " + wave, Main.WIDTH - 130, Main.HEIGHT);
		Assets.drawString(batch, "SCORE: " + score, Main.WIDTH - 130,
				Main.HEIGHT - 20);

		batch.setProjectionMatrix(GameCamera.getCam().combined);
	}

	public void spawnEnemy(Vec2 spawnPoint) {
		switch (enemyTypes) {
		case 1:// 1 - Lazer Zombie
			manager.addEnemy(new Zombie(manager.getLevel(), spawnPoint.x,
					spawnPoint.y, true, false));
			break;
		case 2:// 1 - Lazer Zombie 2
			manager.addEnemy(new Zombie(manager.getLevel(), spawnPoint.x,
					spawnPoint.y, true, true));
			break;
		default:// 0 - Zombie
			manager.addEnemy(new Zombie(manager.getLevel(), spawnPoint.x,
					spawnPoint.y, false, false));
			break;
		}
	}

	public void nextWave() {
		wave++;
		// Spawn Enemies
		if (wave < 4) {
			enemiesToSpawn = wave * 2;
			spawnRate.set(4.0f, 6.0f);
			enemyTypes = 0;
		} else if (wave < 9) {
			enemiesToSpawn = (int) (wave * 1.8f);
			spawnRate.set(5.0f, 10.0f);
			enemyTypes = 1;// TODO: CHANGE TO 1
		} else if (wave < 15) {
			enemiesToSpawn = (int) (wave * 1.8f);
			spawnRate.set(8.0f, 14.0f);
			enemyTypes = 2;// TODO: CHANGE TO 2
		}

		enemySpawnTimer = 0.0f;
		nextEnemySpawnTime = 0.0f;

		// Spawn item
		if(wave <= 19){
			genItem();
		}else if(wave > 19){
			genItem();
			genItem();
		}else if(wave > 32){
			genItem();
			genItem();
			genItem();
		}
	}
	
	public void genItem(){
		int rx = MathUtils.random(0, manager.getLevel().getWidth());
		int ry = MathUtils.random(0, manager.getLevel().getHeight());
		while(manager.getLevel().getTile(rx, ry).isSolid()){
			rx = MathUtils.random(0, manager.getLevel().getWidth());
			ry = MathUtils.random(0, manager.getLevel().getHeight());
		}
		rx *= Tile.TILEWIDTH;
		ry *= Tile.TILEHEIGHT;
		
		if (wave <= 6) {
			if (wave < 3) {
				manager.addItem(new ThermalItem(manager.getLevel(), rx, ry));
			} else if (wave >= 3) {
			if (MathUtils.randomBoolean()) {
				manager.addItem(new ThermalItem(manager.getLevel(), rx, ry));
			} else {
				manager.addItem(new Radio(manager.getLevel(), rx, ry));
			}
			}
		} else {
			if (wave == 7) {
				manager.addItem(new Bomb(manager.getLevel(), rx, ry));
			} else {
				int r = MathUtils.random(0, 3);
				if (r == 0) {
					manager.addItem(new ThermalItem(manager.getLevel(), rx, ry));
				} else if (r == 1) {
					manager.addItem(new Bomb(manager.getLevel(), rx, ry));
				} else {
					manager.addItem(new Radio(manager.getLevel(), rx, ry));
				}
			}
		}
	}

	public void reset() {
		wave = 0;
		score = 0;
		enemySpawnTimer = 0.0f;
		inBetweenWaves = false;
		manager.getEnemies().clear();
//		manager.getEntities().clear();
		manager.getItems().clear();
		// Starting Items
		manager.getPlayer().getInventory().clear();
		manager.getPlayer().getInventory()
				.addItem(new Flashlight(manager.getLevel(), 0, 0));
		manager.getPlayer().getInventory()
				.addItem(new Lazer(manager.getLevel(), 0, 0));
		manager.getPlayer().getInventory().getHealthBar().fill();
		manager.getPlayer().getInventory().getEnergyBar().fill();
	}

	public EntityManager getEntityManager() {
		return manager;
	}

	public void incScore(int score) {
		this.score += score;
	}

}
