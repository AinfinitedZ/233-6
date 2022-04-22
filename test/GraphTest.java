import static org.junit.Assert.*;

import org.junit.Test;

public class GraphTest {

	@Test
	public void testAddNode() throws Exception {
		Graph graph = new Graph(10);
		boolean isSuccA = graph.addNode("A");
		boolean isSuccB = graph.addNode("B");
		boolean isSuccR = graph.addNode("A");
		assertTrue(isSuccA);
		assertTrue(isSuccB);
		assertFalse(isSuccR);
		boolean isSuccCDE = graph.addNode(new String[]{"C", "D", "E"});
		boolean isSuccACD = graph.addNode(new String[]{"A", "C", "D"});
		assertTrue(isSuccCDE);
		assertFalse(isSuccACD);
	}


	@Test
	public void testAddEdge() throws Exception {
		Graph graph = new Graph(10);
		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("C");
		graph.addNode("D");
		boolean isSuccAB = graph.addEdge("A", "B");
		boolean isSuccAC = graph.addEdge("A", "C");
		boolean isSuccRe = graph.addEdge("A", "B");
		assertTrue(isSuccAB);
		assertTrue(isSuccAC);
		assertFalse(isSuccRe);
		boolean isSuccAD = graph.addEdge("A", new String[]{"D"});
		boolean isSuccABAC = graph.addEdge("A", new String[]{"B", "C"});
		boolean isSuccABAD = graph.addEdge("A", new String[]{"B", "D"});
		assertTrue(isSuccAD);
		assertFalse(isSuccABAC);
		assertFalse(isSuccABAD);
	}


	@Test
	public void testRemoveNode() throws Exception {
		Graph graph = new Graph(10);
		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("D");
		graph.addNode("E");
		boolean isSuccA = graph.removeNode("A");
		boolean isSuccC = graph.removeNode("C");
		assertTrue(isSuccA);
		assertFalse(isSuccC);
		boolean isSuccD = graph.removeNode(new String[]{"D"});
		boolean isSuccAE = graph.removeNode(new String[]{"A", "E"});
		assertTrue(isSuccD);
		assertFalse(isSuccAE);
	}

	@Test
	public void testDFS(){
		Graph graph = new Graph(1000);
		graph.addNode(new String[]{"A", "B", "C", "D"});
		graph.addEdge("A", new String[]{"B", "C", "D"});
		graph.addEdge("B", new String[]{"C"});
		String[] dfs1 = graph.DFS("A", "C", "alphabetical");
		assertArrayEquals(dfs1, new String[]{"C", "B", "A"});
		String[] dfs2 = graph.DFS("A", "C", "reverse");
		assertArrayEquals(dfs2, new String[]{"C", "A"});
		String[] dfsE = graph.DFS("A", "E", "alphabetical");
		assertArrayEquals(new String[0], dfsE);

	}

	@Test
	public void testBFS(){
		Graph graph = new Graph(1000);
		graph.addNode(new String[]{"A", "B", "C", "D"});
		graph.addEdge("A", new String[]{"B", "C", "D"});
		graph.addEdge("B", new String[]{"C"});
		String[] bfs1 = graph.BFS("A", "C", "alphabetical");
		assertArrayEquals(bfs1, new String[]{"C", "A"});
		String[] bfs2 = graph.BFS("A", "C", "reverse");
		assertArrayEquals(bfs2, new String[]{"C", "A"}); 
		String[] bfsE = graph.BFS("A", "E", "alphabetical");
		assertArrayEquals(new String[0], bfsE);
	}

	@Test
	public void testSecondShortestPath(){
		Graph graph = new Graph(1000);
		graph.addNode(new String[]{"A", "B", "C", "D"});
		graph.addEdge("A", new String[]{"B", "C", "D"});
		graph.addEdge("B", new String[]{"C"});
		String[] bfsS = graph.secondShortestPath("A", "C"); 
		assertArrayEquals(bfsS, new String[]{"C", "B", "A"});
	}
}
