package development.codenmore.ld32.level;

public class Node {
	
	public int x, y;
	public boolean solid;
	public Node parent;
	
	public Node(int x, int y, Node parent, boolean solid){
		this.x = x;
		this.y = y;
		this.solid = solid;
		this.parent = parent;
	}
	
	public int getF(Node target){
		return getG() + getH(target);
	}
	
	public int getG(){
		if(parent == null)
			return 1;
		return parent.getG() + 1;// + parent.getG();
	}
	
	public int getH(Node target){
		return Math.abs(target.x - x) + Math.abs(target.y - y);
	}

}
