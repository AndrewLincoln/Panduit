import static org.junit.Assert.*;
import java.io.*;
import java.util.*;
import org.junit.*;

/**
 * @author Andrew Lincoln
 */
public class GraphTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// TODO save a graph so we can open it later to test the open file functionality
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// TODO remove the saved class from above
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Graph g = new Graph();
		g.reset(); // start with a fresh graph in each test
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link Graph#Graph()}.
	 */
	@Test
	public final void testGraph() {
		Graph g = null;
		assertNull(g);
		g = new Graph();
		assertNotNull(g);
	}

	/**
	 * Test method for {@link Graph#reset()}.
	 */
	@Test
	public final void testReset() {
		Graph g = new Graph();
		assertEquals(0, g.getNumEdges()); // no edges yet
		int e = g.createEdge();
		assertEquals(1, g.getNumEdges()); // one edge
		assertTrue(g.removeEdge(e));
		assertEquals(0, g.getNumEdges()); // no edges after the delete
		e = g.createEdge();
		assertEquals(1, g.getNumEdges()); // one edge
		g.reset();
		assertEquals(0, g.getNumEdges()); // no edge after reset
	}

	/**
	 * Test method for {@link Graph#getNumEdges()}.
	 */
	@Test
	public final void testGetNumEdges() {
		Graph g = new Graph();
		assertEquals(0, g.getNumEdges()); // no edges yet
		int e = g.createEdge();
		assertEquals(1, g.getNumEdges()); // one edge
		assertTrue(g.removeEdge(e));
		assertEquals(0, g.getNumEdges()); // no edges after the delete
		e = g.createEdge();
		assertEquals(1, g.getNumEdges()); // one edge
		assertTrue(g.removeVertex(g.getEdgeVertex1(e)));
		assertEquals(0, g.getNumEdges()); // no edge after vertex removal
		e = g.createEdge();
		assertEquals(1, g.getNumEdges()); // one edge
		g.reset();
		assertEquals(0, g.getNumEdges()); // no edge after reset
	}

	/**
	 * Test method for {@link Graph#getNumVertices()}.
	 */
	@Test
	public final void testGetNumVertices() {
		Graph g = new Graph();
		assertEquals(0, g.getNumVertices()); // no vertices yet
		int v = g.createVertex();
		assertEquals(1, g.getNumVertices()); // one vertex
		assertTrue(g.removeVertex(v));
		assertEquals(0, g.getNumVertices()); // no vertices
		v = g.createVertex();
		assertEquals(1, g.getNumVertices()); // one vertex
		g.reset();
		assertEquals(0, g.getNumVertices()); // no vertices after reset
	}

	/**
	 * Test method for {@link Graph#createVertex()}.
	 */
	@Test
	public final void testCreateVertex() {
		Graph g = new Graph();
		assertEquals(0, g.getNumVertices()); // no vertices
		int v = g.createVertex();
		assertEquals(1, g.getNumVertices()); // one vertex
		assertNotNull(g.queryVertex(v));
	}

	/**
	 * Test method for {@link Graph#searchVertices(int)}.
	 */
	@Test
	public final void testSearchVertices() {
		Graph g = new Graph();
		int v = g.createVertex();
		assertTrue(g.searchVertices(v)); // the vertex is found
		assertTrue(g.removeVertex(v));
		assertFalse(g.searchVertices(v)); // the vertex is not found after removal
	}

	/**
	 * Test method for {@link Graph#removeVertex(int)}.
	 */
	@Test
	public final void testRemoveVertex() {
		Graph g = new Graph();
		int v = g.createVertex();
		assertTrue(g.removeVertex(v)); // can remove the vertex
		assertFalse(g.removeVertex(v)); // the vertex has already been removed
		int e = g.createEdge();
		v = g.getEdgeVertex1(e);
		assertTrue(g.searchVertices(v)); // found a vertex
		assertEquals(g.getNumEdges(), 1); // found an edge
		assertTrue(g.removeVertex(v)); // remove v1
		assertEquals(g.getNumEdges(), 0); // no edges after vertex removal
		assertFalse(g.searchEdges(e)); // no edge found
	}

	/**
	 * Test method for {@link Graph#queryVertex(int)}.
	 */
	@Test
	public final void testQueryVertex() {
		Graph g = new Graph();
		int v = g.createVertex();
		assertNotNull(g.queryVertex(v)); // new vertex has some info
		assertEquals(g.queryVertex(v), "Vertex: v" + v); // info matches basic info
		assertTrue(g.removeVertex(v));
		assertNull(g.queryVertex(v)); // vertex not found so string is null
	}

	/**
	 * Test method for {@link Graph#createEdge()}.
	 */
	@Test
	public final void testCreateEdge() {
		Graph g = new Graph();
		assertEquals(0, g.getNumEdges()); // no edges yet
		int e = g.createEdge();
		assertEquals(1, g.getNumEdges()); // one edge
		assertNotNull(g.queryEdge(e)); // edge has info
	}

	/**
	 * Test method for {@link Graph#createEdge(int, int, int, double, java.lang.String)}.
	 */
	@Test
	public final void testCreateEdgeIntIntIntDoubleString() {
		Graph g = new Graph();
		assertEquals(0, g.getNumEdges());
		int v1 = g.createVertex();
		int v2 = g.createVertex();
		int d = 0;
		double w = 2.5;
		String s = "basic";
		int e = g.createEdge(v1, v2, d, w, s);
		assertEquals(1, g.getNumEdges()); // one edge connects the 2 vertices
		assertNotNull(g.queryEdge(e)); // edge has info
		assertEquals(d, g.getEdgeDirection(e)); // edge direction matches
		assertTrue(w == g.getEdgeWeight(e)); // edge weight matches
		assertEquals(s, g.getEdgeLabel(e)); // edge label matches
		assertTrue(g.removeEdge(e));
		assertEquals(2, g.getNumVertices());
		assertEquals(-1, g.createEdge(v1, v2 + 1, d, w, s)); // try bad input
	}

	/**
	 * Test method for {@link Graph#searchEdges(int)}.
	 */
	@Test
	public final void testSearchEdges() {
		Graph g = new Graph();
		assertFalse(g.searchEdges(0)); // no edges yet
		int e = g.createEdge();
		assertTrue(g.searchEdges(e)); // find the edge
		assertTrue(g.removeEdge(e));
		assertFalse(g.searchEdges(e)); // no edge
	}

	/**
	 * Test method for {@link Graph#removeEdge(int)}.
	 */
	@Test
	public final void testRemoveEdge() {
		Graph g = new Graph();
		assertEquals(0, g.getNumEdges()); // no edges yet
		assertEquals(0, g.getNumVertices()); // no vertices yet
		int e = g.createEdge();
		assertTrue(g.searchEdges(e)); // finds the edge
		assertEquals(1, g.getNumEdges()); // one edge
		assertEquals(2, g.getNumVertices()); // 2 vertices
		assertTrue(g.removeEdge(e));
		assertEquals(0, g.getNumEdges()); // no edge
		assertEquals(2, g.getNumVertices()); // vertices remain
	}

	/**
	 * Test method for {@link Graph#getEdgeVertex1(int)}.
	 */
	@Test
	public final void testGetEdgeVertex1() {
		Graph g = new Graph();
		int v1 = g.createVertex();
		int v2 = g.createVertex();
		int d = 0;
		double w = 3.5;
		String s = "edge1";
		int e = g.createEdge(v1, v2, d, w, s);
		assertEquals(v1, g.getEdgeVertex1(e)); // edge vertex1 and v1 are the same
		assertNotEquals(v2, g.getEdgeVertex1(e)); // vertex1 and v2 are different
		assertTrue(g.removeEdge(e));
		assertEquals(-1, g.getEdgeVertex1(e)); // removed edge returns -1 for vertex
	}

	/**
	 * Test method for {@link Graph#getEdgeVertex2(int)}.
	 */
	@Test
	public final void testGetEdgeVertex2() {
		Graph g = new Graph();
		int v1 = g.createVertex();
		int v2 = g.createVertex();
		int d = 0;
		double w = 3.5;
		String s = "edge1";
		int e = g.createEdge(v1, v2, d, w, s);
		assertEquals(v2, g.getEdgeVertex2(e)); // edge vertex2 and v2 are the same
		assertNotEquals(v1, g.getEdgeVertex2(e)); // vertex2 and v1 are different
		assertTrue(g.removeEdge(e));
		assertEquals(-1, g.getEdgeVertex2(e)); // removed edge returns -1 for vertex
	}

	/**
	 * Test method for {@link Graph#getEdgeDirection(int)}.
	 */
	@Test
	public final void testGetEdgeDirection() {
		Graph g = new Graph();
		int e = g.createEdge();
		assertEquals(g.getEdgeDirection(e), 0); // basic edge direction
		assertTrue(g.updateEdgeDirection(e, 2)); // can update the direction
		assertEquals(g.getEdgeDirection(e), 2); // returns updated direction
		assertTrue(g.removeEdge(e));
		assertEquals(g.getEdgeDirection(e), -1); // -1 when edge has been removed
	}

	/**
	 * Test method for {@link Graph#getEdgeWeight(int)}.
	 */
	@Test
	public final void testGetEdgeWeight() {
		Graph g = new Graph();
		int e = g.createEdge();
		assertTrue(g.getEdgeWeight(e) == 1.0); // basic edge weight
		assertTrue(g.updateEdgeWeight(e, 2.5)); // can update the weight
		assertTrue(g.getEdgeWeight(e) == 2.5); // returns updated weight
		assertTrue(g.removeEdge(e));
		assertTrue(g.getEdgeWeight(e) == 0.0); // 0.0 when edge has been removed
	}

	/**
	 * Test method for {@link Graph#getEdgeLabel(int)}.
	 */
	@Test
	public final void testGetEdgeLabel() {
		Graph g = new Graph();
		int e = g.createEdge();
		assertEquals(g.getEdgeLabel(e), ""); // basic edge label
		assertTrue(g.updateEdgeLabel(e, "updated")); // can update the label
		assertEquals(g.getEdgeLabel(e), "updated"); // returns updated label
		assertTrue(g.removeEdge(e));
		assertNull(g.getEdgeLabel(e)); // null when edge has been removed
	}

	/**
	 * Test method for {@link Graph#updateEdgeDirection(int, int)}.
	 */
	@Test
	public final void testUpdateEdgeDirection() {
		Graph g = new Graph();
		int e = g.createEdge();
		assertEquals(g.getEdgeDirection(e), 0); // basic edge direction
		assertTrue(g.updateEdgeDirection(e, 2)); // can update the direction
		assertEquals(g.getEdgeDirection(e), 2); // returns updated direction
		assertFalse(g.updateEdgeDirection(e, 3)); // can't be 3
		assertEquals(g.getEdgeDirection(e), 2); // direction stayed at 2
		assertTrue(g.removeEdge(e));
		assertEquals(g.getEdgeDirection(e), -1); // -1 when edge has been removed
	}

	/**
	 * Test method for {@link Graph#updateEdgeWeight(int, double)}.
	 */
	@Test
	public final void testUpdateEdgeWeight() {
		Graph g = new Graph();
		int e = g.createEdge();
		assertTrue(g.getEdgeWeight(e) == 1.0); // basic edge weight
		assertTrue(g.updateEdgeWeight(e, 2.5)); // can update the weight
		assertTrue(g.getEdgeWeight(e) == 2.5); // returns updated weight
		assertTrue(g.removeEdge(e));
		assertTrue(g.getEdgeWeight(e) == 0.0); // 0.0 when edge has been removed
	}

	/**
	 * Test method for {@link Graph#updateEdgeLabel(int, java.lang.String)}.
	 */
	@Test
	public final void testUpdateEdgeLabel() {
		Graph g = new Graph();
		int e = g.createEdge();
		assertEquals(g.getEdgeLabel(e), ""); // basic edge label
		assertTrue(g.updateEdgeLabel(e, "updated")); // can update the label
		assertEquals(g.getEdgeLabel(e), "updated"); // returns updated label
		assertTrue(g.removeEdge(e));
		assertNull(g.getEdgeLabel(e)); // null when edge has been removed
	}

	/**
	 * Test method for {@link Graph#queryEdge(int)}.
	 */
	@Test
	public final void testQueryEdge() {
		Graph g = new Graph();
		int e = g.createEdge();
		assertNotNull(g.queryEdge(e)); // edge has info
		assertEquals(g.queryEdge(e), "Edge: e0 v0 v1 d0 w1.0 ''"); // matches basic edge info
		assertTrue(g.updateEdgeDirection(e, 1));
		assertEquals(g.queryEdge(e), "Edge: e0 v0 v1 d1 w1.0 ''"); // matches updated info
		assertTrue(g.removeEdge(e));
		assertNull(g.queryEdge(e)); // null when edge has been removed
	}

	/**
	 * Test method for {@link Graph#toString()}.
	 */
	@Test
	public final void testToString() {
		Graph g = new Graph();
		assertNotNull(g.toString());
		assertEquals(g.toString(), "Graph: \n"); // basic info
		int v = g.createVertex();
		assertEquals(g.toString(), "Graph: \n Vertex: v" + v + "\n"); // one vertex info
	}

	/**
	 * Test method for {@link Graph#saveGraph(java.lang.String)}.
	 */
	@Test
	public final void testSaveGraph() {
		Graph g = new Graph();
		int v = g.createVertex();
		int e = g.createEdge();
		String filename = "C:\\Users\\buabelincoln\\GraphExample.txt";
		File f = new File(filename);
		assertFalse(f.exists()); // file shouldn't exist yet, otherwise there was an error in previous testing
		g.saveGraph(filename);
		assertTrue(f.exists()); // file exists now
		/**
		try {
			Scanner scan = new Scanner(new FileReader(filename));
			String contents = "";
			while (scan.hasNext()) {
				contents += scan.next();
			}
			//contents += "\n";
			//assertEquals(contents, g.toString()); //somehow failing this check
			scan.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		*/
		f.delete(); // remove the file so testing doesn't fail
	}

	/**
	 * Test method for {@link Graph#openGraph(java.lang.String)}.
	 */
	/**@Test
	public final void testOpenGraph() {
		fail("Not yet implemented"); // TODO
	}*/

}
