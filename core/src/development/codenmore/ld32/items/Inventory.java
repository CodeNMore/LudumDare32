package development.codenmore.ld32.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import development.codenmore.ld32.assets.Assets;
import development.codenmore.ld32.assets.Shaders;
import development.codenmore.ld32.entities.Player;
import development.codenmore.ld32.level.GameCamera;

public class Inventory {
	
	private static TextureRegion slot = Assets.getRegion("inventorySlot");
	private static TextureRegion heart = Assets.getRegion("heart"), energy = Assets.getRegion("energy");
	
	private Player player;
	private Item[] items = new Item[4];
	private int currentSelection = 0;
	private Bar energyBar, healthBar;
	
	public Inventory(Player player){
		this.player = player;
		energyBar = new Bar(362, 53, 260, Color.LIGHT_GRAY, Color.YELLOW);
		healthBar = new Bar(362, 23, 260, Color.LIGHT_GRAY, Color.RED);
	}
	
	public void tick(float delta){
		if(Gdx.input.isKeyJustPressed(Keys.LEFT)){
			if(getCurrentSelectedItem() != null){
				getCurrentSelectedItem().onNotUsed(player, delta);
			}
			currentSelection--;
			if(currentSelection < 0){
				currentSelection = items.length - 1;
			}
		}else if(Gdx.input.isKeyJustPressed(Keys.RIGHT)){
			if(getCurrentSelectedItem() != null){
				getCurrentSelectedItem().onNotUsed(player, delta);
			}
			currentSelection++;
			if(currentSelection >= items.length){
				currentSelection = 0;
			}
		}

		energyBar.tick(delta);
		healthBar.tick(delta);
	}
	
	public void render(SpriteBatch batch){
		batch.setShader(Shaders.basic);
		batch.setProjectionMatrix(GameCamera.getHudCam().combined);
		for(int i = 0;i < items.length;++i){
			if(i == currentSelection){
				batch.setColor(Color.YELLOW);
				batch.draw(slot, i * Item.ITEMWIDTH * 2.4f + 16, 16, Item.ITEMWIDTH * 2, Item.ITEMHEIGHT * 2);
				batch.setColor(Color.WHITE);
			}else{
				batch.draw(slot, i * Item.ITEMWIDTH * 2.4f + 16, 16, Item.ITEMWIDTH * 2, Item.ITEMHEIGHT * 2);
			}
			
			if(items[i] != null){
				Assets.setFontMed();
				Assets.setFontColor(Color.ORANGE);
				Assets.drawString(batch, items[i].getName(), i * Item.ITEMWIDTH * 2.4f + 18, 100);
				items[i].render(batch, i * Item.ITEMWIDTH * 2.4f + 16, 16, Item.ITEMWIDTH * 2, Item.ITEMHEIGHT * 2);
			}
		}
		
		batch.draw(heart, 330, 16, 24, 24);
		batch.draw(energy, 330, 52, 24, 24);
		
		energyBar.render(batch);
		healthBar.render(batch);
		
		batch.setShader(Shaders.basic);
		batch.setProjectionMatrix(GameCamera.getCam().combined);
	}
	
	public Item getCurrentSelectedItem(){
		return items[currentSelection];
	}
	
	public void removeItem(int slot){
		if(slot < 0 || slot >= items.length)
			return;
		items[slot] = null;
	}
	
	public void addItem(Item item){
		for(int i = 0;i < items.length;++i){
			if(items[i] == null){
				items[i] = item;
				return;
			}
		}
	}
	
	public void clear(){
		for(int i = 0;i < items.length;++i){
			items[i] = null;
		}
	}
	
	//GETTERS SETTERS

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Bar getEnergyBar() {
		return energyBar;
	}

	public void setEnergyBar(Bar energyBar) {
		this.energyBar = energyBar;
	}

	public Bar getHealthBar() {
		return healthBar;
	}

	public void setHealthBar(Bar healthBar) {
		this.healthBar = healthBar;
	}
	
	public int getCurrentSelectionNumber(){
		return currentSelection;
	}

}
