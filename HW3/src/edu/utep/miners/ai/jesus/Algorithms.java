package edu.utep.miners.ai.jesus;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Algorithms {

	private Graph searchSpace;
	private boolean stopCurrentAlg = false;

	//Constructor
	public Algorithms (Graph graph) {
		searchSpace = graph;
	}

	public void iterativeDeepSearch(Node start) {
		System.out.println("Iterative Deep Search: \n");
		System.out.println("costo: " + start.getCost());
		
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

	public int depthSearch(Node problem, int limit) {
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
}
