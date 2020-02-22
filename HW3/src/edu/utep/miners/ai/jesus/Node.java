package edu.utep.miners.ai.jesus;

public class Node {
	
	//Variables
	private int cost;
	private int x;
	private int y;
	private int depth;
	private int g;
    private int h;
	
	
	//Constructor
	public Node(int cost, int x, int y) {
		this.cost = cost;
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Node node) {
		return node.getX() == x && node.getY() == y && node.getCost() == cost;
	}
	
	public boolean isImpassable() {
		return cost == 0;
	} 
	
	//Getters
	public int getCost() {
		return cost;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getH() {
		return h;
	}
	
	public int getG() {
		return g;
	}
	
	public int getDepth() {
		return depth;
	}
	
	//Setters
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public void setH(int h) {
		this.h = h;
	}
	
	public void setG(int g) {
		this.g = g;
	}
	
	public void setDepth(int depth) {
		this.depth = depth;
	}
}
