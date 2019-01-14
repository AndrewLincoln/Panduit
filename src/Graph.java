/**
 * A graph library
 * @author Andrew Lincoln
 * @author andrew.abe.lincoln at gmail.com
 */
import java.io.*;
import java.util.*;
public class Graph {
	private static boolean debug = false; // variable for some debug statements
	private ArrayList<Edge> edges; // array of all edges in the graph
	private ArrayList<Vertex> vertices; // array of all vertices in the graph
	private int numEdges = 0; // total number of edges
	private int numVertices = 0; // total number of vertices
	private static int counterEdges = 0; // counter of all edges (for naming)
	private static int counterVertices = 0; // counter of all vertices (for naming)
	
	/**
	 * Basic constructor for Graph
	 */
	public Graph() {
		this.edges = new ArrayList<Edge>();
		this.vertices = new ArrayList<Vertex>();
	}
	
	/**
	 * Resets all Graph values
	 */
	public void reset() {
		this.edges = new ArrayList<Edge>();
		this.vertices = new ArrayList<Vertex>();
		this.numEdges = 0;
		this.numVertices = 0;
		counterEdges = 0;
		counterVertices = 0;
	}

	/**
	 * Accessor for the ArrayList<Edge> 'edges'
	 * @return the edges
	 */
	private ArrayList<Edge> getEdges() {
		return edges;
	}
	
