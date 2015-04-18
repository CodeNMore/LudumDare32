package development.codenmore.ld32.level.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import development.codenmore.ld32.assets.Assets;

public class Tile {

	//DECLARATION TILES
	
	public static Tile[] tiles = new Tile[4];
	public static Tile voidTile = new Tile(Assets.getRegion("color"), false, (byte) 0);
	public static Tile grassTile = new Tile(Assets.getRegion("grass"), false, (byte) 1);
	public static Tile stoneWallTile = new Tile(Assets.getRegion("stone"), true, (byte) 2);
	
	//CLASS
	
	public static final int TILEWIDTH = 48, TILEHEIGHT = 48;
	
	private TextureRegion texture;
	private boolean solid;
	private byte id;
	
	public Tile(TextureRegion texture, boolean solid, byte id){
		this.texture = texture;
		this.solid = solid;
		this.id = id;
		
		tiles[id] = this;
	}
	
//	public void tick(float delta){}
	
	public void render(SpriteBatch batch, float x, float y){
		batch.draw(texture, x, y, TILEWIDTH, TILEHEIGHT);
	}

	//GETTERS SETTERS
	
	public TextureRegion getTexture() {
		return texture;
	}

	public void setTexture(TextureRegion texture) {
		this.texture = texture;
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public byte getId() {
		return id;
	}

	public void setId(byte id) {
		this.id = id;
	}
	
}
