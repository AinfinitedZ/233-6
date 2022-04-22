import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Graph {
    /**
     * Inside class that define a vertex. A vertex is element that being stored in set 'vertices.'
     */
    private class Vertex implements Comparable<Vertex>{
        private String id = "";
        private boolean encountered;
        private Vertex parent;
        private LinkedList<Edge> edges = new LinkedList<>();
        public Vertex(String id){
            this.id = id;
        }
        @Override
        public boolean equals(Object o){
            if(o == this) return true;
            if(!(o instanceof Vertex)) return false;

            Vertex vertex = (Vertex) o;
            return id.equals(vertex.id);
        }
        @Override
        public int compareTo(Graph.Vertex o) {
            return id.compareTo(o.id);
        }
    }
    /**
     * Inside class that define a edge. A edge is a ordered-pair element, where its order implies
     * the start point and end point of a path. 
     */
    private class Edge implements Comparable<Edge>{
        private Vertex startNode;
        private Vertex endNode;
        @Override
        public boolean equals(Object o){
            if(o == this) return true;
            if(!(o instanceof Edge)) return false;

            Edge edge = (Edge) o;
            return startNode.equals(edge.startNode) && endNode.equals(edge.endNode);
        }
        @Override
        public int compareTo(Edge o) {
            return endNode.id.compareTo(o.endNode.id);
        }
    }
    
    private ArrayList<Vertex> vertices = new ArrayList<>();     // A set that stores all vertexs.
    private int numVertices = 0;                                // current number of vertexs.
    private int maxNum = 0;                                     // Upper limit of the number of vertexs.

    public Graph(int maximum){                                  // Constructor with upper limit of vertices that could be stored in this graph.
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
        if(numVertices == maxNum || isExist(name)) return false;// if there is already a vertex. 
        addN(name);                                             // use a helper method which could be reused by another method. 
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
     * Insert a path between a ordered-pair vertex (a,b). a is declared as start point, and b is declared as end point.
     * Return true when this path is inserted successfully, and false when not, i.e, either a or b does not exist. 
     * @param from start point 
     * @param to end point
     * @return whether this edge is inserted successfully. 
     */
    public boolean addEdge(String from, String to){
        if(!(vertices.contains(new Vertex(from)) &&             // if the start point does not exist,
            vertices.contains(new Vertex(to)))) return false;   // or the end point does not exist, return false. 
        boolean succ = addE(from, to);                          // use a helper method which could be reused by another method. 
        return succ;

    }
    /**
     * Insert pathes between a set of ordered-pair vertexes. These ordered-pair share same start point a, and have different end point b, corresponding
     * to the order that one being input from 'tolist'. Return true when all pathes are inserted successfully, and false when not.
     * @param from start point
     * @param tolist set of end points
     * @return whether all edges are inserted successfully. 
     */
    public boolean addEdge(String from, String[] tolist){
        boolean indicator = true;
        for(int i = 0; i < tolist.length; i++){
            if(!(vertices.contains(new Vertex(from)) &&                         // if the start point does not exist, 
                    vertices.contains(new Vertex(tolist[i])))) return false;    // or the end point does not exist, return false. 
            boolean succ = addE(from, tolist[i]);                               // use a helper method which could be reused by another method.
            if(indicator == true) indicator = succ;
        }
        return indicator;
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
     * <nodename1> <neighbor1> <neighbor2> ...
     * <nodename2> <neighbor1> <neighbor2> ...
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
                System.out.print(edgeArray[j].endNode.id + " ");                                // and thus the neighbors
            }
            System.out.println(" ");
        }
    }
    /**
     * Construct a graph by given information from a file. The information should be represented by this way:
     * <nodename1> <neighbor1> <neighbor2> ...
     * <nodename2> <neighbor1> <neighbor2> ...
     * ...
     * @param fileName file path
     * @return a weight graph constructed. 
     * @throws FileNotFoundException
     */
    public static Graph read(String fileName) throws FileNotFoundException{
        Scanner sc = new Scanner(new File(fileName));
        Graph graph = new Graph(10000);
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
            for(int j = 1; j < nodeEdge.get(i).length; j++){
                graph.addEdge(nodeEdge.get(i)[0], nodeEdge.get(i)[j]); // and thus the edges. 
            }
        }
        sc.close();
        return graph;
    }
    /**
     * Depth-first search. Traversal recursively until all of one's neighbors is encountered.  
     * @param from start point
     * @param to end point
     * @param neighborOrder define the way neighbor is sorted, 'alphabetical' or 'reverse'
     * @return the path from start point to end point
     */
    public String[] DFS(String from, String to, String neighborOrder){
        if(!isExist(from) || !isExist(to)) return new String[0];                        // if start point or end point does not exist
        Vertex origin = findVertex(from);
        Vertex target = findVertex(to);
        Vertex ptr = target;
        sort(neighborOrder);                                                            // sort all vertex's neighbors
        dfs(vertices.indexOf(origin), -1, vertices.indexOf(target));                    // helper method
        String[] realPath = new String[0];
        ArrayList<String> path = new ArrayList<>();
        while(ptr != null){
            path.add(ptr.id);
            ptr = ptr.parent;
        }   
        if(!origin.equals(target) && path.size() == 1) return new String[0];
        realPath = path.toArray(realPath);
        for (Vertex vertex : vertices) {                                                // reset the status of each vertex.
            vertex.encountered = false;         
        }
        return realPath;
    }
    /**
     * Breadth-first search. Traversal all neighbors of start point, and all neighbors of neighbors, etc.
     * @param from start point
     * @param to end point
     * @param neighborOrder define the way neighbor is sorted, 'alphabetical' or 'reverse'
     * @return the path from start point to end point
     */
    public String[] BFS(String from, String to, String neighborOrder){
        if(!isExist(from) || !isExist(to)) return new String[0];            // if start point or end point does not exist
        sort(neighborOrder);                                                // sort all vertex's neighbors
        Vertex origin = findVertex(from);
        Vertex target = findVertex(to);
        ArrayList<String> path = bfs(origin, target);                       // helper method
        String[] realPath = new String[0];
        realPath = path.toArray(realPath);
        return realPath;
    }
    /**
     * Find second shortest path from start point to another. Find optimal path by using bfs, and thus call on
     * bfs from start point to all its neighbors. Return a path shorter than all other but longer than optimal path 
     * after comparing.
     * @param from start point
     * @param to end point
     * @return the second shortest path
     */
    public String[] secondShortestPath(String from, String to){
        if(!isExist(from) || !isExist(to)) return new String[0];                 // if start point or end point does not exist
        Vertex origin = findVertex(from);
        Vertex target = findVertex(to);
        Iterator<Edge> iterator = target.edges.iterator();
        String[] realPath = new String[0];
        ArrayList<String> path = bfs(origin, target);                            // to find optimal path
        int optimalLength = path.size();                                         // acquire optimal path length
        int secondLength = Integer.MAX_VALUE;
        while(iterator.hasNext()){                                               // iterates all neighbors of end point
            Vertex neighbor = iterator.next().endNode;
            ArrayList<String> temp = bfs(origin, neighbor);                      // call-on bfs on neighbor
            if(secondLength > temp.size() && temp.size() > optimalLength - 1){   // path length is least but not equal to optimal length       
                path = temp;                                                     // update path and length
                secondLength = temp.size();
            }
        }
        path.add(0, target.id);
        if(secondLength == Integer.MAX_VALUE) return new String[0];              // if another path is not found return an empty array.
        realPath = path.toArray(realPath);                                       
        for (Vertex vertex : vertices) {
            vertex.encountered = false;
        }
        return realPath;
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

    private boolean addE(String from, String to){
        Vertex startNode = findVertex(from);
        Vertex endNode = findVertex(to);

        Edge newEdge = new Edge();
        newEdge.startNode = startNode;
        newEdge.endNode = endNode;

        Edge newEndEdge = new Edge();
        newEndEdge.startNode = endNode;
        newEndEdge.endNode = startNode;

        boolean indicator = !startNode.edges.contains(newEdge);
        if(indicator){
            startNode.edges.add(newEdge);
            endNode.edges.add(newEndEdge);   
        }
        return indicator;
    }

    private void removeN(String name){
        int position = vertices.indexOf(new Vertex(name));
        Vertex toDelete = vertices.get(position);
        Iterator<Edge> itEdge = toDelete.edges.iterator();
        while(itEdge.hasNext()){                    // remove all edges that connected to endNode
            Edge relateEdge = itEdge.next();
            Edge temp = new Edge();
            temp.endNode = toDelete;
            temp.startNode = relateEdge.endNode;
            relateEdge.endNode.edges.remove(temp);
        }
        vertices.remove(position);
        numVertices--;
    }

    private void dfs(Integer i, Integer parent, Integer target){
        if(vertices.get(target).encountered == true) return;                                // base case
        Vertex vertex = vertices.get(i); 
        vertex.encountered = true;                                                          // initialization
        vertex.parent = (parent == -1) ? null : vertices.get(parent);                       // enable one to traversal back
        Iterator<Edge> iterator = vertex.edges.iterator();
        while(iterator.hasNext()){
            Edge curEdge = iterator.next();
            int j = vertices.indexOf(curEdge.endNode);
            if(vertices.get(j).encountered == false) dfs(j, i, target);                     // recursive case: if one's neighbor is not encountered
        }
    }
     
    private void sort(String neighborOrder){
        if(neighborOrder.equals("alphabetical")){
            for (Vertex vertex : vertices) {
                vertex.edges.sort(Comparator.naturalOrder());
            }
        } else if (neighborOrder.equals("reverse")){
            for (Vertex vertex : vertices) {
                vertex.edges.sort(Comparator.reverseOrder());
            }
        }
    }

    private ArrayList<String> bfs(Vertex origin, Vertex target){
        ArrayList<String> path = new ArrayList<>();
        origin.parent = null;
        origin.encountered = true;
        LinkedList<Vertex> queue = new LinkedList<>();          // use a FIFO queue to store neighbors.
        queue.addLast(origin);
        while(!queue.isEmpty()){                                // while there are non-meet nodes, 
            Vertex curr = queue.remove();                       // remove one of non-meet node
            if(curr.equals(target)){                            
                break;
            }
            Iterator<Edge> iterator = curr.edges.iterator();
            while(iterator.hasNext()){                          // for neighbors of a non-meet node,
                Vertex neighbor = iterator.next().endNode;
                if(neighbor.encountered == false){              // if it is not-meet
                    neighbor.encountered = true;                // update its status,
                    neighbor.parent = curr;
                    queue.addLast(neighbor);                    // and push it into queue.
                }
            }
        }
        for (Vertex vertex : vertices) {
            vertex.encountered = false;
        }
        while(target != null){
            path.add(target.id);
            target = target.parent;
        }
        if(!origin.equals(target) && path.size() == 1) return new ArrayList<>();
        return path;
    }

    private Vertex findVertex(String string){
        Vertex vertex = new Vertex(string);
        vertex = vertices.get(vertices.indexOf(vertex));
        return vertex;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Graph graph = Graph.read("/Users/daniel.l/Code/git/233-6/graph.txt");
        System.out.print("\nFirst graph: \n");
        graph.printGraph();
        String[] pathdfsAlpha = graph.DFS("A", "C", "alphabetical");
        String[] pathdfsRever = graph.DFS("A", "C", "reverse");
        String[] pathbfs = graph.BFS("A", "C", "alphabetical");
        String[] pathSec = graph.secondShortestPath("A","C");
        System.out.print("\nDFS with alphabetical: \n");
        for(int i = 0; i < pathdfsAlpha.length; i++){
            System.out.print(pathdfsAlpha[i] + " ");
        } 
        System.out.print("\nDFS with reverse: \n");
        for(int i = 0; i < pathdfsRever.length; i++){
            System.out.print(pathdfsRever[i] + " ");
        } 
        System.out.println("\nBFS: ");
        for(int i = 0; i < pathbfs.length; i++){
            System.out.print(pathbfs[i] + " ");
        }
        System.out.println("\nSecond: ");
        for(int i = 0; i < pathSec.length; i++){
            System.out.print(pathSec[i] + " ");
        }
        System.out.println("");
        System.out.print("\nAnother complex graph: \n");
        graph = Graph.read("/Users/daniel.l/Code/git/233-6/graphComplex.txt");
        graph.printGraph();
        pathdfsAlpha = graph.DFS("A", "G", "alphabetical");
        pathdfsRever = graph.DFS("A", "G", "reverse");
        pathbfs = graph.BFS("A", "G", "alphabetical");
        pathSec = graph.secondShortestPath("A", "G");
        System.out.print("\nDFS with alphabetical: \n");
        for(int i = 0; i < pathdfsAlpha.length; i++){
            System.out.print(pathdfsAlpha[i] + " ");
        }
        System.out.print("\nDFS with reverse: \n");
        for(int i = 0; i < pathdfsRever.length; i++){
            System.out.print(pathdfsRever[i] + " ");
        } 
        System.out.println("\nBFS: ");
        for(int i = 0; i < pathbfs.length; i++){
            System.out.print(pathbfs[i] + " ");
        }
        System.out.println("\nSecond: ");
        for(int i = 0; i < pathSec.length; i++){
            System.out.print(pathSec[i] + " ");
        }
        System.out.println("");
        System.out.print("\none way road graph: \n");
        graph = Graph.read("/Users/daniel.l/Code/git/233-6/graphoneway.txt");
        graph.printGraph();
        pathdfsAlpha = graph.DFS("A", "D", "alphabetical");
        pathdfsRever = graph.DFS("A", "D", "reverse");
        pathbfs = graph.BFS("A", "D", "alphabetical");
        pathSec = graph.secondShortestPath("A", "D");
        System.out.print("\nDFS with alphabetical: \n");
        for(int i = 0; i < pathdfsAlpha.length; i++){
            System.out.print(pathdfsAlpha[i] + " ");
        }
        System.out.print("\nDFS with reverse: \n");
        for(int i = 0; i < pathdfsRever.length; i++){
            System.out.print(pathdfsRever[i] + " ");
        } 
        System.out.println("\nBFS: ");
        for(int i = 0; i < pathbfs.length; i++){
            System.out.print(pathbfs[i] + " ");
        }
        System.out.println("\nSecond: ");
        for(int i = 0; i < pathSec.length; i++){
            System.out.print(pathSec[i] + " ");
        }
        System.out.println("");
    }
}


