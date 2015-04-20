package development.codenmore.ld32.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import development.codenmore.ld32.Main;
import development.codenmore.ld32.assets.lighting.LightManager;
import development.codenmore.ld32.assets.lighting.PointLight;
import development.codenmore.ld32.level.Level;
import development.codenmore.ld32.level.Node;
import development.codenmore.ld32.level.tiles.Tile;

public abstract class Enemy extends Entity {
	
	private static final int beamWidth = 4;
	
	protected Entity target;
	protected int health;
	private Array<Node> openNodes, closedNodes;
	private Node startNode, endNode;
	private Node[] nodes;
	protected Direction lastDir;
	protected Node pathNode;
	protected float touchAttackDamage;
	protected float touchAttackedTimer = 0.0f;
	protected boolean canTouchAttack = true;
	
	protected PointLight light;
	protected int distance;
	private Rectangle beamBounds;
	protected float beamAttackWaitTimer = 0.0f, beamAttackWaitTime = 0.0f, beamHurtTimer = 0.0f;
	private boolean mustAddLight = true;

	public Enemy(Level level, float x, float y, int width, int height) {
		super(level, x, y, width, height);
		health = 4;
		speed = 130;
		lastDir = Direction.DOWN;
		touchAttackDamage = 0.1f;
		target = level.getEntityManager().getPlayer();
		
		nodes = new Node[level.getWidth() * level.getHeight()];
		for(int yy = 0;yy < level.getHeight();++yy){
			for(int xx = 0;xx < level.getWidth();++xx){
				nodes[xx + yy * level.getWidth()] = new Node(xx, yy, null, level.getTile(xx, yy).isSolid());
			}
		}
		
		openNodes = new Array<Node>();
		closedNodes = new Array<Node>();
	
		light = new PointLight(x, y, 800, 50);
		light.setColor(0.0f, 0.0f, 1.0f);
		distance = Main.WIDTH / 6;
		beamBounds = new Rectangle(x, y, width, height);
	}
	
	public void beamAttack(float delta){
		beamAttackWaitTimer += delta;
		beamHurtTimer += delta;
		
		if(beamAttackWaitTimer > beamAttackWaitTime){
			if (mustAddLight) {
				light.setPosition(x + width / 2, y + height / 2);
				LightManager.addLight(light);
				mustAddLight = false;
			} else {
				light.setPosition(x + width / 2, y + height / 2);
			}
			
			
			
			switch (lastDir) {
			case UP:
				light.setMinMax(beamWidth + 3, beamWidth - 3, -8, distance);
				beamBounds.width = beamWidth * 2;
				beamBounds.height = distance;
				beamBounds.x = light.getX() - beamWidth;
				beamBounds.y = light.getY() - beamWidth;
				break;
			case DOWN:
				light.setMinMax(beamWidth + 3, beamWidth - 3, distance, -2);
				beamBounds.width = beamWidth * 2;
				beamBounds.height = distance;
				beamBounds.x = light.getX() - beamWidth;
				beamBounds.y = light.getY() - beamWidth - distance;
				break;
			case LEFT:
				light.setMinMax(distance, -8, beamWidth, beamWidth);
				beamBounds.height = beamWidth * 2;
				beamBounds.width = distance;
				beamBounds.x = light.getX() - beamWidth - distance;
				beamBounds.y = light.getY() - beamWidth;
				break;
			default:// RIGHT
				light.setMinMax(-2, distance, beamWidth, beamWidth);
				beamBounds.height = beamWidth * 2;
				beamBounds.width = distance;
				beamBounds.x = light.getX() - beamWidth;
				beamBounds.y = light.getY() - beamWidth;
				break;
			}
			//ENTITY COLLISION HURT
			if(beamBounds.overlaps(level.getEntityManager().getPlayer().getBounds())){
				if(beamHurtTimer > 0.5f){
					level.getEntityManager().getPlayer().hurt(touchAttackDamage);
					beamHurtTimer = 0.0f;
				}
			}
		}else{
			if(!mustAddLight){
				mustAddLight = true;
				LightManager.removeLight(light);
				beamAttackWaitTimer = 0.0f;
			}
		}
	}
	
	public void onTouchHurt(){
		if(canTouchAttack){
			level.getEntityManager().getPlayer().hurt(touchAttackDamage);
			canTouchAttack = false;
			touchAttackedTimer = 0.0f;
		}
	}
	
	public boolean followPath(float delta){
		boolean ret = true;
		float px, py;
		if(pathNode.parent == null){
			px = pathNode.x * Tile.TILEWIDTH + 4;
			py = pathNode.y * Tile.TILEHEIGHT + 4;
		}else{
			px = pathNode.parent.x * Tile.TILEWIDTH + 4;
			py = pathNode.parent.y * Tile.TILEHEIGHT + 4;
		}
		float xs = 0, ys = 0;
		
		if(x < px){
			xs = speed * delta;
			if(x + xs > px){
				xs = 0;
				if(pathNode.parent == null){
					ret = false;
				}else{
					pathNode = pathNode.parent;
				}
			}
		}else if(x > px){
			xs = -speed * delta;
			if(x + xs < px){
				xs = 0;
				if(pathNode.parent == null){
					ret = false;
				}else{
					pathNode = pathNode.parent;
				}
			}
		}
		
		if(y < py){
			ys = speed * delta;
			if(y + ys > py){
				ys = 0;
				if(pathNode.parent == null){
					ret = false;
				}else{
					pathNode = pathNode.parent;
				}
			}
		}else if(y > py){
			ys = -speed * delta;
			if(y + ys < py){
				ys = 0;
				if(pathNode.parent == null){
					ret = false;
				}else{
					pathNode = pathNode.parent;
				}
			}
		}
		lastDir = moveEn(xs, ys);
		
		return ret;
	}
	
