package edu.utep.miners.ai.edy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;


/**
 * @author Eduardo Lara and Jesus Gomez
 *
 * The Algorithms class contains all 3
 * algorithms BFS, IDS and A*search along
 * with helper methods.
 *
 * Class: CS 4320/5314
 * Instructor: Christopher Kiekintveld
 * Assignment: HW3: Search and Pathfinding
 * Date of last modification: 02/23/2020
 **/

public class Algorithms {

	/** Variables */
	private Graph searchSpace;
	private boolean stopCurrentAlg = false;

	/** Constructor */
	public Algorithms (Graph graph) {
		searchSpace = graph;
	}

	/** Displays the graph along with the cost of all the nodes. Used for debug purposes. */
	public void displayGraph(){
		System.out.println("Start Coordinates (" + searchSpace.getStartX() + "," + searchSpace.getStartY() + ")");
		System.out.println("End Coordinates (" + searchSpace.getEndX() + "," + searchSpace.getEndY() + ")");
		System.out.println("Traverse Cost:");

		for(int i = 0; i < searchSpace.getGraph().length; i++) {
			for(int j = 0; j < searchSpace.getGraph()[i].length; j++) {
				if(!searchSpace.getGraph()[i][j].isImpassable()) {
					System.out.print(searchSpace.getGraph()[i][j].getCost() + " ");
				} else {
					System.out.print("  ");
				}
			}
			System.out.println();
		}

		for(int i = 0; i < searchSpace.getGraph().length; i++) {
			for(int j = 0; j < searchSpace.getGraph()[i].length; j++) {
				if(!searchSpace.getGraph()[i][j].isImpassable()) {
					System.out.print(" ("+searchSpace.getGraph()[i][j].getX() + ", " + 
							searchSpace.getGraph()[i][j].getY() + ") ");
				} else {
					System.out.print(" ( , ) ");
				}
			}
			System.out.println();
		}
	}

	/** Prints reversed path of given node. */
	public void findPath(Node graph){
		int totalCost = 0;
		System.out.print("Reverse path: ");
		while(graph.getComingFrom() !=null){
			System.out.println("X: (" + graph.getX() + ", " + graph.getY() + ") Cost: " + graph.getCost());
			graph = graph.getComingFrom();
			totalCost += graph.getCost();
		}
		System.out.println("X: (" + graph.getX() + ", " + graph.getY() + ") Cost: " + graph.getCost());
		totalCost += graph.getCost();
		System.out.println("Total Cost: " + totalCost);
	}

	/** 
	 * Will perform Breadth First search algorithm into the 
	 * search space and prints the output of it. 
	 **/
	public void breadthFirst() {
		System.out.println("\nBreadth-first search: \n");
		long startTime = System.currentTimeMillis(), endTime = startTime + 180000;

		PriorityQueue<Node> nexttoVisit =new PriorityQueue<>(new Comparator<Node>(){
			@Override
			public int compare(Node fNode, Node sNode){	    //put the smallest in front first
				return fNode.getCost() - sNode.getCost();	// put sNode fist: compare a big against a smaller will give a bigger value than 0
			}
		});
		int totalCost = 0;
		List<Node> visitedPos = new ArrayList<>();
		nexttoVisit.add(searchSpace.getStartNode());

		while(!nexttoVisit.isEmpty()) {
			if(System.currentTimeMillis() > endTime) {
				System.out.println("3 minutes exceeded!");
				return;
			}

			Node currentPos = nexttoVisit.remove();               //remove the node to continue with the node

			visitedPos.add(currentPos);						      //now the current position will count as visited

			if(currentPos.equals(searchSpace.getGoalNode())) {	  //Check if we arrived to the goal
				System.out.println("\nGoal Found: " + "(" + currentPos.getX() + "," + currentPos.getY() + ")");
				findPath(searchSpace.getGoalNode());			  //show the path to the beginning
				System.out.println("Runtime: " + (System.currentTimeMillis() - startTime) + " ms");
				System.out.println("Nodes in Memory: " + visitedPos.size());
				return;
			}

			List<Node> successorsOfCurrent = searchSpace.generateSuccessors(currentPos);
			for(Node neighbor : successorsOfCurrent){
				if(!visitedPos.contains(neighbor) ){
					nexttoVisit.add(neighbor);		    //add the next node that will need to visit
					visitedPos.add(neighbor);			//add the nodes that are in the nexttoVisit already so we dont repeat
					neighbor.setComingFrom(currentPos);	//set the nodes where they are coming from
				}
			}
		}
	}

