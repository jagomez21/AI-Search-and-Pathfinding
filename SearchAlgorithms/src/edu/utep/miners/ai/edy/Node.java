package edu.utep.miners.ai.edy;

/**
 * @author Eduardo Lara and Jesus Gomez
 *
 * The Node class stores the properties
 * of each node, like location and estimates.
 *
 * Class: CS 4320/5314
 * Instructor: Christopher Kiekintveld
 * Assignment: HW3: Search and Pathfinding
 * Date of last modification: 02/23/2020
 **/

public class Node {
	private int cost, x, y, depth, h, g, f;																					
	private Node comingFrom;																											

	/** Constructor */
	public Node(int cost, int x, int y){
		this.cost = cost;
		this.x = x;
		this.y = y;
	}

	/** 
	 * Checks if given node is equal to this by comparing its values. 
	 * 
	 * @param given node
	 * @return true if given node and this one are the same.
	 * */
	public boolean equals(Node node) {
		return node.getX() == x && node.getY() == y && node.getCost() == cost;
	}

	/** Returns true if node is impassable, which is when cost == 0. */
	public boolean isImpassable() {
		return cost == 0;
	} 

	/** Getters */
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

	public int getF() {
		return f;
	}

	public Node getComingFrom() {
		return comingFrom;
	}

	/** Setters */
	public void setCost(int cost) {
		this.cost = cost;
	}

	public void setH(int h) {
		this.h = h;
	}

	public void setG(int g) {
		this.g = g;
	}

	public void setF(int f) {
		this.f = f;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public void setComingFrom(Node comingFrom) {
		this.comingFrom = comingFrom;
	}
}
