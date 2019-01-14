/**
 * Driver class to showcase the Graph library
 * @author Andrew Lincoln
 * @author andrew.abe.lincoln at gmail.com
 */
import java.util.*;
public class Driver {
	private static boolean debug = true; // variable for some debug statements
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to the Graph Library!");
		Graph graph = new Graph();
		Scanner scanner = new Scanner(System.in);
		String input = "", output = "";
		int v1, v2, e, result;
		double w;
		boolean keepGraphing = true, foo;
		do {
			System.out.println("");
			System.out.println("Main Menu. What would you like to do?");
			System.out.println("C - Create an edge or vertex");
			System.out.println("E - Edit an edge or vertex");
			System.out.println("R - Remove an edge or vertex");
			System.out.println("Q - Query an edge or vertex");
			System.out.println("S - Save graph to disk");
			//System.out.println("O - Open graph from disk");
			System.out.println("N - New graph (will wipe all unsaved progress)");
			System.out.println("any other input will exit the program");
			System.out.println("");
			input = scanner.next().toUpperCase();
			if (debug) {
				System.out.println("Input is " + input);
			}
			if (input.equals("C")) { // create an edge or vertex
				System.out.println("Edge or Vertex?");
				input = scanner.next().toUpperCase();
				if (debug) {
					System.out.println("Input is " + input);
				}
				if (input.equals("E")) { // create edge
					if (graph.getNumVertices() < 2) { // create a new edge with new vertices
						result = graph.createEdge();
					}
					else {
						System.out.println("New edge or Connect vertices?");
						input = scanner.next().toUpperCase();
						if (input.equals("N")) { // new edge and vertices, disconnected from the rest of the graph
							result = graph.createEdge();
						} 
						else if (input.equals("C")) { // new edge connected to existing vertices
							System.out.println("Enter Vertex 1, Vertex 2, direction, weight, and label on seperate lines");
							v1 = scanner.nextInt();
							v2 = scanner.nextInt();
							e = scanner.nextInt();
							w = scanner.nextDouble();
							input = scanner.next();
							if ((graph.searchVertices(v1)) && (graph.searchVertices(v2))) {
								result = graph.createEdge(v1, v2, e, w, input);
							} else {
								System.out.println("Incorrect values");
							}
						} 
						else {
							System.out.println("Incorrect input");
						}
					}
				} 
				else if (input.equals("V")) { // create vertex
					result = graph.createVertex();
				} 
				else {
					System.out.println("Incorrect input");
				}
			}
			else if (input.equals("E")) { // edit an edge or vertex
				System.out.println("Edge or Vertex?");
				input = scanner.next().toUpperCase();
				if (debug)
					System.out.println("Input is " + input);
				if (input.equals("E")) { // edit an edge
					System.out.println("Edge number?");
					e = scanner.nextInt();
					if (graph.searchEdges(e)) {
						System.out.println("Direction, Weight, or Label?");
						input = scanner.next().toUpperCase();
						if (input.equals("D")) {
							System.out.println("Enter direction (0 - bidirectional, 1 - from v1 to v2, 2 - from v2 to v1)");
							v1 = scanner.nextInt();
							foo = graph.updateEdgeDirection(e, v1);
						}
						else if (input.equals("W")) {
							System.out.println("Enter weight");
							w = scanner.nextDouble();
							foo = graph.updateEdgeWeight(e, w);
						}
						else if (input.equals("L")) {
							System.out.println("Enter label");
							input = scanner.next();
							foo = graph.updateEdgeLabel(e, input);
						}
						else {
							System.out.println("Incorrect input");
						}
					}
					else {
						System.out.println("Incorrect Edge number");
					}
					
				} 
				else if (input.equals("V")) { // edit a vertex
					// currently nothing to edit
					if (debug) {
						System.out.println("Currently nothing to edit in a Vertex (can only create and remove)");
					}
				} 
				else {
					System.out.println("Incorrect input");
				}
			}
			else if (input.equals("R")) { // remove an edge or vertex
				System.out.println("Edge or Vertex?");
				input = scanner.next().toUpperCase();
				if (debug)
					System.out.println("Input is " + input);
				if (input.equals("E")) { // remove an edge
					if (graph.getNumEdges() > 0) {
						System.out.println("Enter Edge number to remove");
						e = scanner.nextInt();
						foo = graph.removeEdge(e);
					}
					else {
						System.out.println("No edges to remove");
					}
				} 
				else if (input.equals("V")) { // remove a vertex
					if (graph.getNumVertices() > 0) {
						System.out.println("Enter Vertex number to remove");
						v1 = scanner.nextInt();
						foo = graph.removeVertex(v1);
					}
					else {
						System.out.println("No vertices to remove");
					}
				} 
				else {
					System.out.println("Incorrect input");
				}
			}
			else if (input.equals("Q")) { // query an edge or vertex
				System.out.println("Edge or Vertex?");
				input = scanner.next().toUpperCase();
				if (debug)
					System.out.println("Input is " + input);
				if (input.equals("E")) { // query an edge
					if (graph.getNumVertices() > 0) {
						System.out.println("Edge number?");
						e = scanner.nextInt();
						if (graph.searchEdges(e)) {
							output = graph.queryEdge(e);
						}
						else {
							System.out.println("Incorrect Edge number");
						}
					}
					else {
						System.out.println("No edges to query");
					}
				} 
				else if (input.equals("V")) { // query a vertex
					if (graph.getNumVertices() > 0) {
						System.out.println("Vertex number?");
						v1 = scanner.nextInt();
						if (graph.searchVertices(v1)) {
							output = graph.queryVertex(v1);
						}
						else {
							System.out.println("Incorrect Vertex number");
						}
					}
					else {
						System.out.println("No vertices to query");
					}
				} 
				else {
					System.out.println("Incorrect input");
				}
			}
			else if (input.equals("S")) { // save the graph
				System.out.println("Enter the full path, filename, and extension where you want to save the graph");
				input = scanner.next();
				if (debug) {
					System.out.println("File path and name entered was: " + input);
				}
				foo = graph.saveGraph(input);
			}
//			else if (input.equals("O")) { // open an existing graph
//				System.out.println("Enter the full path, filename, and extension of graph you wish to open");
//				input = scanner.next();
//				if (debug) {
//					System.out.println("File path and name entered was: " + input);
//				}
//				result = graph.openGraph(input); 
//			}
			else if (input.equals("N")) { // new graph
				//graph = null;
				//graph = new Graph();
				graph.reset();
				System.out.println("New graph created");
			}
			else {
				System.out.println("Your input did not match any of the menu items, therefore exiting");
				keepGraphing = false;
			}
			if (debug) {
				System.out.println(graph.toString());
				System.out.println(graph.getNumVertices());
				System.out.println(graph.getNumEdges());
			}
		} while (keepGraphing);
		scanner.close();
		System.out.println("Thanks for stopping by!");
	}	

}
