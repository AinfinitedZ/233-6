import static org.junit.Assert.*;

import org.junit.Test;

public class GraphTest {

	@Test
	public void testAddNode() throws Exception {
		Graph graph = new Graph(10);
		boolean isSuccA = graph.addNode("A");
		boolean isSuccB = graph.addNode("B");
		boolean isSuccR = graph.addNode("A");
		assertTrue(isSuccA);											// One should successfully insert a vertex into empty graph.
		assertTrue(isSuccB);											
		assertFalse(isSuccR);											// One should not successfully insert a existed vertex.
		boolean isSuccCDE = graph.addNode(new String[]{"C", "D", "E"});
		boolean isSuccACD = graph.addNode(new String[]{"A", "C", "D"});
		assertTrue(isSuccCDE);											// return true only if all vertex is successfully inserted
		assertFalse(isSuccACD);											// return false if any vertex is not successfully inserted.
	}


	@Test
	public void testAddEdge() throws Exception {
		Graph graph = new Graph(10);
		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("C");
		graph.addNode("D");
		boolean isSuccAB = graph.addEdge("A", "B");
		boolean isSuccAE = graph.addEdge("A", "E");
		boolean isSuccRe = graph.addEdge("A", "B");
		assertTrue(isSuccAB);													// One should successfully insert a edge with two existed vertex
		assertFalse(isSuccAE);													// One should not successfully insert a edge without two existed vertexs
		assertFalse(isSuccRe);													// One should not successfully insert a edge when it is repeated. 
		boolean isSuccAD = graph.addEdge("A", new String[]{"D"});			
		boolean isSuccABAC = graph.addEdge("A", new String[]{"B", "C"});
		boolean isSuccABAD = graph.addEdge("A", new String[]{"B", "D"});
		assertTrue(isSuccAD);													// return true only if all edges is inserted successful
		assertFalse(isSuccABAC);												// return false if any edges is not successfully inserted. 
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
		assertTrue(isSuccA);										// if vertex is existed, it would be removed
		assertFalse(isSuccC);										// vice versa.
		boolean isSuccD = graph.removeNode(new String[]{"D"});		
		boolean isSuccAE = graph.removeNode(new String[]{"A", "E"});
		assertTrue(isSuccD);										// if vertex is existed, it would be removed
		assertFalse(isSuccAE);										// vice versa.
	}

	@Test
	public void testDFS(){
		Graph graph = new Graph(1000);
		graph.addNode(new String[]{"A", "B", "C", "D"});							// Same undirected graph as on instruction
		graph.addEdge("A", new String[]{"B", "C", "D"});
		graph.addEdge("B", new String[]{"C"});
		String[] dfs1 = graph.DFS("A", "C", "alphabetical");
		assertArrayEquals(dfs1, new String[]{"C", "B", "A"});						// traversal one's neighbors with alphabetical order
		String[] dfs2 = graph.DFS("A", "C", "reverse");
		assertArrayEquals(dfs2, new String[]{"C", "A"});							// traversal one's neighbors with reverse order
		String[] dfsE = graph.DFS("A", "E", "alphabetical");
		assertArrayEquals(new String[0], dfsE);										// return empty array when not found

	}

	@Test
	public void testBFS(){
		Graph graph = new Graph(1000);
		graph.addNode(new String[]{"A", "B", "C", "D"});							// Same undirected graph as on instruction
		graph.addEdge("A", new String[]{"B", "C", "D"});
		graph.addEdge("B", new String[]{"C"});
		String[] bfs1 = graph.BFS("A", "C", "alphabetical");
		assertArrayEquals(bfs1, new String[]{"C", "A"});							// traversal one's neighbor with alphabetical order
		String[] bfs2 = graph.BFS("A", "C", "reverse");
		assertArrayEquals(bfs2, new String[]{"C", "A"}); 							// traversal one's neighbor with reverse order
		String[] bfsE = graph.BFS("A", "E", "alphabetical");
		assertArrayEquals(new String[0], bfsE);										// return empty array when not found
	}

	@Test
	public void testSecondShortestPath(){
		Graph graph = new Graph(1000);
		graph.addNode(new String[]{"A", "B", "C", "D"});					// same undirected graph as on instruction
		graph.addEdge("A", new String[]{"B", "C", "D"});
		graph.addEdge("B", new String[]{"C"});
		String[] bfsS = graph.secondShortestPath("A", "C"); 
		assertArrayEquals(bfsS, new String[]{"C", "B", "A"});				// if there exist a second shortest path
		String[] bfsE = graph.secondShortestPath("A", "E");
		assertArrayEquals(new String[0], bfsE);								// if not (from one existed vertex to unexisted vertex)
		bfsE = graph.secondShortestPath("A", "D");
		assertArrayEquals(new String[0], bfsE);								// if not (from two existed vertex, but only one path)
	}
}
