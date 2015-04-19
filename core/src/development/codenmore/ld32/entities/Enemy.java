package development.codenmore.ld32.entities;

import com.badlogic.gdx.utils.Array;

import development.codenmore.ld32.level.Level;
import development.codenmore.ld32.level.Node;
import development.codenmore.ld32.level.tiles.Tile;

public abstract class Enemy extends Entity {
	
	protected Entity target;
	protected int health;
	private Array<Node> openNodes, closedNodes;
	private Node startNode, endNode;
	private Node[] nodes;
	protected Node pathNode;

	public Enemy(Level level, float x, float y, int width, int height) {
		super(level, x, y, width, height);
		health = 8;
		speed = 130;
		target = level.getEntityManager().getPlayer();
		
		nodes = new Node[level.getWidth() * level.getHeight()];
		for(int yy = 0;yy < level.getHeight();++yy){
			for(int xx = 0;xx < level.getWidth();++xx){
				nodes[xx + yy * level.getWidth()] = new Node(xx, yy, null, level.getTile(xx, yy).isSolid());
			}
		}
		
		openNodes = new Array<Node>();
		closedNodes = new Array<Node>();
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
		move(xs, ys);
		
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
		}
		System.out.println("HURTING ENEMY!");
	}

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.target = target;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

}
