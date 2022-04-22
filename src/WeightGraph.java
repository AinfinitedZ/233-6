import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class WeightGraph {
    /**
     * Inside class that define a vertex. A vertex is element that being stored in set 'vertices.'
     */
    private class Vertex implements Comparable<Vertex>{
        private String id = "";
        private Vertex parent;
        private int cost;
        private LinkedList<Edge> edges = new LinkedList<>();
        private ArrayList<Vertex> neightbors = new ArrayList<>();   // store all start nodes that pointed to this vertex.  
        public Vertex(String id){
            this.id = id;
        }
        @Override
        public boolean equals(Object o){                            // justify whether a vertex is equal to another. 
            if(o == this) return true;
            if(!(o instanceof Vertex)) return false;

            Vertex vertex = (Vertex) o;
            return id.equals(vertex.id);
        }
        @Override
        public int compareTo(Vertex o) {                            // return a integer represent order of two nodes by comparing their id in alphabetical order.  
            return id.compareTo(o.id);
        }
        public Edge findEdge(String to){                            
            if(!vertices.contains(new Vertex(to))) return new Edge();
            Edge edge = new Edge();
            edge.endNode = new Vertex(to);
            edge = edges.get(edges.indexOf(edge));
            return edge;
        }
    }
    /**
     * Inside class that define a edge. A edge is a ordered-pair element, where its order implies
     * the start point and end point of a path. 
     */
    private class Edge implements Comparable<Edge>{
        private Vertex startNode = new Vertex("");
        private Vertex endNode = new Vertex("");
        private int weight;
        @Override
        public boolean equals(Object o){
            if(o == this) return true;
            if(!(o instanceof Edge)) return false;

            Edge edge = (Edge) o;
            return endNode.equals(edge.endNode);
        }
        @Override
        public int compareTo(Edge o) {
            return endNode.id.compareTo(o.endNode.id);
        }
    } 
    /**
     * Rule that implies the order of vertexs by their current 'cost.' 'Cost' is defined in dijkstra algroithm
     * and is the least sum of weight from a given point to this. 
     */
    private class costComparator implements Comparator<Vertex>{
        @Override
        public int compare(WeightGraph.Vertex o1, WeightGraph.Vertex o2) { 
            return Integer.valueOf(o1.cost).compareTo(o2.cost);
        }
    }

    private ArrayList<Vertex> vertices = new ArrayList<>();         // A set that stores all vertexs. 
    private int numVertices = 0;                                    // current number of vertexs.
    private int maxNum = 0;                                         // Upper limit of the number of vertexs.

    public WeightGraph(int maximum){                                // Constructor with upper limit of vertices that could be stored in this graph.
        vertices = new ArrayList<>();
        numVertices = 0;
        maxNum = maximum;
    }
    /**
     * Insert a vertex into set 'graph.' This vertex is defined by their name. Return true when this node is inserted successfully, 
     * and false when not, i.e, there is already a vertex with same name.  
     * @param name ID that distinguish one vertex to another. 
     * @return whether this vertex is inserted successfully. 
     */
    public boolean addNode(String name){
        if(numVertices == maxNum || isExist(name)) return false;    // if there is already a vertex. 
        addN(name);                                                 // use a helper method which could be reused by another method. 
        return true;
    }

    /**
     * Insert a set of vertex into set 'graph,' consisting of several names that used to define each vertex. Return true when all nodes are inserted
     * successfully, and false when not. This method would keep inserting until the set is empty or there is one repeated element. 
     * @param names set of ID that distinguish these vertex to another. 
     * @return whether these vertexs are inserted successfully. 
     */
    public boolean addNode(String[] names){
        for(int i = 0; i < names.length; i++){
            if(numVertices == maxNum || isExist(names[i])) return false;    // if there is already a vertex
            addN(names[i]);                                                 // use a helper method which could be reused by another method. 
        }
        return true;
    }
    /**
     * Insert a path between a ordered-pair vertex (a,b) with weight w. a is declared as start point, and b is declared as end point. w
     * represent the 'cost' from a to b, i.e, the time spend from a to b. Return true when this path is inserted successfully, and false when
     * not, i.e, either a or b does not exist. 
     * @param from start point
     * @param to end point
     * @param weight cost from a to b
     * @return whether this edge is connected successfully. 
     */
    public boolean addWeightEdge(String from, String to, int weight){
        if(!(vertices.contains(new Vertex(from)) &&             // if the start point does not exist,
            vertices.contains(new Vertex(to)))) return false;   // or the end point does not exist, return false. 
        addE(from, to, weight);                                 // use a helper method which could be reused by another method. 
        return true;
    }
    /**
     * Insert pathes between a set of ordered-pair vertexes. These ordered-pair share same start point a, and have different end point b, corresponding
     * to the order that one being input from 'tolist'. weight w correspond to the order that being input from 'weightlist'. 
     * Return true when all pathes are inserted successfully, and false when not.
     * @param from start point
     * @param tolist set of end points
     * @param weightlist set of weight
     * @return whether these edges are connected successfully. 
     */
    public boolean addWeightEdge(String from, String[] tolist, int[] weightlist){
        for(int i = 0; i < tolist.length; i++){
            if(!(vertices.contains(new Vertex(from)) &&                         // if the start point does not exist, 
                    vertices.contains(new Vertex(tolist[i])))) return false;    // or the end point does not exist, return false. 
            addE(from, tolist[i], weightlist[i]);                               // use a helper method which could be reused by another method. 
        }
        return true;
    }
    /**
     * Remove a vertex from the graph. Return true when this vertex is removed successfully, and not when this vertex is not.  
     * @param name ID that used to find target vertex 
     * @return whether this vertex is removed successfully. 
     */
    public boolean removeNode(String name){
        if(numVertices == 0 || !isExist(name)) return false;    // if target vertex does not exist
        removeN(name);                                          // use a helper method which could be reused by another method. 
        return true;
    }
    /**
     * Remove several vertex from the graph. Return true when all targeted vertexes are removed successfully, and false when not. 
     * @param namelist set of ID that used to find target vertex. 
     * @return whether those vertexes are removed successfully. 
     */
    public boolean removeNode(String[] namelist){
        for(int i = 0; i < namelist.length; i++){
            if(numVertices == 0 || !isExist(namelist[i])) return false;     // if the target vertex does not exist
            removeN(namelist[i]);                                           // use a helper method which could be reused by another method. 
        }
        return true;
    }
    /**
     * Print all vertexes and their edges by alphabetical order. These information would be displayed by this way:
     * <nodename1> <weight> <neighbor1> <weight> <neighbor2> ...
     * <nodename2> <weight> <neighbor1> <weight> <neighbor2> ...
     * ...
     */
    public void printGraph(){
        Vertex[] vertex = new Vertex[numVertices];
        vertex = vertices.toArray(vertex);
        Arrays.sort(vertex);
        for(int i = 0; i < vertex.length; i++){                                                 // for each line,
            System.out.print(vertex[i].id + " ");                                               // print the node name first, 
            Edge[] edgeArray = new Edge[vertex[i].edges.size()]; 
            edgeArray = vertex[i].edges.toArray(edgeArray);
            Arrays.sort(edgeArray);
            for(int j = 0; j < edgeArray.length; j++){
                System.out.print(edgeArray[j].weight + " " + edgeArray[j].endNode.id + " ");    // and thus each weight and neighbor.
            }
            System.out.println(" ");
        }
    }

    /**
     * Construct a graph by given information from a file. The information should be represented by this way:
     * <nodename1> <weight> <neighbor1> <weight> <neighbor2> ...
     * <nodename2> <weight> <neighbor1> <weight> <neighbor2> ...
     * ...
     * @param fileName file path
     * @return a weight graph constructed. 
     * @throws FileNotFoundException
     */
    public static WeightGraph readWeight(String fileName) throws FileNotFoundException{
        Scanner sc = new Scanner(new File(fileName));
        WeightGraph graph = new WeightGraph(Integer.MAX_VALUE);
        ArrayList<String[]> nodeEdge = new ArrayList<>();       // two dimensional matrix that store the given information
        while(sc.hasNextLine()){
            String temp = sc.nextLine();
            String[] array = temp.split("\\s");           // deal with given information
            nodeEdge.add(array);        
        }

        for(int i = 0; i < nodeEdge.size(); i++){
            graph.addNode(nodeEdge.get(i) [0]);                 // all nodes would be inserted first
        }
        for(int i = 0; i < nodeEdge.size(); i++){
            for(int j = 1; j < nodeEdge.get(i).length; j = j + 2){
                graph.addWeightEdge(nodeEdge.get(i)[0], nodeEdge.get(i)[j + 1], Integer.parseInt(nodeEdge.get(i)[j])); // and thus the edges. 
            }
        }
        sc.close();
        return graph;
    }
    
    /**
     * By using dijkstra algorithm, this method find the shortest path from given start point to end point. Return a list of node name
     * from end point to start point. 
     * @param from start point
     * @param to end point
     * @return shortest path, represented by a list. 
     */
    public String[] shortestPath(String from, String to){
        if(!isExist(from) || !isExist(to)) return new String[0];        // return empty list when either start point or end point exists.
        ArrayList<String> result = new ArrayList<>();
        String[] type = new String[0];
        Vertex vFrom = findVertex(from);
        Vertex vTo = findVertex(to);
        dijShortestPath(vFrom);                                         // dijkstra algorithm.
        while(vTo != null){                                             // traversal to acquire the shortest path. 
            result.add(vTo.id);
            vTo = vTo.parent;
        }
        return result.toArray(type);
    }

    /**
     * By using once dijkstra algorithm, this method find the second shortest path from given start point to end point. Return a list of 
     * node name from end point to start point. Basically, we are finding a node to substitute one from shortest path by using adjcent lists
     * of nodes in shortest path. Since dijkstra find smallest path to each node, we could thus find a edge in adjcent lists that has least cost
     * except the one already in shortest path. For n nodes in the shortest path, there is n substitution of edges founded. We thus need to find
     * least cost edge among these substitution. Therefore we meet a second shortest path.  
     * @param from start point
     * @param to end point
     * @return shortest path, representd by a list. 
     */
    public String[] secondShortestPath(String from, String to){
        if(!isExist(from) || !isExist(to)) return new String[0];        // return false if either start point or end point does not exist. 
        ArrayList<String> result = new ArrayList<>();
        String[] type = new String[0];
        Vertex vFrom = findVertex(from);
        Vertex vTo = findVertex(to);
        Vertex ptr = vTo;
        int cost = Integer.MAX_VALUE;                                   // cost of smallest node in adjcent list. used to compare
        int difference = Integer.MAX_VALUE;                             // cost of smallest node in n substitution node. used to compare.
        Edge newEdge = new Edge();
        Edge secondEdge = new Edge();
        dijShortestPath(vFrom);                                         // calculate smallest paths to each node.
        while(ptr.parent != null){                                      // traversal adjcent lists from end point to start point,
            for (Vertex vertex : ptr.neightbors) {                      // for each neighbor
                Edge edge = vertex.findEdge(ptr.id);
                if(!vertex.equals(ptr.parent)                           // if one is not a node in shortest path
                    && cost > (vertex.cost + edge.weight)){             // and have least cost among adjcent list
                    cost = vertex.cost + edge.weight;                   // update one's cost,
                    newEdge = edge;                                     // and store this edge
                }
            }
            int currDifference =  newEdge.startNode.cost - newEdge.endNode.cost + newEdge.weight;   // after find one in adjcent list, one need to compare with other edge founded in other adjcent list
            if(!newEdge.equals(new Edge()) && currDifference < difference && currDifference != 0){  // if a edge is found and it has lower cost
                difference = newEdge.startNode.cost - newEdge.endNode.cost + newEdge.weight;        // update one's cost
                secondEdge = newEdge;                                                               // and store this edge
            }       
            ptr = ptr.parent;                                                                       // step forward
            cost = Integer.MAX_VALUE;                                                               // and reset the cost. 
        }
        if(secondEdge.equals(new Edge())) return new String[0];     // if second edge is not found. namely, there is only one path to destination. 
        secondEdge.endNode.parent = secondEdge.startNode;           // substitute this node with corresponing one in shortest path. 
        while(vTo != null){
            result.add(vTo.id);
            vTo = vTo.parent;
        }
        return result.toArray(type);
    }

    private boolean isExist(String name){
        if(vertices.contains(new Vertex(name))) return true;
        return false;
    }

    private void addN(String name){
        Vertex node = new Vertex(name);
        vertices.add(node);
        numVertices++;
    }

    private void removeN(String name){
        int position = vertices.indexOf(new Vertex(name));
        vertices.remove(position);
        numVertices--;
    }

    private void addE(String from, String to, Integer weight){
        int start = vertices.indexOf(new Vertex(from));
        int end = vertices.indexOf(new Vertex(to));
        Vertex startNode = vertices.get(start);
        Vertex endNode = vertices.get(end);

        Edge newEdge = new Edge();
        newEdge.startNode = startNode;
        newEdge.endNode = endNode;
        newEdge.weight = weight;

        endNode.neightbors.add(startNode);

        if(!startNode.edges.contains(newEdge)){     // if there is not repeated edge,
            startNode.edges.add(newEdge);
        }
    }

    /**
     * dijkstra algorithm. Greedy traverse each point to find least cost path from one to another.  
     * @param from start point
     */
    private void dijShortestPath(Vertex from){
        ArrayList<Vertex> set = new ArrayList<>();
        PriorityQueue<Vertex> heap = new PriorityQueue<>(new costComparator());     // custom comparator that order vertexs by their current cost.
        set.add(from);                                                              // Finalize start point
        for (Vertex vertex : vertices) {                                            // set initial cost for all point
            if(!vertex.equals(from) && vertex.neightbors.contains(from)){           // update cost and parent for neighbors of start point only
                vertex.parent = from;                                               
                vertex.cost = from.findEdge(vertex.id).weight; 
                heap.add(vertex);                                                   // push neighbors into min-heap
            } else {
                vertex.cost = Integer.MAX_VALUE;                                    // for non-neighbor vertex, their cost is infinity.
            }
        }
        from.cost = 0;                                                              // initialize the cost of start point
        while(set.size() < numVertices){                                            // while there is vertex not in finalized set, 
            Vertex curr = heap.poll();                                              // poll one have least cost to start point
            if(!set.contains(curr)) set.add(curr);                                  // and finalize it
            for (Edge edge : curr.edges) {                                          // for all its neighbors,
                Vertex neighbor = edge.endNode;
                if(!set.contains(neighbor)){                                        // if not finalized, 
                    int orig = neighbor.cost;                                       // find original cost
                    int temp = curr.cost + edge.weight;                             // and new cost from current vertex to it
                    neighbor.cost = Math.min(orig, temp);                           // compare two cost,
                    if(temp < orig) neighbor.parent = curr;                         // and update cost and parent
                    heap.add(neighbor);                                             // push neighbor in min-heap. Even though there would be repeated vertex,
                                                                                    // only smallest cost path would be considered because of min-heap.
                }
            }
        }
    }

    private Vertex findVertex(String string){
        Vertex vertex = new Vertex(string);
        vertex = vertices.get(vertices.indexOf(vertex));
        return vertex;
    }

    public static void main(String[] args) throws FileNotFoundException {
        WeightGraph graph = new WeightGraph(100);
        graph = WeightGraph.readWeight("/Users/daniel.l/Code/git/233-6/weightgraph.txt");
        System.out.print("\nFirst graph: \n");
        graph.printGraph();
        System.out.print("\nShortest Path: \n");
        String[] path = graph.shortestPath("A", "F");
        for(int i = path.length - 1; i >= 0; i--){
            System.out.print(path[i] + " -> ");
        } 
        System.out.print("\nSecond Shortest Path: \n");
        path = graph.secondShortestPath("A", "F");
        for(int i = path.length - 1; i >= 0; i--){
            System.out.print(path[i] + " -> ");
        }
        System.out.println("");
        graph = WeightGraph.readWeight("/Users/daniel.l/Code/git/233-6/weightgraphcomplex.txt");
        System.out.print("\nMore complex graph: \n");
        graph.printGraph();
        System.out.print("\nShortest Path: \n");
        path = graph.shortestPath("A", "J");
        for(int i = path.length - 1; i >= 0; i--){
            System.out.print(path[i] + " -> ");
        } 
        System.out.print("\nSecond Shortest Path: \n");
        path = graph.secondShortestPath("A", "J");
        for(int i = path.length - 1; i >= 0; i--){
            System.out.print(path[i] + " -> ");
        }
        System.out.println("");
        graph = WeightGraph.readWeight("/Users/daniel.l/Code/git/233-6/weightgraphoneway.txt");
        System.out.print("\nOnly one way: \n");
        graph.printGraph();
        System.out.print("\nShortest Path: \n");
        path = graph.shortestPath("A", "D");
        for(int i = path.length - 1; i >= 0; i--){
            System.out.print(path[i] + " -> ");
        } 
        System.out.print("\nSecond Shortest Path: \n");
        path = graph.secondShortestPath("A", "D");
        for(int i = path.length - 1; i >= 0; i--){
            System.out.print(path[i] + " -> ");
        }
        System.out.println("");
        graph = WeightGraph.readWeight("/Users/daniel.l/Code/git/233-6/map.txt");
        System.out.print("\nRealistic Map: \n");
        graph.printGraph();
        System.out.print("\nShortest Path from cleveland to cincinnati: \n");
        path = graph.shortestPath("Cleveland", "Cincinnati");
        for(int i = path.length - 1; i >= 0; i--){
            System.out.print(path[i] + " -> ");
        } 
        System.out.print("\nSecond Shortest Path from cleveland to cincinnati: \n");
        path = graph.secondShortestPath("Cleveland", "Cincinnati");
        for(int i = path.length - 1; i >= 0; i--){
            System.out.print(path[i] + " -> ");
        }
        System.out.print("\nShortest Path from cleveland to evansville: \n");
        path = graph.shortestPath("Cleveland", "Evansville");
        for(int i = path.length - 1; i >= 0; i--){
            System.out.print(path[i] + " -> ");
        } 
        System.out.print("\nSecond Shortest Path from cleveland to evansville: \n");
        path = graph.secondShortestPath("Cleveland", "Evansville");
        for(int i = path.length - 1; i >= 0; i--){
            System.out.print(path[i] + " -> ");
        }
    }
}