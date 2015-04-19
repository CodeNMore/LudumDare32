package development.codenmore.ld32.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import development.codenmore.ld32.assets.Shaders;
import development.codenmore.ld32.items.Item;
import development.codenmore.ld32.level.Level;

public class EntityManager {
	
	private Player player;
	private Array<Entity> entities;
	private Array<Item> items;
	
	public EntityManager(Level level){
		player = new Player(level, 270, 650);
		
		entities = new Array<Entity>();
		items = new Array<Item>();
	}
	
	public void tick(float delta){
		player.tick(delta);
		
		for(Entity e : entities){
			e.tick(delta);
			if(!e.isAlive()){
				removeEntity(e);
			}
		}
		
		for(Item i : items){
			i.tick(delta);
			if(i.isCollectable() && i.getBounds().overlaps(player.getBounds())){
				i.collect();
				removeItem(i);
			}else if(!i.isAlive()){
				removeItem(i);
			}
		}
	}
	
	public void render(SpriteBatch batch){
		batch.setShader(Shaders.world);
		for(Item i : items){
			i.render(batch);
		}
		player.render(batch);
		for(Entity e : entities){
			e.render(batch);
		}
		batch.setShader(Shaders.basic);
		
		player.getInventory().render(batch);
	}
	
	public void addItem(Item i){
		items.add(i);
	}
	
	public void addEntity(Entity e){
		entities.add(e);
	}
	
	public void removeEntity(Entity e){
		entities.removeValue(e, true);
	}
	
	public void removeItem(Item i){
		items.removeValue(i, true);
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public Array<Entity> getEntities(){
		return entities;
	}
	
	public Array<Item> getItems(){
		return items;
	}

}
