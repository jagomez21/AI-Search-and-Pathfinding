package edu.utep.miners.ai.edy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class Algorithms {
	
	private Graph searchSpace;
	private boolean stopCurrentAlg = false;

	//Constructor
	public Algorithms (Graph graph) {
		searchSpace = graph;
	}
	
	public void displayGraph(){												//This will display the graph/cost of all the nodes. the empty spaces are the places where you can not go through
		System.out.println("Start Coordinates ("+searchSpace.startx+","+searchSpace.starty+")");
		System.out.println("End Coordinates ("+searchSpace.endx+","+searchSpace.endy+")");
		System.out.println("Traverse Cost:");
		for(int i=0; i< searchSpace.nodes.length;i++){
			for(int j=0; j<searchSpace.nodes[i].length; j++){
				if(!searchSpace.nodes[i][j].impassable){
					System.out.print(searchSpace.nodes[i][j].cost+" ");
				}else{
					System.out.print("  ");
				}
			}
			System.out.println();
		}
	}
	
	/*this method will generate the successors*/
	
	public void successor(){
		for(int i=0; i<searchSpace.nodes.length;i++){
			for(int j=0; j<searchSpace.nodes[i].length;j++){
				
				if( ((i-1)>=0) && ((i-1)<searchSpace.nodes.length) && (searchSpace.nodes[i-1][j].impassable==false) ){					//below
					searchSpace.nodes[i][j].setBelowSuccessor(searchSpace.nodes[i-1][j]);
				}
				if( ((j-1)>=0) && ((j-1)<searchSpace.nodes[i].length) && (searchSpace.nodes[i][j-1].impassable==false) ){					//left
					searchSpace.nodes[i][j].setLeftSuccessor(searchSpace.nodes[i][j-1]);
				}
				if( ((j+1)>=0) && ((j+1)<searchSpace.nodes[i].length) && (searchSpace.nodes[i][j+1].impassable==false) ){					//right
					searchSpace.nodes[i][j].setRightSuccessor(searchSpace.nodes[i][j+1]);
				}
				if( ((i+1)>=0) && ((i+1)<searchSpace.nodes.length) && (searchSpace.nodes[i+1][j].impassable==false) ){					//top
					searchSpace.nodes[i][j].setTopSuccessor(searchSpace.nodes[i+1][j]);
				}
				
			}
		}
	}
	/*prove that the successors are working correctly*/
	public void print_successors(int i, int j){
		System.out.println("successors:");
		System.out.print("current:"+searchSpace.nodes[i][j].cost);
		if(searchSpace.nodes[i][j].successors[3] != null){															//top
			System.out.print(" top: "+searchSpace.nodes[i][j].successors[3].cost+" ");
		}else{
			System.out.print(" top: "+searchSpace.nodes[i][j].successors[3]+" ");
		}
		if(searchSpace.nodes[i][j].successors[0] != null){															//below
			System.out.print(" below: "+searchSpace.nodes[i][j].successors[0].cost+" ");
		}else{
			System.out.print(" below: "+searchSpace.nodes[i][j].successors[0]+" ");
		}
		if(searchSpace.nodes[i][j].successors[1] != null){															//left
			System.out.print(" left: "+searchSpace.nodes[i][j].successors[1].cost+" ");
		}else{
			System.out.print(" left: "+searchSpace.nodes[i][j].successors[1]+" ");
		}
		if(searchSpace.nodes[i][j].successors[2] != null){															//right
			System.out.print(" right: "+searchSpace.nodes[i][j].successors[2].cost+" ");
		}else{
			System.out.print(" right: "+searchSpace.nodes[i][j].successors[2]+" ");
		}
		System.out.println();
	}
	
	public void findPath(Node graph){
		System.out.print("Reverse path: ");
		while(graph.comingFrom !=null){
			System.out.println("X: ("+graph.coordenateX+", "+graph.coordenateY+") Cost: "+graph.cost);
			graph = graph.comingFrom;
		}
		System.out.println("X: ("+graph.coordenateX+", "+graph.coordenateY+") Cost: "+graph.cost);

	}
	
	/* breadth-first search algorithm*/
	public void BreadtFirst(){
		PriorityQueue<Node> nexttoVisit =new PriorityQueue<>(new Comparator<Node>(){
			@Override
			public int compare(Node fNode, Node sNode){											//put the smallest in front first
				return fNode.cost - sNode.cost;													// put sNode fist: compare a big against a smaller will give a bigger value than 0
																								//leave fNode first: compare a small number against a bigger will give a smaller number than 0
			}
		});
		int totalCost = 0;
		
		//for(int i=0; i<searchSpace.nodes[1][3].successors.length; i++){										//check if this queue works
		//	q.add(searchSpace.nodes[1][3].successors[i]);
		//}
		//while(!q.isEmpty()){
		//	System.out.println(q.remove().cost);
		//}
		List<Node> visitedPos = new ArrayList<>();
		
		nexttoVisit.add(searchSpace.nodes[searchSpace.startx][searchSpace.starty]);							//start
		
		while(!nexttoVisit.isEmpty()){
			
																	//remove the node to continue with the node
			Node currentPos = nexttoVisit.remove();
			
			visitedPos.add(currentPos);								//now the current position will count as visited
			
			if(currentPos.equals(searchSpace.nodes[searchSpace.endx][searchSpace.endy])){					//Check if we arrived to the goal
				System.out.println("The path was found" );
				findPath(searchSpace.nodes[searchSpace.endx][searchSpace.endy]);
				return;
			}
			Node[] successorsOfCurrent = currentPos.successors;
			for(int i=0; i< successorsOfCurrent.length;i++){
				//if(successorsOfCurrent[i] == null){
				//	System.out.println("Position: "+currentPos.cost+" fixing "+successorsOfCurrent[i]);
				//}else{
				//	System.out.println("Position: "+currentPos.cost+" fixing "+successorsOfCurrent[i].cost);
				//}
				if(!visitedPos.contains(successorsOfCurrent[i]) && successorsOfCurrent[i] !=null ){
					nexttoVisit.add(successorsOfCurrent[i]);		//add the next node that will need to visit
					visitedPos.add(successorsOfCurrent[i]);			//add the nodes that are in the nexttoVisit already so we dont repeat
					successorsOfCurrent[i].comingFrom = currentPos;	//set the nodes where they are coming from
					
				}
			}
			
			
		}
		
	}
	
	
	public void iterativeDeepSearch(Node start) {
		System.out.println("Iterative Deep Search: \n");
		System.out.println("costo: " + start.cost);
		
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
		problem.depth = 0;
		fringe.push(problem);

		while(!fringe.isEmpty()){
			if(System.currentTimeMillis() > endTime){
				return -1;
			}

			Node current = fringe.pop();
			totalCost += current.cost;
			nodes += "(" + current.coordenateX + "," + current.coordenateY + ") ";

			if(searchSpace.getGoalNode().equals(current)){
				System.out.println(nodes);
				stopCurrentAlg = true;
				System.out.println("\nGoal Found: " + "(" + current.coordenateX + "," + current.coordenateY + ")");
				System.out.println("Runtime: " + (System.currentTimeMillis() - startTime) + " ms");
				System.out.println("Total Cost: " + totalCost);
				System.out.println("Nodes in Memory: " + visitedNodes.size());
				return nodesExpanded;
			}

			if(current.depth >= limit){
				break;
			}

			for(Node successor : searchSpace.generateSuccessors(current)) {
				successor.depth = (current.depth + 1);
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
