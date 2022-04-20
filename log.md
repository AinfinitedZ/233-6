4.17 20:00

Begin working on the class Graph. 

4.17 20:04

Stub on reSize() method. Should enlarge the graph in the factor of two. 

4.18 14:28

Finish two addNode methods. Call on addNode(String name) when invoking addNode(String[] names). 

4.18 14:49

Use a hash table to implement finding duplicates. That ensure there is only one individual elememt in the Graph. 

4.18 14:59

Figure out that hashtable could both store the String, which is the key of node, and Integer, which is the position of node in ArrayList. That exchange space with time, make constructing behaviors cost $O(1)$. 

4.18 15:40

Use helper method for addNode and addEdge so that these helper method could be reused simply. 

4.18 16:08

Use helper method for removeNode so that these helper method could be reused simply. 

4.18 16:24

Finish removeNode methods. All these method that return a boolean would be exectued repeatly until they could not be executed.(i.e., not found ‘name’ in String array or as a input, )

4.18 17:34

Replace hashtable by call-on of arraylist. Override equals method on Vertex class and Edge class. 

4.19 14:55

Implement both Comparable interface and equals() method to Vertex class and Edge class.

4.19 15:08

Implement read method by using a Scanner. 

4.19 15:24

Forget an ^ sign in addEdge(). 

4.19 15:46

Replace toArray with toArray(T[]). That method makes life easier when export Lists that should be sorted. 

4.19 16:19

Use a two dimension array in read() method. One would read all nodes first and then add edges. 

10% complete

<hr>

4.19 16:45

Use -1 as parent of first node to avoid nullPointerException. Pass a ArrayList<String> that store the path in argument of DFS which executed recursively. 

4.19 17:35

Finish DFS method. 

4.20 11:10

Fix the bug of DFS that some path could be displayed. Implement a sort method that could sort the neighbor orders. 

4.20 11:58

Finish BFS method. 

4.20 12:28

Finish secondShortestPath method. Use BFS method and another field named ‘done’ which enabled the algorithm traverse twice to find the path. 

4.20 12:56

Fix the bug that secondShortestPath and BFS could not work well. The reason is the push() method of LinkedList actually work for a stack but not a queue. 

50% complete

<hr>

