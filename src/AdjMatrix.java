import java.io.*;
import java.util.*;


/**
 * Adjacency matrix implementation for the FriendshipGraph interface.
 * 
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2016.
 */
public class AdjMatrix <T extends Object> implements FriendshipGraph<T>
{

    public static final int INTMAX = 50000;

	public int numberOfVertices;
	public Map<T, Integer> vertices;
	public boolean[][] adjacencies;
	//public List<Adjacency> adjacenciesList;


	/*protected class Adjacency <T extends Object> {
	    protected T first;
	    protected T second;

	    protected Adjacency (T first, T second) throws SameAdjacencyException{

	        if (first == second) {
                throw new SameAdjacencyException();
            }

	        this.first = first;
	        this.second = second;
        }



        public void print() {
	        System.out.println(first + " " + second);
        }

    }*/


	/**
	 * Contructs empty graph.
	 */
    public AdjMatrix() {
    	// Implement me!
        this.numberOfVertices = 0;
        this.vertices = new HashMap<T, Integer>();
        this.adjacencies = new boolean[INTMAX][INTMAX];
        //this.adjacenciesList = new ArrayList<Adjacency>();

    } // end of AdjMatrix()
    
    
    public void addVertex(T vertLabel) {
        // Implement me!
        if (!vertices.containsKey(vertLabel)) {
            vertices.put(vertLabel, numberOfVertices);
            numberOfVertices = numberOfVertices + 1;
        }
    } // end of addVertex()

    
    public void addEdge(T srcLabel, T tarLabel) {
        // Implement me!
        int srcValue;
        int tarValue;

        if (!vertices.containsKey(srcLabel) || !vertices.containsKey(tarLabel)) {
           System.err.print("One of these vertices doesn't Exist!");
           return;
        }
        srcValue = vertices.get(srcLabel);
        tarValue = vertices.get(tarLabel);
        if( srcValue != -1 && tarValue != -1 ) {
            adjacencies[srcValue][tarValue] = true;
            adjacencies[tarValue][srcValue] = true;

            /*try {
                Adjacency edge = new Adjacency(srcLabel, tarLabel);
                adjacenciesList.add(edge);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }


    } // end of addEdge()

    private T getKeyByValue (int mapValue) {

        for ( T vertex : vertices.keySet() ) {
            if ( Objects.equals(mapValue, vertices.get(vertex))) return vertex;
        }
        return null;
    }

    public ArrayList<T> neighbours(T vertLabel) {
        ArrayList<T> neighbours = new ArrayList<T>();
        int vertValue;
        // Implement me
        if (!this.vertices.containsKey(vertLabel)) {
            System.err.println("One of these Edges doesn't Exist!");
            return null;
        }

        vertValue = vertices.get(vertLabel);
	//System.out.print(vertLabel + " ");
        for (int i = 0; i < numberOfVertices - 1; i++ ) {
            if (this.adjacencies[vertValue][i] == true) {
	 	neighbours.add(getKeyByValue(i));
	        //System.out.print(getKeyByValue(i));
	    }
        }
	//System.out.println();
	
        return neighbours;
    } // end of neighbours()
    
    
    public void removeVertex(T vertLabel) {
        // Implement me!
        int vertexValue = vertices.get(vertLabel);

        if (!vertices.containsKey(vertLabel)) {
            System.err.println("That vertex doesn't exist!");
            return;
        }
	for (T vertex : vertices.keySet()) {
	    if ( vertices.get(vertex) > vertexValue) {
		vertices.put(vertex, vertices.get(vertex) - 1);
	    }
	}
        this.vertices.remove(vertLabel);


        for (int i = vertexValue; i < numberOfVertices; i++) {
            //adjacencies[i] = Arrays.copyOf(adjacencies[i+1], adjacencies[i+1].length);

	    for (int j = 0; j < numberOfVertices; j++) {
	        adjacencies[i][j] = adjacencies[i+1][j];	
	    }

        }

        for (int i = 0; i < numberOfVertices - 1; i++) {
            //adjacencies[i] = Arrays.copyOf(adjacencies[i+1], adjacencies[i+1].length);
            for (int j = vertexValue; j < numberOfVertices - 1; j++) {
                adjacencies[i][j] = adjacencies[i][j+1];
            }
        }
	//System.out.println("Before rem: " + numberOfVertices);
        numberOfVertices = numberOfVertices - 1;
	//System.out.println("After rem:  " + numberOfVertices);
    } // end of removeVertex()
	
    
    public void removeEdge(T srcLabel, T tarLabel) {
        // Implement me!

        int srcValue = vertices.get(srcLabel);
        int tarValue = vertices.get(tarLabel);

        if (!adjacencies[srcValue][tarValue] || !adjacencies[tarValue][srcValue] ) {
            System.err.println("That edge doesn't exist!");
            return;
        }

        adjacencies[srcValue][tarValue] = false;
        adjacencies[tarValue][srcValue] = false;

    } // end of removeEdges()
	
    
    public void printVertices(PrintWriter os) {
        // Implement me!
        for ( int i = 0; i < numberOfVertices ; i++ ) {
            os.print(getKeyByValue(i) + " ");
        }
	os.println();
    } // end of printVertices()
	
    
    public void printEdges(PrintWriter os) {
        // Implement me!
        //System.out.println(adjacencies[0]);
		
	//System.out.println("The number of vertices is: " + numberOfVertices);
	
        for (int i = 0; i <= numberOfVertices; i++) {
            for (int j = 0; j <= numberOfVertices; j++) {
                if (adjacencies[i][j]) os.println(getKeyByValue(i) + " " + getKeyByValue(j));
            }
        }
	
    } // end of printEdges()
    
    
    public int shortestPathDistance(T vertLabel1, T vertLabel2) {
    	// Implement me!

	Queue<T> path;
	boolean[] flags = new boolean[numberOfVertices];	
 	int[] pred = new int[numberOfVertices];
	T node;
	for (T vertex : vertices.keySet()) {
	    flags[vertices.get(vertex)] = false;
	    pred[vertices.get(vertex)] = -1;

	}

	path = new ArrayDeque();
	
	flags[vertices.get(vertLabel1)] = true;
	path.add(vertLabel1);
    //System.out.println("starting search");
	while(!path.isEmpty()) {
		
	    node = path.remove();
        //System.out.println(node);
	    for (T child : neighbours(node)) {
		    if (flags[vertices.get(child)] == false) {
		        flags[vertices.get(child)] = true;
		        pred[vertices.get(child)] = vertices.get(node);
		        path.add(child);
		    }
	    }
	}

	int pointer = pred[vertices.get(vertLabel2)];
	int pathLength = 1;
	//System.out.println("finished search");
	if (pointer == -1) return -1;
	
	while(pointer != vertices.get(vertLabel1)) {
	    pathLength += 1;
	    pointer = pred[pointer];
	}
	//System.out.println("Length: " + pathLength);
	return pathLength;

	/*
    	Queue<T> path;

    	// Dictionary to store path to T 
        Map<T, Queue<T>> savedPath;
	ArrayList<T> children;
    	Set<T> visited;

    	T node;

        if (!vertices.containsKey(vertLabel1) || !vertices.containsKey(vertLabel2)) {
    	    System.err.println("One or more of these vertecies do not exist!");
        }
	
	System.out.println("Path of : " + vertLabel1 + " " + vertLabel2);

        path = new ArrayDeque<T>();
        savedPath = new HashMap<T, Queue<T>>();
        //savedPath = new ArrayDeque<T>();
        visited = new HashSet<T>();

        //path = findPath(vertLabel2, vertLabel1, new ArrayList<T>(), new ArrayList<T>());
	int count = 0;
        path.add(vertLabel1);
	//visited.add(vertLabel1);
        while (!path.isEmpty()) {
		
            node = path.remove();
	    savedPath.put(node, new ArrayDeque<>());
	    savedPath.get(node).add(node);
	    children = neighbours(node);
	    if (children.size() == 0) {return disconnectedDist;};
	    System.out.print(node + " ");
            
	    System.out.print("CHECK: " + node + " " + vertLabel2);
	    
	    if (visited.contains(node)) continue; 

            for (T child : children) {
                
                //else {
                    //visited.add(node);
                    //savedPath.add(node);
                    savedPath.put(child, new ArrayDeque<>());
                if (savedPath.containsKey(node)) {
		    System.out.println("ADDING: " + savedPath.get(node));
                    savedPath.get(child).addAll(savedPath.get(node));
    		    savedPath.get(child).add(child);
		    System.out.println("[" + child+"]"+"Current: " + savedPath.get(child));
                }
                else {
                   
                }
            }
            path.addAll(neighbours(node));
                //}
            

	    if (node.equals(vertLabel2)) {
                //savedPath.add(node);
                System.out.println("SIZE: " + savedPath.get(vertLabel2));
                return savedPath.get(vertLabel2).size();
            }
	    
	    
    	    


            visited.add(node);


            //savedPath.remove(node);
        }

	*/
        // if we reach this point, source and target are disconnected
      //  return disconnectedDist;    	
    } // end of shortestPathDistance()

    /*private List<T> findPath (T target, T current, List<T> visited, List<T> currentPath, int distance) {

        List<T> possibleVertices = neighbours(current);
        List<T> possiblePath = null;
        //List<T> currentPath;
        //int possiblePaths = possibleVertices.size();

        for (T check : visited) {
            if (possibleVertices.contains(check)) {
                possibleVertices.remove(check);
                //possiblePaths--;
            }
        }

        currentPath.add(current);
        visited.add(current);

       if ( current == target ){

            return currentPath;
        }


        for ( T node : possibleVertices) {
            possiblePath = findPath(target, node, visited, currentPath);
            if (possiblePath.contains(target)) {
                possiblePath.add(current);
                return possiblePath;
            }
            visited.add(node);

        }

        //currentPath.addAll(possiblePath);

        return null;

    }*/
    
} // end of class AdjMatrix
