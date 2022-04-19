import org.junit.Test;

public class GraphTest {

	@Test
	public void testAddNode() throws Exception {
		Graph graph = new Graph(10);
		graph.addNode("A");
		graph.addNode("B");
	}


	@Test
	public void testAddEdge() throws Exception {
		Graph graph = new Graph(10);
		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("C");
		boolean ifSucc = graph.addEdge("A", "B");
		boolean ifSucc2 = graph.addEdge("A", "C");
		graph.removeNode("A");
		System.out.println(1);
	}


	@Test
	public void testRemoveNode() throws Exception {
		Graph graph = new Graph(10);
		graph.addNode(new String[] {"A", "B", "C"});
		graph.addEdge("A",new String[] {"B", "C"});
		graph.removeNode(new String[] {"A", "B"});
		System.out.println(1);
	}


	@Test
	public void testPrintGraph() throws Exception {

	}

	@Test
	public void testSetMaximum() throws Exception {

	}

	@Test
	public void testRead() throws Exception {

	}

}
