package edu.utep.miners.ai.jesus;
import java.util.ArrayList;
import java.util.List;

public class Graph {
	
	//Variables
	private int startX, startY, goalX, goalY;
	
	private Node[][] nodes;
	
	public Node getStartNode() {
		return nodes[startX][startY];
	}
	
	public Node getGoalNode() {
		return nodes[goalX][goalY];
	}
	
	public List<Node> generateSuccessors(Node current) {
        List<Node> successors = new ArrayList<>();
        int x = current.getX();
        int y = current.getY();    
        Node cheking = null;

        //Up
        cheking = validNode(x - 1, y);
        if(cheking != null){
        	cheking.setH(manhattanDist(cheking, getGoalNode()));
        	cheking.setG(current.getCost() + cheking.getCost());
            successors.add(cheking);
        }
        
        //Down
        cheking = validNode(x + 1, y);
        if(cheking != null){
        	cheking.setH(manhattanDist(cheking, getGoalNode()));
        	cheking.setG(current.getCost() + cheking.getCost());
            successors.add(cheking);
        }
        
        //Left
        cheking = validNode(x, y - 1);
        if(cheking != null){
        	cheking.setH(manhattanDist(cheking, getGoalNode()));
        	cheking.setG(current.getCost() + cheking.getCost());
            successors.add(cheking);
        }
        
        //Right
        cheking = validNode(x, y + 1);
        if(cheking != null){
        	cheking.setH(manhattanDist(cheking, getGoalNode()));
        	cheking.setG(current.getCost() + cheking.getCost());
            successors.add(cheking);
        }
        return successors;
    }
    
    public Node validNode(int x, int y){
        try {
        	if(!nodes[x][y].isImpassable())
        		return nodes[x][y];
        } catch (IndexOutOfBoundsException e) {
        }
        return null;
    }
    
    public int manhattanDist(Node nodeA, Node nodeB){
        return((Math.abs(nodeA.getX() - nodeB.getX())) + (Math.abs(nodeA.getY() - nodeB.getY())));
    }
	
	//Getters
	public int getStartX() {
		return startX;
	}
	
	public int getStartY() {
		return startY;
	}
	
	public int getGoalX() {
		return goalX;
	}
	
	public int getGoalY() {
		return goalY;
	}
	
	public Node[][] getNodesArray() {
		return nodes;
	}
	
	//Setters
	public void setStartX(int startX) {
		this.startX = startX;
	}
	
	public void setStartY(int startY) {
		this.startY = startY;
	}
	
	public void setGoalX(int goalX) {
		this.goalX = goalX;
	}
	
	public void setGoalY(int goalY) {
		this.goalY = goalY;
	}
	
	public void setNodes(Node[][] nodes) {
		this.nodes = nodes;
	}
}