	/**
	 * Mutator for the ArrayList<Edge> 'edges'
	 * @param edges the edges to set
	 */
	private void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}
	
	/**
	 * Accessor for the ArrayList<Vertex> 'vertices'
	 * @return the vertices
	 */
	private ArrayList<Vertex> getVertices() {
		return vertices;
	}
	
	/**
	 * Mutator for the ArrayList<Vertex> 'vertices'
	 * @param vertices the vertices to set
	 */
	private void setVertices(ArrayList<Vertex> vertices) {
		this.vertices = vertices;
	}
	
	/**
	 * Accessor for the number of edges in this Graph
	 * @return the numEdges
	 */
	public int getNumEdges() {
		return this.numEdges;
	}

	/**
	 * Mutator for the number of edges in this Graph
	 * @param numEdges the number of edges to set
	 */
	private void setNumEdges(int numEdges) {
		this.numEdges = numEdges;
	}

	/**
	 * Accessor for the number of vertices in this Graph
	 * @return the numVertices
	 */
	public int getNumVertices() {
		return numVertices;
	}

	/**
	 * Mutator for the number of vertices in this Graph
	 * @param numVertices the numVertices to set
	 */
	private void setNumVertices(int numVertices) {
		this.numVertices = numVertices;
	}

	/**
	 * Constructor for a Vertex object that is automatically added to the Graph
	 * @return the number of the vertex
	 */
	public int createVertex() {
		Vertex v = new Vertex();
		if (debug) {
			System.out.println("Vertex " + v.getNumber() + " created");
		}
		setNumVertices(getNumVertices() + 1);
		if (debug) {
			System.out.println("Number of Vertices: " + getNumVertices());
		}
		this.vertices.add(v);
		return v.getNumber();
	}
	
	/**
	 * Searches the Graph's vertices for a particular vertex
	 * @param v the vertex number to search for
	 * @return true if the vertex is found, otherwise false
	 */
	public boolean searchVertices(int v) {
		for (int i = 0; i < this.vertices.size(); i++) {
			if (this.vertices.get(i).getNumber() == v) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the Vertex
	 * @param v the vertex number to get
	 * @return the Vertex object if found in the Graph, otherwise null
	 */
	private Vertex getVertex(int v) {
		for (int i = 0; i < this.vertices.size(); i++) {
			if (this.vertices.get(i).getNumber() == v) {
				return this.vertices.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Removes a Vertex and connected Edges from the Graph
	 * @param v the Vertex to remove
	 * @return true if able to remove the Vertex, otherwise false
	 */
	private boolean removeVertex(Vertex v) {
		int size = v.connectedEdges.size();
		for (int i = size - 1; i >= 0; i--) {
			if (debug) {
				System.out.println("i: " + i);
				for (int j = 0; j < this.vertices.size(); j++) {
					System.out.println(this.vertices.get(j).toString());
				}
				for (int j = 0; j < this.edges.size(); j++) {
					System.out.println(this.edges.get(j).toString());
				}
			}
			this.removeEdge(v.connectedEdges.get(i));
		}
		setNumVertices(getNumVertices() - 1);
		return this.vertices.remove(v);
	}
	
	/**
	 * Removes a vertex and connected edges from the Graph
	 * @param v the vertex to remove
	 * @return true if able to remove the vertex, otherwise false;
	 */
	public boolean removeVertex(int v) {
		if (this.searchVertices(v)) {
			boolean removed = this.removeVertex(this.getVertex(v));
			return removed;
		}
		return false;
	}
	
	/**
	 * Gets the Vertex details
	 * @param v the vertex to query
	 * @return the String description of the vertex if found in the Graph, otherwise null
	 */
	public String queryVertex(int v) {
		if (this.searchVertices(v)) {
			return this.getVertex(v).toString();
		}
		return null;
	}
	
	/**
	 * Constructor for a basic Edge object that is automatically added to the Graph
	 * Automatically creates corresponding vertices
	 * @return the number of the edge
	 */
	public int createEdge() {
		Edge e = new Edge();
		this.vertices.add(e.getVertex_1());
		e.getVertex_1().addEdge(e);
		this.vertices.add(e.getVertex_2());
		e.getVertex_2().addEdge(e);
		setNumVertices(getNumVertices() + 2);
		setNumEdges(getNumEdges() + 1);
		this.edges.add(e);
		return e.getNumber();
	}
	
	/**
	 * Constructor for a modified Edge object that is automatically added to the Graph
	 * Vertex numbers must match current vertices in the Graph
	 * @param v1 the first vertex of this Edge
	 * @param v2 the second Vertex of this Edge
	 * @param direction the direction of this Edge
	 * @param weight the weight of this Edge
	 * @param label the label for this Edge
	 * @return the edge number if a new Edge was added to ArrayList<Edge> 'edges', otherwise -1
	 */
	public int createEdge(int v1, int v2, int direction, double weight, String label) {
		if (this.searchVertices(v1) && this.searchVertices(v2)) {
			Edge e = new Edge(this.getVertex(v1), this.getVertex(v2), direction, weight, label);
			this.getVertex(v1).addEdge(e);
			this.getVertex(v2).addEdge(e);
			setNumEdges(getNumEdges() + 1);
			this.edges.add(e);
			return e.getNumber();
		}
		return -1;
	}
	
	/**
	 * Constructor for a modified Edge object that is automatically added to the Graph
	 * Vertices must be current vertices in the Graph
	 * @param v1 the first vertex of this Edge
	 * @param v2 the second Vertex of this Edge
	 * @param direction the direction of this Edge
	 * @param weight the weight of this Edge
	 * @param label the label for this Edge
	 * @return the edge number
	 */
	private int createEdge(Vertex v1, Vertex v2, int direction, double weight, String label) {
		Edge e = new Edge(v1, v2, direction, weight, label);
		v1.addEdge(e);
		v2.addEdge(e);
		setNumEdges(getNumEdges() + 1);
		this.edges.add(e);
		return e.getNumber();
	}
	
	/**
	 * Searches the Graph's edges for a particular edge
	 * @param e the edge number to search for
	 * @return true if the edge is found, otherwise false
	 */
	public boolean searchEdges(int e) {
		for (int i = 0; i < this.edges.size(); i++) {
			if (this.edges.get(i).getNumber() == e) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the Edge
	 * @param e the edge number to get
	 * @return the Edge object if found in the Graph, otherwise null
	 */
	private Edge getEdge(int e) {
		for (int i = 0; i < this.edges.size(); i++) {
			if (this.edges.get(i).getNumber() == e) {
				return this.edges.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Removes an Edge from the Graph
	 * @param e the Edge to remove
	 * @return true if able to remove the Edge, otherwise false
	 */
	private boolean removeEdge(Edge e) {
		setNumEdges(getNumEdges() - 1);
		e.getVertex_1().removeEdge(e);
		e.getVertex_2().removeEdge(e);
		return this.edges.remove(e);
	}
	
	/**
	 * Removes an edge from the Graph
	 * @param e the edge to remove
	 * @return true if able to remove the edge, otherwise false
	 */
	public boolean removeEdge(int e) {
		if (this.searchEdges(e)) {
			return this.removeEdge(this.getEdge(e));
		}
		return false;
	}
	
	/**
	 * Gets the first vertex of the specified edge
	 * @param e the edge to get
	 * @return the first vertex of the edge, otherwise -1
	 */
	public int getEdgeVertex1(int e) {
		if (this.searchEdges(e)) {
			return this.getEdge(e).getVertex_1().getNumber();
		}
		return -1;
	}
	
	/**
	 * Gets the second vertex of the specified edge
	 * @param e the edge to get
	 * @return the second vertex of the edge, otherwise -1
	 */
	public int getEdgeVertex2(int e) {
		if (this.searchEdges(e)) {
			return this.getEdge(e).getVertex_2().getNumber();
		}
		return -1;
	}
	
	/**
	 * Gets the direction of the specified edge
	 * @param e the edge to get
	 * @return the direction of the edge, otherwise -1
	 */
	public int getEdgeDirection(int e) {
		if (this.searchEdges(e)) {
			return this.getEdge(e).getDirection();
		}
		return -1;
	}
	
	/**
	 * Gets the weight of the specified edge
	 * @param e the edge to get
	 * @return the weight of the edge, otherwise 0.0
	 */
	public double getEdgeWeight(int e) {
		if (this.searchEdges(e)) {
			return this.getEdge(e).getWeight();
		}
		return 0.0;
	}
	
	/**
	 * Gets the label of the specified edge
	 * @param e the edge to get
	 * @return the label of the edge, otherwise null
	 */
	public String getEdgeLabel(int e) {
		if (this.searchEdges(e)) {
			return this.getEdge(e).getLabel();
		}
		return null;
	}
	
	/**
	 * Mutator for an edge direction
	 * @param e the edge to edit
	 * @param d the new direction for the edge
	 * @return true if able to change the direction, otherwise false
	 */
	public boolean updateEdgeDirection(int e, int d) {
		if (this.searchEdges(e)) {
			return this.getEdge(e).setDirection(d);
		}
		return false;
	}
	
	/**
	 * Mutator for an edge weight
	 * @param e the edge to edit
	 * @param w the new weight for the edge
	 * @return true if able to change the weight, otherwise false
	 */
	public boolean updateEdgeWeight(int e, double w) {
		if (this.searchEdges(e)) {
			return this.getEdge(e).setWeight(w);
		}
		return false;
	}
	
	/**
	 * Mutator for an edge label
	 * @param e the edge to edit
	 * @param s the new label for the edge
	 * @return true if able to change the label, otherwise false
	 */
	public boolean updateEdgeLabel(int e, String s) {
		if (this.searchEdges(e)) {
			return this.getEdge(e).setLabel(s);
		}
		return false;
	}
	
	/**
	 * Gets the Edge details
	 * @param e the edge to query
	 * @return the String description of the edge if found in the Graph, otherwise null
	 */
	public String queryEdge(int e) {
		if (this.searchEdges(e)) {
			return this.getEdge(e).toString();
		}
		return null;
	}
	
	/**
	 * Creates a String that represents this Graph
	 * @return The String representation of this Graph
	 */
	public String toString() {
		String s = "Graph: \n";
		for (int i = 0; i < this.vertices.size(); i++) {
			s += " " + this.vertices.get(i).toString() + "\n";
		}
		for (int i = 0; i < this.edges.size(); i++) {
			s += " " + this.edges.get(i).toString() + "\n";
		}
		// TODO add the extra variables for info to save to file
		return s;
	}
	
	/**
	 * Saves the current Graph to disk
	 * @param fullFileName full filename (including directory) to save the Graph to
	 * @return true if the Graph has been saved, otherwise false
	 */
	public boolean saveGraph(String fullFileName) {
		File f = new File(fullFileName);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		try {
			Writer w = new FileWriter(f);
			w.write(this.toString()); // write the graph to the file
			//w.notifyAll(); // for multiple threads, needs some more work here TODO
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Opens the specified Graph from disk
	 * @param fullFileName full filename (including directory) to open the Graph from
	 * @return true if the Graph has been opened, otherwise false
	 */
	/** still working on the implementation, comment out for now
	 * public boolean openGraph(String fullFileName) {
		File f = new File(fullFileName);
		if (!f.exists()) {
			return false; // file does not exist, no need to go any further
		}
		if (!f.canRead()) {
			return false; // can't read the file, no need to go any further
		}
		Scanner reader;
		try {
			reader = new Scanner(new FileReader(fullFileName));
			String line = "";
			while (reader.hasNext()) {
				line = reader.next().trim();
				if (line.startsWith("Graph")) {
					// can ignore
				}
				else if (line.startsWith("Vertex")) {
					// get vertex number then edge array (if any)
					int v1, v2 = line.length();
					v1 = line.indexOf('v', line.indexOf(':')) + 1;
					if (line.indexOf('e', line.indexOf(':')) != -1) {
						// there is at least one edge
						v2 = line.indexOf('e', line.indexOf(':')) - 1;
					}
					int vertexNum = Integer.parseInt(line.substring(v1, v2));
					// now need to get the edge array
					
					// create the vertex object
					
				}
				else if (line.startsWith("Edge")) {
					
				}
				else {
					// not sure what it is
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}*/
	
	
	
	
	/**
	 * Class Edge
	 * A basic building block in Graph
	 */
	private class Edge {
		private int number; // for naming
		private Vertex vertex_1, vertex_2; // vertex for each end of the edge
		private int direction; // direction of the edge (0 - no direction, 1 - vertex_1 towards vertex_2, 2 - vertex_2 towards vertex_1)
		private double weight; // weight for the edge (can be negative, zero, or positive)
		private String label; // label for the edge
		
		/**
		 * Basic constructor for Edge
		 */
		public Edge() {
			this.setNumber(counterEdges++);
			this.vertex_1 = new Vertex();
			this.vertex_2 = new Vertex();
			this.setDirection(0);
			this.setWeight(1.0);
			this.setLabel("");
		}
		
		/**
		 * Constructor for Edge with non-basic values
		 * @param v1 the first Vertex of this Edge
		 * @param v2 the second Vertex of this Edge
		 * @param startingDirection the initial direction of this Edge
		 * @param startingWeight the initial weight of this Edge
		 * @param startingLabel the initial label of this Edge
		 */
		public Edge(Vertex v1, Vertex v2, int startingDirection, double startingWeight, String startingLabel) {
			this.setNumber(counterEdges++);
			this.vertex_1 = v1;
			this.vertex_2 = v2;
			this.setDirection(startingDirection);
			this.setWeight(startingWeight);
			this.setLabel(startingLabel);
		}

		/**
		 * Accessor for the number for this Edge
		 * @return the number of this Edge
		 */
		public int getNumber() {
			return number;
		}

		/**
		 * Mutator for the number for this Edge
		 * @param num the number to set
		 */
		public void setNumber(int num) {
			this.number = num;
		}

		/**
		 * Accessor for vertex_1
		 * @return the vertex_1
		 */
		public Vertex getVertex_1() {
			return vertex_1;
		}

		/**
		 * Mutator for vertex_1
		 * @param v1 the vertex_1 to set
		 */
		public void setVertex_1(Vertex v1) {
			this.vertex_1 = v1;
		}

		/**
		 * Accessor for vertex_2
		 * @return the vertex_2
		 */
		public Vertex getVertex_2() {
			return vertex_2;
		}

		/**
		 * Mutator for vertex_2
		 * @param v2 the vertex_2 to set
		 */
		public void setVertex_2(Vertex v2) {
			this.vertex_2 = v2;
		}
		
		/**
		 * Accessor for the direction of this Edge
		 * @return the direction
		 */
		public int getDirection() {
			return direction;
		}

		/**
		 * Mutator for the direction
		 * @param dir the direction to set
		 * @return true if the direction was updated, otherwise false
		 */
		public boolean setDirection(int dir) {
			if (dir < 0 || dir > 2) {
				return false;
			}
			this.direction = dir;
			return true;
		}
		
		/**
		 * Determines if the edge is directional or not
		 * 0: bidirectional (no direction)
		 * 1: from vertex_1 to vertex_2
		 * 2: from vertex_2 to vertex_1
		 * @return true if direction is 1 or 2, false if direction is 0
		 */
		public boolean hasDirection()
		{
			if (this.direction == 0) {
				return false;
			}
			return true; //has a direction
		}

		/**
		 * Accessor for weight
		 * @return the weight of this Edge
		 */
		public double getWeight() {
			return weight;
		}

		/**
		 * Mutator for weight
		 * @param w the weight to set
		 * @return true if the weight updated (currently cannot return false)
		 */
		public boolean setWeight(double w) {
			this.weight = w;
			return true;
		}

		/**
		 * Accessor for the label
		 * @return the label of this Edge
		 */
		public String getLabel() {
			return label;
		}

		/**
		 * Mutator for the label
		 * @param newLabel the label to set
		 * @return true if the label updated (currently cannot return false)
		 */
		public boolean setLabel(String newLabel) {
			this.label = newLabel;
			return true;
		}
		
		/**
		 * Checks if this Edge is connected to the specified Vertex
		 * @param v the Vertex to check connection to
		 * @return true if this Edge is connected to the specified Vertex, otherwise false
		 */
		public boolean isConnectedTo(Vertex v) {
			return (( v == vertex_1 ) || ( v == vertex_2 ));
		}
		
		/**
		 * Creates a String that represents this Edge
		 * @return The String representation of this Edge
		 */
		public String toString() {
			String s = "Edge: ";
			s += "e" + number;
			s += " v" + vertex_1.getNumber();
			s += " v" + vertex_2.getNumber();
			s += " d" + direction;
			s += " w" + weight;
			s += " '" + label + "'";
			return s;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Class Vertex
	 * A basic building block in Graph
	 */
	private class Vertex {
		private int number; // used as the name for the vertex
		private ArrayList<Edge> connectedEdges; // array of edges that are connected to the vertex

		/**
		 * Basic constructor for Vertex
		 */
		public Vertex() {
			this.setNumber(counterVertices++); // name the vertex automatically
			this.connectedEdges = new ArrayList<Edge>(); // empty at initialization
		}
		
		/**
		 * Accessor for the number
		 * @return the number of this Vertex
		 */
		public int getNumber() {
			return number;
		}

		/**
		 * Mutator for the number
		 * @param num the number to set for this Vertex
		 */
		private void setNumber(int num) {
			this.number = num;
		}
		
		/**
		 * Accessor for the connected Edges ArrayList<Edge>
		 * @return the connected Edges ArrayList<Edge>
		 */
		public ArrayList<Edge> getConnectedEdges() {
			return connectedEdges;
		}
		
		/**
		 * Mutator for the connected Edges ArrayList<Edge>
		 * @param ce the connected Edges ArrayList<Edge> to set
		 */
		private void setConnectedEdges(ArrayList<Edge> ce) {
			this.connectedEdges = ce;
		}
		
		/**
		 * Adds an Edge to this Vertex
		 * @param e Edge to add
		 * @return true if the Edge is added to this Vertex, otherwise false
		 */
		public boolean addEdge(Edge e) {
			return this.connectedEdges.add(e);
		}
		
		/**
		 * Removes an Edge from this Vertex
		 * @param e Edge to remove
		 * @return true if Edge is removed from this Vertex, otherwise false
		 */
		public boolean removeEdge(Edge e) {
			return this.connectedEdges.remove(e);
		}
		
		/**
		 * Creates a String that represents this Vertex
		 * @return The String representation of this Vertex
		 */
		public String toString() {
			String s = "Vertex: ";
			s += "v" + this.number;
			for (int i = 0; i < this.connectedEdges.size(); i++) {
				s += " e" + this.connectedEdges.get(i).getNumber();
			}
			return s;
		}
	}
	
}