	/** 
	 * Will perform iterative deepening search algorithm into the 
	 * search space and prints the output of it. 
	 **/
	public void iterativeDeepSearch() {
		Node start = searchSpace.getStartNode();
		System.out.println("\nIterative Deep Search: \n");

		int depth = 0, nodeNum = 0;
		do {
			nodeNum = depthSearch(start, depth);
			depth++;
			if(nodeNum == -1) {
				System.out.println("3 minutes exceeded!");
				return;
			}
		} while (!stopCurrentAlg);
		System.out.println("Depth: " + depth);
		System.out.println("Expanded Nodes: " + nodeNum);
		stopCurrentAlg = false;
	}

	/** Performs algorithm and prints nodes in each depth. **/
	private int depthSearch(Node problem, int limit) {
		int nodesExpanded = 1, totalCost = 0;
		long startTime = System.currentTimeMillis(), endTime = startTime + 180000;

		String nodes = "";

		Stack<Node> fringe = new Stack<>();
		Set<Node> visitedNodes = new HashSet<>();
		problem.setDepth(0);
		fringe.push(problem);

		while(!fringe.isEmpty()){
			if(System.currentTimeMillis() > endTime){
				return -1;
			}

			Node current = fringe.pop();
			totalCost += current.getCost();
			nodes += "(" + current.getX() + "," + current.getY() + ") ";

			if(searchSpace.getGoalNode().equals(current)){
				System.out.println(nodes);
				stopCurrentAlg = true;
				System.out.println("\nGoal Found: " + "(" + current.getX() + "," + current.getY() + ")");
				System.out.println("Runtime: " + (System.currentTimeMillis() - startTime) + " ms");
				System.out.println("Total Cost: " + totalCost);
				System.out.println("Nodes in Memory: " + visitedNodes.size());
				return nodesExpanded;
			}

			if(current.getDepth() >= limit){
				break;
			}

			for(Node successor : searchSpace.generateSuccessors(current)) {
				successor.setDepth(current.getDepth() + 1);
				if(!visitedNodes.contains(successor)) {
					nodesExpanded++;
					visitedNodes.add(successor);
					fringe.push(successor);
				}
			}
		}
		System.out.println(nodes);
		return nodesExpanded;
	}

	/** 
	 * Will perform A * Search algorithm into the 
	 * search space and prints the output of it. 
	 **/
	public void aStarSearch() {
		System.out.println("\nA* Search: \n");
		long startTime = System.currentTimeMillis(), endTime = startTime + 180000;

		List<Node> visited = new ArrayList<Node>();
		List<Node> nextToVisit = new ArrayList<Node>();

		Node start = searchSpace.getStartNode();

		nextToVisit.add(start);

		while(!nextToVisit.isEmpty()) {
			if(System.currentTimeMillis() > endTime) {
				System.out.println("3 minutes exceeded!");
				return;
			}

			Node current = nextToVisit.get(0);
			List<Node> neighbors = searchSpace.generateSuccessorsForAstar(current, nextToVisit, visited);
			nextToVisit.remove(0);
			visited.add(current);

			for(Node neighbor : neighbors) {	//checks in generate successors
				if(!visited.contains(neighbor)) {
					nextToVisit.add(neighbor);
				}
			}

			Collections.sort(nextToVisit,new Comparator<Node>(){
				@Override
				public int compare(Node fNode, Node sNode) { //put the smallest in front first
					return fNode.getF() - sNode.getF();		 // put sNode fist: compare a big against a smaller will give a bigger value than 0
				}
			});

			if(nextToVisit.get(0).equals(searchSpace.getGoalNode())){
				System.out.println("\nGoal Found: " + "(" + nextToVisit.get(0).getX() + "," + nextToVisit.get(0).getY() + ")");
				visited.add(nextToVisit.get(0));
				for(Node node : visited) {
					System.out.println(" Cost: " + node.getCost() + 
							" coordinate: (" + node.getX() + ", " + node.getY() + 
							") Value: " + node.getF() + " mahattan: " + node.getH() + 
							" path cost to the node: " + node.getG() + " cost: " + node.getCost());
				}
				System.out.println("Path");
				findPath(searchSpace.getGoalNode());
				System.out.println("Runtime: " + (System.currentTimeMillis() - startTime) + " ms");
				System.out.println("Nodes in Memory: " + visited.size());
				return;
			}
		}
	}
}
