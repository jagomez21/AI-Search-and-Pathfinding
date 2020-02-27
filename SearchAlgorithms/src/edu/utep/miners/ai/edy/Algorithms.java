package edu.utep.miners.ai.edy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;


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

	/** 
	 * Prints out the total cost for the path found of each algorithms.
	 * 
	 * @param graph node being searched.
	 *  */
	public void totalCost(Node graph){
		int totalCost = 0;
		while(graph.getComingFrom() !=null){
			graph = graph.getComingFrom();
			totalCost += graph.getCost();
		}
		totalCost += graph.getCost();
		System.out.println("Path Cost: " + totalCost);

	}

	/** Prints reversed path of given node. */
	public void findPath(Node graph){
		if(graph != null) {
			findPath(graph.getComingFrom());
			System.out.print(" (" + graph.getX() + ", " + graph.getY() + ") ");
		}
	}

	/** 
	 * Will perform Breadth First search algorithm into the 
	 * search space and prints the output of it. 
	 **/
	public void breadthFirst() {
		System.out.println("\nBreadth-first search: \n");
		long startTime = System.currentTimeMillis(), endTime = startTime + 180000;
		int expandedNodes = 0;
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

				System.out.println();
				totalCost(searchSpace.getGoalNode());
				System.out.println("Nodes Expanded: "+(visitedPos.size()-1));
				System.out.println("Nodes in Mempory, in Queue: "+nexttoVisit.size());
				System.out.println("Runtime: " + (System.currentTimeMillis() - startTime) + " ms");
				System.out.print("Path:");
				findPath(searchSpace.getGoalNode());
				System.out.println();

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
		if(nexttoVisit.isEmpty()){
			System.out.println("Path Cost: "+(-1));
			System.out.println("Nodes Expanded: "+visitedPos.size());
			System.out.println("Nodes in memory, in Queue: "+nexttoVisit.size());
			System.out.println("Running Time: "+ (System.currentTimeMillis() - startTime) + " ms");
			System.out.println("Path Sequence: Null");

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
		System.out.println("Depth: " + searchSpace.getGoalNode().getDepth());
		stopCurrentAlg = false;
	}

	/** Performs algorithm and prints nodes in each depth. 
	 * 
	 * @param problem node being search.
	 * @param limit / depth
	 * 
	 * @return num of nodes expanded.
	 * **/
	private int depthSearch(Node problem, int limit) {
		int nodesExpanded = 1;
		long startTime = System.currentTimeMillis(), endTime = startTime + 180000;

		String nodes = "";

		PriorityQueue<Node> nextToVisit = new PriorityQueue<>(new Comparator<Node>() {
			@Override
			public int compare(Node fNode, Node sNode){	    //put the smallest in front first
				return fNode.getDepth() - sNode.getDepth();	// put sNode fist: compare a big against a smaller will give a bigger value than 0
			}
		});

		List<Node> visitedNodes = new ArrayList<>();
		if(problem.equals(searchSpace.getStartNode()))
			problem.setDepth(0);
		nextToVisit.add(problem);

		while(!nextToVisit.isEmpty()) {
			if(System.currentTimeMillis() > endTime){
				return -1;
			}

			Node current = nextToVisit.remove();
			visitedNodes.add(current);

			nodes += "(" + current.getX() + "," + current.getY() + ") ";

			if(searchSpace.getGoalNode().equals(current)) {
				stopCurrentAlg = true;
				System.out.println("\nGoal Found: " + "(" + current.getX() + "," + current.getY() + ")");
				System.out.println();
				totalCost(searchSpace.getGoalNode());
				System.out.println("Nodes Expanded: "+(visitedNodes.size()-1));
				System.out.println("Nodes in memory, in Queue: "+nextToVisit.size());
				System.out.println("Runtime: " + (System.currentTimeMillis() - startTime) + " ms");
				System.out.print("Path:");
				findPath(searchSpace.getGoalNode());
				System.out.println();
				return nodesExpanded;
			}

			for(Node successor : searchSpace.generateSuccessorsIDS(current, visitedNodes)) {
				if(!visitedNodes.contains(successor)) {
					successor.setDepth(current.getDepth() + 1);
					nodesExpanded++;
					visitedNodes.add(successor);
					nextToVisit.add(successor);
				}
			}
		}
		if(nextToVisit.isEmpty()){
			System.out.println("Path Cost: "+(-1));
			System.out.println("Nodes Expanded: "+visitedNodes.size());
			System.out.println("Not visited yet but in Queue: "+nextToVisit.size());
			System.out.println("Running Time: "+ (System.currentTimeMillis() - startTime) + " ms");
			System.out.println("Path Sequence: Null");

		}
		System.out.println(nodes + "Depth " + limit);
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
				System.out.println();
				System.out.println("Path Cost: "+searchSpace.getGoalNode().getG());
				System.out.println("Nodes Expanded: "+(visited.size()-1));
				System.out.println("Nodes in Memory, in Queue: "+nextToVisit.size());
				System.out.println("Runtime: " + (System.currentTimeMillis() - startTime) + " ms");
				System.out.print("Path:");
				findPath(searchSpace.getGoalNode());
				System.out.println();
				return;
			}
		}
		if(nextToVisit.isEmpty()){
			System.out.println("Path Cost: "+(-1));
			System.out.println("Nodes Expanded: "+visited.size());
			System.out.println("Nodes in memory, in Queue: "+nextToVisit.size());
			System.out.println("Running Time: "+ (System.currentTimeMillis() - startTime) + " ms");
			System.out.println("Path Sequence: Null");

		}
	}
}
