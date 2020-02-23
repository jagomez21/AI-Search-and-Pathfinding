package edu.utep.miners.ai.edy;
import java.util.*;



public class Graph {
	int startx;
	int starty;
	int endx;
	int endy;
	Node[][] nodes; 							//Graph

	public Graph(){

	}


	public Node getStartNode() {
		return nodes[startx][starty];
	}

	public Node getGoalNode() {
		return nodes[endx][endy];
	}

	public List<Node> generateSuccessors(Node current) {
		List<Node> successors = new ArrayList<>();
		int x = current.coordenateX;
		int y = current.coordenateY;    
		Node cheking = null;

		//Up
		cheking = validNode(x - 1, y);
		if(cheking != null){
			cheking.h = (manhattanDist(cheking, getGoalNode()));					//gets the expected distance from this node to goal
			cheking.g = (current.g + cheking.cost);									//check the cost of the current node + the cost of the node if going through this node
			cheking.f = cheking.g + cheking.h;										//A*score
			successors.add(cheking);
		}

		//Down
		cheking = validNode(x + 1, y);
		if(cheking != null){
			cheking.h = (manhattanDist(cheking, getGoalNode()));
			cheking.g = (current.g + cheking.cost);
			cheking.f = cheking.g + cheking.h;										//A*score
			successors.add(cheking);
		}

		//Left
		cheking = validNode(x, y - 1);
		if(cheking != null){
			cheking.h = (manhattanDist(cheking, getGoalNode()));
			cheking.g = (current.g + cheking.cost);
			cheking.f = cheking.g + cheking.h;										//A*score
			successors.add(cheking);
		}

		//Right
		cheking = validNode(x, y + 1);
		if(cheking != null){
			cheking.h = (manhattanDist(cheking, getGoalNode()));
			cheking.g = (current.g + cheking.cost);
			cheking.f = cheking.g + cheking.h;										//A*score
			successors.add(cheking);
		}
		return successors;
	}

	public List<Node> generateSuccessorsForAstar(Node current,List<Node> nextToVisit, List<Node> visited) {
		List<Node> successors = new ArrayList<>();
		int x = current.coordenateX;
		int y = current.coordenateY;    
		Node cheking = null;

		//Up
		cheking = validNode(x - 1, y);
		if(cheking != null && (!visited.contains(cheking)) ){
			if(!(nextToVisit.contains(cheking))){
				cheking.h = (manhattanDist(cheking, getGoalNode()));					//gets the expected distance from this node to goal
				cheking.g = (current.g + cheking.cost);									//check the cost of the current node + the cost of the node if going through this node
				cheking.f = cheking.g + cheking.h;										//A*score
				successors.add(cheking);
			}else{																		//it is already in the next to visit, to avoid the change in value, lets keep the smallest
				int holdManhattan = (manhattanDist(cheking, getGoalNode()));
				int holdTotalcostToNode = (current.g + cheking.cost);
				int holdAscore = holdTotalcostToNode + holdManhattan;
				if(holdAscore < cheking.f){
					cheking.h = holdManhattan;
					cheking.g = holdTotalcostToNode;
					cheking.f = holdAscore;
					successors.add(cheking);
				}																		//if is greater than the current one, not bother to add it to next to visit
			}
		}

		//Down
		cheking = validNode(x + 1, y);
		if(cheking != null && (!visited.contains(cheking))){
			if(!(nextToVisit.contains(cheking))){
				cheking.h = (manhattanDist(cheking, getGoalNode()));					//gets the expected distance from this node to goal
				cheking.g = (current.g + cheking.cost);									//check the cost of the current node + the cost of the node if going through this node
				cheking.f = cheking.g + cheking.h;										//A*score
				successors.add(cheking);
			}else{																		//it is already in the next to visit, to avoid the change in value, lets keep the smallest
				int holdManhattan = (manhattanDist(cheking, getGoalNode()));
				int holdTotalcostToNode = (current.g + cheking.cost);
				int holdAscore = holdTotalcostToNode + holdManhattan;
				if(holdAscore < cheking.f){
					cheking.h = holdManhattan;
					cheking.g = holdTotalcostToNode;
					cheking.f = holdAscore;
					successors.add(cheking);
				}																		//if is greater than the current one, not bother to add it to next to visit
			}
		}

		//Left
		cheking = validNode(x, y - 1);
		if(cheking != null && (!visited.contains(cheking))){
			if(!(nextToVisit.contains(cheking))){
				cheking.h = (manhattanDist(cheking, getGoalNode()));					//gets the expected distance from this node to goal
				cheking.g = (current.g + cheking.cost);									//check the cost of the current node + the cost of the node if going through this node
				cheking.f = cheking.g + cheking.h;										//A*score
				successors.add(cheking);
			}else{																		//it is already in the next to visit, to avoid the change in value, lets keep the smallest
				int holdManhattan = (manhattanDist(cheking, getGoalNode()));
				int holdTotalcostToNode = (current.g + cheking.cost);
				int holdAscore = holdTotalcostToNode + holdManhattan;
				if(holdAscore < cheking.f){
					cheking.h = holdManhattan;
					cheking.g = holdTotalcostToNode;
					cheking.f = holdAscore;
					successors.add(cheking);
				}																		//if is greater than the current one, not bother to add it to next to visit
			}
		}

		//Right
		cheking = validNode(x, y + 1);
		if(cheking != null && (!visited.contains(cheking))){
			if(!(nextToVisit.contains(cheking))){
				cheking.h = (manhattanDist(cheking, getGoalNode()));					//gets the expected distance from this node to goal
				cheking.g = (current.g + cheking.cost);									//check the cost of the current node + the cost of the node if going through this node
				cheking.f = cheking.g + cheking.h;										//A*score
				successors.add(cheking);
			}else{																		//it is already in the next to visit, to avoid the change in value, lets keep the smallest
				int holdManhattan = (manhattanDist(cheking, getGoalNode()));
				int holdTotalcostToNode = (current.g + cheking.cost);
				int holdAscore = holdTotalcostToNode + holdManhattan;
				if(holdAscore < cheking.f){
					cheking.h = holdManhattan;
					cheking.g = holdTotalcostToNode;
					cheking.f = holdAscore;
					successors.add(cheking);
				}																		//if is greater than the current one, not bother to add it to next to visit
			}
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
		return((Math.abs(nodeA.coordenateX - nodeB.coordenateX)) + (Math.abs(nodeA.coordenateY - nodeB.coordenateY)));
	}


	public void setStartx(int sx){
		this.startx = sx;
	}
	public void setStarty(int sy){
		this.starty = sy;
	}
	public void setEndx(int ex){
		this.endx = ex;
	}
	public void setEndy(int ey){
		this.endy = ey;
	}
	public void setGraph(Node[][] graph){
		this.nodes = graph;
	}




}
