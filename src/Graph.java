import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Graph {
    private class Vertex implements Comparable<Vertex>{
        private String id = "";
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

    private ArrayList<Vertex> vertices = new ArrayList<>();
    private int numVertices = 0;
    private int maxNum = 0;

    public Graph(int maximum){
        vertices = new ArrayList<>();
        numVertices = 0;
        maxNum = maximum;
    }
    
    public boolean addNode(String name){
        if(numVertices == maxNum || isExist(name)) return false;
        addN(name);         // use a helper method which could be reused by another method. 
        return true;
    }

    public boolean addNode(String[] names){
        for(int i = 0; i < names.length; i++){
            if(numVertices == maxNum || isExist(names[i])) return false;
            addN(names[i]);
        }
        return true;
    }

    public boolean addEdge(String from, String to){
        if(!(vertices.contains(new Vertex(from)) && vertices.contains(new Vertex(to)))) return false;
        addE(from, to);     // use a helper method which could be reused by another method. 
        return true;

    }

    public boolean addEdge(String from, String[] tolist){
        for(int i = 0; i < tolist.length; i++){
            if(!(vertices.contains(new Vertex(from)) && vertices.contains(new Vertex(tolist[i])))) return false;
            addE(from, tolist[i]);
        }
        return false;
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
                System.out.print(edgeArray[j].endNode.id + " ");
            }
            System.out.println(" ");
        }
    }

    public void setMaximum(int maximum){
        this.maxNum = maximum;
    }

    public static Graph read(String fileName) throws FileNotFoundException{
        Scanner sc = new Scanner(new File(fileName));
        Graph graph = new Graph(10000);
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
            for(int j = 1; j < nodeEdge.get(i).length; j++){
                graph.addEdge(nodeEdge.get(i)[0], nodeEdge.get(i)[j]);
            }
        }
        sc.close();
        return graph;
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

    private void addE(String from, String to){
        int start = vertices.indexOf(new Vertex(from));
        int end = vertices.indexOf(new Vertex(to));
        Vertex startNode = vertices.get(start);
        Vertex endNode = vertices.get(end);

        Edge newEdge = new Edge();
        newEdge.startNode = startNode;
        newEdge.endNode = endNode;

        Edge newEndEdge = new Edge();
        newEndEdge.startNode = endNode;
        newEndEdge.endNode = startNode;

        if(!startNode.edges.contains(newEdge)){
            startNode.edges.add(newEdge);
            endNode.edges.add(newEndEdge);   
        }
    }

    private void removeN(String name){
        int position = vertices.indexOf(new Vertex(name));
        Vertex toDelete = vertices.get(position);
        Iterator<Edge> itEdge = toDelete.edges.iterator();
        while(itEdge.hasNext()){
            Edge relateEdge = itEdge.next();
            Edge temp = new Edge();
            temp.endNode = toDelete;
            temp.startNode = relateEdge.endNode;
            relateEdge.endNode.edges.remove(temp);
        }
        vertices.remove(position);
        numVertices--;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Graph graph = Graph.read("/Users/daniel.l/Code/git/233-6/graph.txt");
        graph.printGraph();
    }
}