	public void findPathToTarget(){
		for(int yy = 0;yy < level.getHeight();++yy){
			for(int xx = 0;xx < level.getWidth();++xx){
				nodes[xx + yy * level.getWidth()].parent = null;
			}
		}
		//REVERSED TO FORM PATH BACKWARDS
		endNode = nodes[(int) ((x + width / 2) / Tile.TILEWIDTH) + (int) ((y + width / 2) / Tile.TILEHEIGHT) * level.getWidth()];
		startNode = nodes[(int) ((target.getX() + target.getWidth() / 2) / Tile.TILEWIDTH) + (int) ((target.getY() + target.getHeight() / 2) / Tile.TILEHEIGHT) * level.getWidth()];
		openNodes.clear();
		closedNodes.clear();
		
		openNodes.add(startNode);
		int lowestF = 1000000000;
		Node lowestFNode = new Node(-1, -1, null, true);
		Node tmpNode = new Node(-1, -1, null, true);
		
		while(true){
			if(openNodes.size <= 0){
				System.out.println("PATH NOT FOUND, OPEN LIST EMPTY");
				break;
			}
			lowestF = 1000000000;
			for(Node n : openNodes){
				if(n.getF(endNode) < lowestF){
					lowestF = n.getF(endNode);
					lowestFNode = n;
				}
			}
		
			openNodes.removeValue(lowestFNode, true);
			closedNodes.add(lowestFNode);
			if(lowestFNode.equals(endNode)){
				//FOUND THE PATH!
				pathNode = lowestFNode;
				break;
			}
			
			tmpNode = nodes[(lowestFNode.x - 1) + lowestFNode.y * level.getWidth()];
			if(!tmpNode.solid){
				if(!openNodes.contains(tmpNode, true) && !closedNodes.contains(tmpNode, true)){
					openNodes.add(tmpNode);
					tmpNode.parent = lowestFNode;
				}else if(!closedNodes.contains(tmpNode, true)){//ON OPEN LIST
					if(tmpNode.getG() < lowestFNode.getG()){
						tmpNode.parent = lowestFNode;
					}
				}
			}
			
			tmpNode = nodes[(lowestFNode.x + 1) + lowestFNode.y * level.getWidth()];
			if(!tmpNode.solid){
				if(!openNodes.contains(tmpNode, true) && !closedNodes.contains(tmpNode, true)){
					openNodes.add(tmpNode);
					tmpNode.parent = lowestFNode;
				}else if(!closedNodes.contains(tmpNode, true)){//ON OPEN LIST
					if(tmpNode.getG() < lowestFNode.getG()){
						tmpNode.parent = lowestFNode;
					}
				}
			}
			
			tmpNode = nodes[lowestFNode.x + (lowestFNode.y - 1) * level.getWidth()];
			if(!tmpNode.solid){
				if(!openNodes.contains(tmpNode, true) && !closedNodes.contains(tmpNode, true)){
					openNodes.add(tmpNode);
					tmpNode.parent = lowestFNode;
				}else if(!closedNodes.contains(tmpNode, true)){//ON OPEN LIST
					if(tmpNode.getG() < lowestFNode.getG()){
						tmpNode.parent = lowestFNode;
					}
				}
			}
			
			tmpNode = nodes[lowestFNode.x + (lowestFNode.y + 1) * level.getWidth()];
			if(!tmpNode.solid){
				if(!openNodes.contains(tmpNode, true) && !closedNodes.contains(tmpNode, true)){
					openNodes.add(tmpNode);
					tmpNode.parent = lowestFNode;
				}else if(!closedNodes.contains(tmpNode, true)){//ON OPEN LIST
					if(tmpNode.getG() < lowestFNode.getG()){
						tmpNode.parent = lowestFNode;
					}
				}
			}
		}
	}
	
	public void dumbMoveToTarget(float delta){
		float px = target.getX();
		float py = target.getY();
		float xs = 0, ys = 0;
		
		if(x < px){
			xs = speed * delta;
			if(x + xs > px)
				xs = 0;
		}else if(x > px){
			xs = -speed * delta;
			if(x + xs < px)
				xs = 0;
		}
		
		if(y < py){
			ys = speed * delta;
			if(y + ys > py)
				ys = 0;
		}else if(y > py){
			ys = -speed * delta;
			if(y + ys < py)
				ys = 0;
		}
		
		move(xs, ys);
	}
	
	public void hurt(int amt){
		health -= amt;
		if(health <= 0){
			alive = false;
			if(LightManager.containsLight(light)){
				LightManager.removeLight(light);
			}
		}
	}

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.target = target;
		findPathToTarget();
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

}
