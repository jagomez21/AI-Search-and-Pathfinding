package edu.utep.miners.ai.jesus;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

		public static Graph readFile(String fileName) {
			String line;
			String text = "";
			Graph graph = new Graph();

			try {
				FileReader fr = new FileReader(fileName);
				BufferedReader buffer = new BufferedReader(fr);
				while ((line = buffer.readLine()) != null) {
					text += line + " ";
				}
				buffer.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 

			text = text.substring(0, text.length() - 1);
			
			String[] strNums = text.split("\\s+");
			List<Integer> values = new ArrayList<Integer>();

			for(int i = 0; i < strNums.length; i++) {
				values.add(Integer.parseInt(strNums[i]));
			}

			Node[][] nodes = new Node[values.get(0)][values.get(1)];

			graph.setStartX(values.get(2));
			graph.setStartY(values.get(3));

			graph.setGoalX(values.get(4));
			graph.setGoalY(values.get(5));

			int counter = 6;
			for(int i = 0; i < nodes.length; i++) {
				for(int j = 0; j < nodes[i].length; j++) {
					nodes[i][j] = new Node(values.get(counter), i, j);
					counter++;
				}
			}
			graph.setNodes(nodes);
			return graph;
		}

		public static void main(String[] args) {
			Scanner input = new Scanner(System.in);
			System.out.println("Whats the name of the file?");
			String fileName = input.next();
			input.close();
			Graph graph = readFile(fileName);

			Algorithms alg = new Algorithms(graph);
			alg.iterativeDeepSearch(graph.getStartNode());
		} 

	}
