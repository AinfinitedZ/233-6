import org.junit.Test;

import static org.junit.Assert.*;
public class WeightGraphTest {

	@Test
	public void testAddNode() throws Exception {
		WeightGraph graph = new WeightGraph(10);
		boolean isSuccA = graph.addNode("A");
		boolean isSuccB = graph.addNode("B");
		boolean isSuccR = graph.addNode("A");
		assertTrue(isSuccA);													// One should successfully insert a vertex into empty graph.
		assertTrue(isSuccB);											
		assertFalse(isSuccR);													// One should not successfully insert a existed vertex.
		boolean isSuccCDE = graph.addNode(new String[]{"C", "D", "E"});
		boolean isSuccACD = graph.addNode(new String[]{"A", "C", "D"});
		assertTrue(isSuccCDE);													// return true only if all vertex is successfully inserted
		assertFalse(isSuccACD);													// return false if any vertex is not successfully inserted.
	}

	@Test
	public void testAddWeightEdge() throws Exception {
		WeightGraph graph = new WeightGraph(10);
		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("C");
		graph.addNode("D");
		boolean isSuccAB = graph.addWeightEdge("A", "B", 4);
		boolean isSuccAE = graph.addWeightEdge("A", "E", 3);
		boolean isSuccRe = graph.addWeightEdge("A", "B", 4);
		assertTrue(isSuccAB);													// One should successfully insert a edge with two existed vertex
		assertFalse(isSuccAE);													// One should not successfully insert a edge without two existed vertexs
		assertFalse(isSuccRe);													// One should not successfully insert a edge when it is repeated. 
		boolean isSuccAC = graph.addWeightEdge("A", new String[]{"C"}, new int[]{3});			
		boolean isSuccABAC = graph.addWeightEdge("A", new String[]{"B", "C"}, new int[]{3, 5});
		boolean isSuccABAD = graph.addWeightEdge("A", new String[]{"B", "D"}, new int[]{4, 5});
		assertTrue(isSuccAC);													// return true only if all edges is inserted successful
		assertFalse(isSuccABAC);												// return false if any edges is not successfully inserted. 
		assertFalse(isSuccABAD);
	}

	@Test
	public void testRemoveNode() throws Exception {
		WeightGraph graph = new WeightGraph(10);
		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("D");
		graph.addNode("E");
		boolean isSuccA = graph.removeNode("A");
		boolean isSuccC = graph.removeNode("C");
		assertTrue(isSuccA);													// if vertex is existed, it would be removed
		assertFalse(isSuccC);													// vice versa.
		boolean isSuccD = graph.removeNode(new String[]{"D"});		
		boolean isSuccAE = graph.removeNode(new String[]{"A", "E"});
		assertTrue(isSuccD);													// if vertex is existed, it would be removed
		assertFalse(isSuccAE);													// vice versa.
	}

	@Test
	public void testShortestPath() throws Exception {
		WeightGraph graph = new WeightGraph(10);
		graph.addNode(new String[]{"A", "B", "C", "D", "E", "F", "G"});			// graph as on the graph.
		graph.addWeightEdge("A", new String[]{"B", "D"}, new int[]{2,1});
		graph.addWeightEdge("B", new String[]{"D", "E"}, new int[]{3,10});
		graph.addWeightEdge("C", new String[]{"A", "F"}, new int[]{4,5});
		graph.addWeightEdge("D", new String[]{"C", "E", "F", "G"}, new int[]{2,2,8,4});
		graph.addWeightEdge("E", new String[]{"G"}, new int[]{6});
		graph.addWeightEdge("F", new String[]{}, new int[]{});
		graph.addWeightEdge("G", new String[]{"F"}, new int[]{1});
		String[] path = graph.shortestPath("A", "E");
		assertArrayEquals(new String[]{"E", "D", "A"}, path);					// if shortest path is found
		path = graph.shortestPath("A", "C");
		assertArrayEquals(new String[]{"C", "D", "A"}, path);
		path = graph.shortestPath("A", "H");
		assertArrayEquals(new String[]{}, path);								// if not
	}	

	@Test
	public void testSecondShortestPath() throws Exception {
		WeightGraph graph = new WeightGraph(10);
		graph.addNode(new String[]{"A", "B", "C", "D", "E", "F", "G"});
		graph.addWeightEdge("A", new String[]{"B", "D"}, new int[]{2,1});
		graph.addWeightEdge("B", new String[]{"D", "E"}, new int[]{3,10});
		graph.addWeightEdge("C", new String[]{"A", "F"}, new int[]{4,5});
		graph.addWeightEdge("D", new String[]{"C", "E", "F", "G"}, new int[]{2,2,8,4});
		graph.addWeightEdge("E", new String[]{"G"}, new int[]{6});
		graph.addWeightEdge("F", new String[]{}, new int[]{});
		graph.addWeightEdge("G", new String[]{"F"}, new int[]{1});
		String[] path = graph.secondShortestPath("A", "E");
		assertArrayEquals(new String[]{"E", "D", "B", "A"}, path); 
		path = graph.secondShortestPath("A", "C");					 // if shortest path is found
		assertArrayEquals(new String[]{"C", "D", "B", "A"}, path);
		path = graph.secondShortestPath("A", "H");
		assertArrayEquals(new String[]{}, path);								// if not
	}

}
