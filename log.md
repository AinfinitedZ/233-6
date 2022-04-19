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

