package edu.utep.miners.ai.edy;
import java.util.*;

/**
 * @author Eduardo Lara and Jesus Gomez
 *
 * The Graph class stores and manages all nodes
 * that were given from the txt file.
 *
 * Class: CS 4320/5314
 * Instructor: Christopher Kiekintveld
 * Assignment: HW3: Search and Pathfinding
 * Date of last modification: 02/23/2020
 **/

public class Graph {

	/** Variables */
	private int startX, startY, endX, endY;
	private Node[][] nodes;

	/** Returns the starting node of the graph. */
	public Node getStartNode() {
		return nodes[startX][startY];
	}

	/** Returns the goal node of the graph. */
	public Node getGoalNode() {
		return nodes[endX][endY];
	}

	/** 
	 * Checks all neighbors of given node and returns the list of 
	 * all existent and passable neighbors. Also computes h, g and 
	 * f for all neighbors.
	 *
	 * @param current node being checked in order to generate successors.
	 * @return list of successors from given node.
	 */
	public List<Node> generateSuccessors(Node current) {
		List<Node> successors = new ArrayList<>();
		int x = current.getX();
		int y = current.getY();    

		List<Node> neighbors = Arrays.asList(validNode(x - 1, y), validNode(x + 1, y), validNode(x, y - 1), validNode(x, y + 1)); // Up, Down, Left, Right

		for(Node checking : neighbors) {
			if(checking != null) {
				checking.setH(manhattanDist(checking, getGoalNode())); //gets the expected distance from this node to goal
				checking.setG(current.getCost() + checking.getCost());    //check the cost of the current node + the cost of the node if going through this node
				checking.setF(checking.getG() + checking.getH());
				successors.add(checking);
			}
		}
		return successors;
	}

	/** 
	 * Checks all neighbors of given node and returns the list of 
	 * all existent and passable neighbors. Also computes h, g and 
	 * f for all neighbors.
	 *
	 * @param current node being checked in order to generate successors.
	 * @param nextToVisit
	 * @param visited 
	 * @return list of successors from given node.
	 */
	public List<Node> generateSuccessorsForAstar(Node current, List<Node> nextToVisit, List<Node> visited) {
		List<Node> successors = new ArrayList<>();
		int x = current.getX();
		int y = current.getY();    

		List<Node> neighboors = Arrays.asList(validNode(x - 1, y), validNode(x + 1, y), validNode(x, y - 1), validNode(x, y + 1)); // Up, Down, Left, Right

		for(Node checking : neighboors) {
			if(checking != null && (!visited.contains(checking)) ) {
				if(!(nextToVisit.contains(checking))) {
					checking.setH(manhattanDist(checking, getGoalNode()));					//gets the expected distance from this node to goal
					checking.setG(current.getG() + checking.getCost());									//check the cost of the current node + the cost of the node if going through this node
					checking.setF(checking.getG() + checking.getH());										//A*score
					checking.setComingFrom(current);
					successors.add(checking);
				} else {																    //it is already in the next to visit, to avoid the change in value, lets keep the smallest
					int holdManhattan = (manhattanDist(checking, getGoalNode()));
					int holdTotalcostToNode = (current.getG() + checking.getCost());
					int holdAscore = holdTotalcostToNode + holdManhattan;
					if(holdAscore < checking.getF()){
						checking.setH(holdManhattan);
						checking.setG(holdTotalcostToNode);
						checking.setF(holdAscore);
						checking.setComingFrom(current);
					}																		//if is greater than the current one, not bother to add it to next to visit
				}
			}
		}
		return successors;
	}

	/** 
	 * Checks whether node is on the graph and if it is impassable.
	 * If so, it will return the node.
	 * 
	 * @param x x-coord of desire node.
	 * @param y y-coord of desire node.
	 * 
	 * @param desire node if it exists, otherwise null.
	 * */
	public Node validNode(int x, int y){
		try {
			if(!nodes[x][y].isImpassable())
				return nodes[x][y];
		} catch (IndexOutOfBoundsException e) {
		}
		return null;
	}

	/** 
	 * Computes the manhattan-distance between 2 nodes.
	 * |x1 - x2| + |y1 - y2|
	 * 
	 * @param nodeA 1st node
	 * @param nodeB 2nd node
	 * 
	 * @return manhattan-distance
	 * */
	public int manhattanDist(Node nodeA, Node nodeB){
		return((Math.abs(nodeA.getX() - nodeB.getY())) + (Math.abs(nodeA.getY() - nodeB.getY())));
	}

	/** Getters */
	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public int getEndX() {
		return endX;
	}

	public int getEndY() {
		return endY;
	}

	public Node[][] getGraph() {
		return nodes;
	}

	/** Setters */
	public void setStartX(int startX){
		this.startX = startX;
	}

	public void setStartY(int startY){
		this.startY = startY;
	}

	public void setEndX(int endX){
		this.endX = endX;
	}

	public void setEndY(int endY){
		this.endY = endY;
	}

	public void setGraph(Node[][] nodes){
		this.nodes = nodes;
	}
}