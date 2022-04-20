import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Graph {
    private class Vertex implements Comparable<Vertex>{
        private String id = "";
        private boolean encountered;
        private boolean done;
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

    public String[] DFS(String from, String to, String neighborOrder){
        if(!isExist(from) || !isExist(to)) return new String[0];
        sort(neighborOrder);
        ArrayList<String> path = new ArrayList<>();
        path = dfs(vertices.indexOf(new Vertex(from)), -1, path, vertices.indexOf(new Vertex(to)));
        String[] realPath = new String[0];
        realPath = path.toArray(realPath);
        // reset the status of each vertex.
        for (Vertex vertex : vertices) {
            vertex.encountered = false;
        }
        return realPath;
    }

    public String[] BFS(String from, String to, String neighborOrder){
        if(!isExist(from) || !isExist(to)) return new String[0];
        sort(neighborOrder);
        Vertex origin = vertices.get(vertices.indexOf(new Vertex(from)));
        Vertex target = vertices.get(vertices.indexOf(new Vertex(to)));
        ArrayList<String> path = bfs(origin, target);
        String[] realPath = new String[0];
        realPath = path.toArray(realPath);
        for (Vertex vertex : vertices) {
            vertex.encountered = false;
        }
        return realPath;
    }

    public String[] secondShortestPath(String from, String to){
        if(!isExist(from) || !isExist(to)) return new String[0]; 
        Vertex origin = vertices.get(vertices.indexOf(new Vertex(from)));
        Vertex target = vertices.get(vertices.indexOf(new Vertex(to)));
        String[] realPath = new String[0];
        ArrayList<String> path = bfsS(origin, target);
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

    private ArrayList<String> dfs(Integer i, Integer parent, ArrayList<String> path, Integer target){
        Vertex vertex = vertices.get(i); 
        vertex.encountered = true;
        vertex.parent = (parent == -1) ? new Vertex("0") : vertices.get(parent);
        Iterator<Edge> iterator = vertex.edges.iterator();
        while(iterator.hasNext()){
            Edge curEdge = iterator.next();
            int j = vertices.indexOf(curEdge.endNode);
            if(vertices.get(j).encountered == false) dfs(j, i, path, target);
            if(vertices.get(target).encountered == true){
                path.add(vertex.id);
                return path;
            }
        }
        return new ArrayList<String>();
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
        LinkedList<Vertex> queue = new LinkedList<>();
        queue.addLast(origin);
        while(!queue.isEmpty()){    
            Vertex curr = queue.remove();
            if(curr.equals(target)){
                break;
            }
            Iterator<Edge> iterator = curr.edges.iterator();
            while(iterator.hasNext()){
                Vertex neighbor = iterator.next().endNode;
                if(neighbor.encountered == false){
                    neighbor.encountered = true;
                    neighbor.parent = curr;
                    queue.addLast(neighbor);
                }
            }
        }
        while(target != null){
            path.add(target.id);
            target = target.parent;
        }
        return path;
    }

    private ArrayList<String> bfsS(Vertex origin, Vertex target){
        ArrayList<String> path = new ArrayList<>();
        origin.parent = null;
        origin.encountered = true;
        LinkedList<Vertex> queue = new LinkedList<>();
        queue.addLast(origin);
        while(!queue.isEmpty()){    
            Vertex curr = queue.remove();
            if(curr.equals(target) && target.done == false){
                target.done = true;
            } else if (curr.equals(target) && target.done == true){
                break;
            }
            Iterator<Edge> iterator = curr.edges.iterator();
            while(iterator.hasNext()){
                Vertex neighbor = iterator.next().endNode;
                if(neighbor.encountered == false){
                    if(!neighbor.equals(target)) neighbor.encountered = true;
                    neighbor.parent = curr;
                    queue.addLast(neighbor);
                }
            }
        }
        while(target != null){
            path.add(target.id);
            target = target.parent;
        }
        return path;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Graph graph = Graph.read("/Users/daniel.l/Code/git/233-6/graph.txt");
        System.out.print("\nFirst graph: \n");
        graph.printGraph();
        String[] pathdfsAlpha = graph.DFS("A", "E", "alphabetical");
        String[] pathdfsRever = graph.DFS("A", "E", "reverse");
        String[] pathbfs = graph.BFS("A", "E", "alphabetical");
        String[] pathSec = graph.secondShortestPath("A","E");
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
    }
}


