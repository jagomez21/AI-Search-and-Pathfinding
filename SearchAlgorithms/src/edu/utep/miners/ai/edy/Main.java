package edu.utep.miners.ai.edy;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

/**
 * @author Eduardo Lara and Jesus Gomez
 *
 * Main class where graph is setup and
 * algorithms are ran.
 *
 * Class: CS 4320/5314
 * Instructor: Christopher Kiekintveld
 * Assignment: HW3: Search and Pathfinding
 * Date of last modification: 02/23/2020
 **/

public class Main {

	/** 
	 * Reads a txt file and generates a graph/search space.
	 * 
	 * @param fileName Name of the file that we are getting the info from.
	 * @return graph/search space that we are going to use for our algorithms.
	 * */
	public static Graph ReadFile(String fileName) throws IOException {
		Graph execGraph = new Graph();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;

			line = reader.readLine();
			String[] GraphDimension = line.split(" ");
			Node[][] graph = new Node[Integer.parseInt(GraphDimension[0])][Integer.parseInt(GraphDimension[1])];	

			line = reader.readLine();
			String[] setGraphClass = line.split(" ");
			execGraph.setStartX(Integer.parseInt(setGraphClass[0]));
			execGraph.setStartY(Integer.parseInt(setGraphClass[1]));

			line = reader.readLine();																				
			setGraphClass = line.split(" ");
			execGraph.setEndX(Integer.parseInt(setGraphClass[0]));
			execGraph.setEndY(Integer.parseInt(setGraphClass[1]));

			String[] cost;
			int i = 0;
			while((line = reader.readLine()) != null){
				cost = line.split(" ");
				for(int j = 0; j < cost.length; j++){
					graph[i][j] = new Node(Integer.parseInt(cost[j]), i, j);
				}
				i++;
			}
			execGraph.setGraph(graph);
			reader.close();
		} catch(FileNotFoundException e){
			e.printStackTrace();
		}
		return execGraph;
	}

	public static void main(String[] args) throws IOException {
		/* Hardcoded, used for testing purposes.
		 * 
		 * Graph graph = ReadFile("map1.txt");
		 * Algorithms algorithm = new Algorithms(graph);
		 * algorithm.breadthFirst();
		 * algorithm.iterativeDeepSearch();
		 * algorithm.aStarSearch();
		 */

		/*if(args.length > 0 && args.length < 3) {
			if(args.length == 1) {
				System.out.println("Please specify the desire algorithm.");
				System.out.println("Available Algorithms: BFS, IDS  A*");
			} else if(args.length == 2) {
				Graph graph = ReadFile("map1.txt");
				Algorithms algorithm = new Algorithms(graph);
				if(args[1].equalsIgnoreCase("bfs")) {
					algorithm.breadthFirst();
				} else if(args[1].equalsIgnoreCase("ids")) {
					algorithm.iterativeDeepSearch();
				} else if(args[1].equalsIgnoreCase("a*")) {
					algorithm.aStarSearch();
				} else {
					System.out.println("Available Algorithms: BFS, IDS  A*");
				}
			}
		} else {
			System.out.println("Please specify the name of the text file along with the desire algorithm.");
			System.out.println("Available Algorithms: BFS, IDS  A*");
		}*/
	}
}
