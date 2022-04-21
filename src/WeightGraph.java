import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class WeightGraph {
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

    private class costComparator implements Comparator<Vertex>{
        @Override
        public int compare(WeightGraph.Vertex o1, WeightGraph.Vertex o2) { 
            return Integer.valueOf(o1.cost).compareTo(o2.cost);
        }
    }

    private ArrayList<Vertex> vertices = new ArrayList<>();
    private int numVertices = 0;
    private int maxNum = 0; 

    public WeightGraph(int maximum){
        vertices = new ArrayList<>();
        numVertices = 0;
        maxNum = maximum;
    }

    public boolean addNode(String name){
        if(numVertices == maxNum || isExist(name)) return false;
        addN(name);                 // use a helper method which could be reused by another method. 
        return true;
    }

    public boolean addNode(String[] names){
        for(int i = 0; i < names.length; i++){
            if(numVertices == maxNum || isExist(names[i])) return false;
            addN(names[i]);
        }
        return true;
    }

    public boolean addWeightEdge(String from, String to, int weight){
        if(!(vertices.contains(new Vertex(from)) && vertices.contains(new Vertex(to)))) return false;
        addE(from, to, weight);     // use a helper method which could be reused by another method. 
        return true;
    }

    public boolean addWeightEdge(String from, String[] tolist, int[] weightlist){
        for(int i = 0; i < tolist.length; i++){
            if(!(vertices.contains(new Vertex(from)) && vertices.contains(new Vertex(tolist[i])))) return false;
            addE(from, tolist[i], weightlist[i]);
        }
        return true;
    }

    public boolean removeNode(String name){
        if(numVertices == 0 || !isExist(name)) return false;
        removeN(name);
        return true;
    }
 
    public boolean removeNode(String[] namelist){
        for(int i = 0; i < namelist.length; i++){
            if(numVertices == 0 || !isExist(namelist[i])) return false;
            removeN(namelist[i]);
        }
        return true;
    }

    public void printGraph(){
        Vertex[] vertex = new Vertex[numVertices];
        vertex = vertices.toArray(vertex);
        Arrays.sort(vertex);
        for(int i = 0; i < vertex.length; i++){
            System.out.print(vertex[i].id + " ");
            Edge[] edgeArray = new Edge[vertex[i].edges.size()];
            edgeArray = vertex[i].edges.toArray(edgeArray);
            Arrays.sort(edgeArray);
            for(int j = 0; j < edgeArray.length; j++){
                System.out.print(edgeArray[j].weight + " " + edgeArray[j].endNode.id + " ");
            }
            System.out.println(" ");
        }
    }

    public static WeightGraph readWeight(String fileName) throws FileNotFoundException{
        Scanner sc = new Scanner(new File(fileName));
        WeightGraph graph = new WeightGraph(10000);
        ArrayList<String[]> nodeEdge = new ArrayList<>();
        while(sc.hasNextLine()){
            String temp = sc.nextLine();
            String[] array = temp.split("\\s");
            nodeEdge.add(array);
        }

        for(int i = 0; i < nodeEdge.size(); i++){
            graph.addNode(nodeEdge.get(i) [0]);
        }
        for(int i = 0; i < nodeEdge.size(); i++){
            for(int j = 1; j < nodeEdge.get(i).length; j = j + 2){
                graph.addWeightEdge(nodeEdge.get(i)[0], nodeEdge.get(i)[j + 1], Integer.parseInt(nodeEdge.get(i)[j]));
            }
        }
        sc.close();
        return graph;
    }

    public String[] shortestPath(String from, String to){
        if(!isExist(from) || !isExist(to)) return new String[0];
        ArrayList<String> result = new ArrayList<>();
        String[] type = new String[0];
        Vertex vFrom = findVertex(from);
        Vertex vTo = findVertex(to);
        dijShortestPath(vFrom);
        while(vTo != null){
            result.add(vTo.id);
            vTo = vTo.parent;
        }
        return result.toArray(type);
    }

    public String[] secondShortestPath(String from, String to){
        if(!isExist(from) || !isExist(to)) return new String[0];
        ArrayList<String> result = new ArrayList<>();
        String[] type = new String[0];
        Vertex vFrom = findVertex(from);
        Vertex vTo = findVertex(to);
        Vertex ptr = vTo;
        int cost = Integer.MAX_VALUE;
        int difference = Integer.MAX_VALUE;
        Edge newEdge = new Edge();
        Edge secondEdge = new Edge();
        dijShortestPath(vFrom);
        while(ptr.parent != null){
            for (Vertex vertex : ptr.neightbors) {
                Edge edge = vertex.findEdge(ptr.id);
                if(!vertex.equals(ptr.parent) && cost > (vertex.cost + edge.weight)){
                    cost = vertex.cost + edge.weight;
                    newEdge = edge;
                }
            }
            if(!newEdge.equals(new Edge()) && newEdge.startNode.cost - newEdge.endNode.cost + newEdge.weight < difference){
                difference = newEdge.startNode.cost - newEdge.endNode.cost + newEdge.weight;
                secondEdge = newEdge;
            }
            ptr = ptr.parent;
            cost = Integer.MAX_VALUE;
        }
        if(secondEdge.equals(new Edge())) return new String[0];     // if second edge is not found, namely, there is only one path to target. 
        secondEdge.endNode.parent = secondEdge.startNode;           // one actually change the pointer of node. 
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

        if(!startNode.edges.contains(newEdge)){
            startNode.edges.add(newEdge);
        }
    }

    private void dijShortestPath(Vertex from){
        ArrayList<Vertex> set = new ArrayList<>();
        PriorityQueue<Vertex> heap = new PriorityQueue<>(new costComparator());
        set.add(from);
        for (Vertex vertex : vertices) {
            if(!vertex.equals(from) && vertex.neightbors.contains(from)){
                vertex.parent = from;
                vertex.cost = from.findEdge(vertex.id).weight; 
                heap.add(vertex);
            } else {
                vertex.cost = Integer.MAX_VALUE;
            }
        }
        from.cost = 0;
        while(set.size() < numVertices){
            Vertex curr = heap.poll();
            if(!set.contains(curr)) set.add(curr);
            for (Edge edge : curr.edges) {
                Vertex neighbor = edge.endNode;
                if(!set.contains(neighbor)){
                    int orig = neighbor.cost;
                    int temp = curr.cost + edge.weight;
                    neighbor.cost = Math.min(orig, temp);
                    if(temp < orig) neighbor.parent = curr;
                    heap.add(neighbor);
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
        System.out.print("Shortest Path: \n");
        String[] path = graph.shortestPath("A", "F");
        for(int i = 0; i < path.length; i++){
            System.out.print(path[i] + " ");
        } 
        System.out.print("\nSecond Shortest Path: \n");
        path = graph.secondShortestPath("A", "F");
        for(int i = 0; i < path.length; i++){
            System.out.print(path[i] + " ");
        }

        graph = WeightGraph.readWeight("/Users/daniel.l/Code/git/233-6/weightgraphcomplex.txt");
        System.out.print("\nMore complex graph: \n");
        graph.printGraph();
        System.out.print("Shortest Path: \n");
        path = graph.shortestPath("A", "J");
        for(int i = 0; i < path.length; i++){
            System.out.print(path[i] + " ");
        } 
        System.out.print("\nSecond Shortest Path: \n");
        path = graph.secondShortestPath("A", "J");
        for(int i = 0; i < path.length; i++){
            System.out.print(path[i] + " ");
        }

        graph = WeightGraph.readWeight("/Users/daniel.l/Code/git/233-6/weightgraphoneway.txt");
        System.out.print("\nOnly one way: \n");
        graph.printGraph();
        System.out.print("Shortest Path: \n");
        path = graph.shortestPath("A", "D");
        for(int i = 0; i < path.length; i++){
            System.out.print(path[i] + " ");
        } 
        System.out.print("\nSecond Shortest Path: \n");
        path = graph.secondShortestPath("A", "D");
        for(int i = 0; i < path.length; i++){
            System.out.print(path[i] + " ");
        }
        System.out.print("\n");
    }

}