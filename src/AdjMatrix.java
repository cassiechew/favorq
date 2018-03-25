import java.io.*;
import java.lang.reflect.Array;
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
	public int numberOfVertecies;
	public Map<T, Integer> verticies;
	public boolean[][] adjacencies;
	//public Map<Adjacency, Integer> adjacencies;


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
    }*/


	/**
	 * Contructs empty graph.
	 */
    public AdjMatrix() {
    	// Implement me!
        this.numberOfVertecies = 0;
        this.verticies = new HashMap<T, Integer>();
        this.adjacencies = new boolean[INTMAX][INTMAX];

        //this.adjacencies = new HashMap<Adjacency, Integer>();
    } // end of AdjMatrix()
    
    
    public void addVertex(T vertLabel) {
        // Implement me!
        if (!verticies.containsKey(vertLabel)) verticies.put(vertLabel, numberOfVertecies);
        numberOfVertecies++;
    } // end of addVertex()

    
    public void addEdge(T srcLabel, T tarLabel) {
        // Implement me!
        int srcValue;
        int tarValue;

        if (!verticies.containsKey(srcLabel) || !verticies.containsKey(tarLabel)) {
           System.err.print("One of these Edges doesnt Exist!");
           return;
        }
        srcValue = verticies.get(srcLabel);
        tarValue = verticies.get(tarLabel);
        if( srcValue != -1 && tarValue != -1 ) {
            adjacencies[srcValue][tarValue] = true;
            adjacencies[tarValue][srcValue] = true;
        }


    } // end of addEdge()

    private T getKeyByValue (int mapValue) {

        for ( T vertex : verticies.keySet() ) {
            if ( Objects.equals(mapValue, verticies.get(vertex))) return vertex;
        }
        return null;
    }

    public ArrayList<T> neighbours(T vertLabel) {
        ArrayList<T> neighbours = new ArrayList<T>();
        int vertValue;
        // Implement me
        if (!this.verticies.containsKey(vertLabel)) {
            System.err.println("One of these Edges doesnt Exist!");
            return null;
        }

        vertValue = verticies.get(vertLabel);
        for (int i = this.numberOfVertecies; i >= 0; i-- ) {
            if (this.adjacencies[vertValue][i] == true) neighbours.add(getKeyByValue(i));
        }
        return neighbours;
    } // end of neighbours()
    
    
    public void removeVertex(T vertLabel) {
        // Implement me!
        int vertexValue = verticies.get(vertLabel);

        if (!verticies.containsKey(vertLabel)) {
            System.err.println("That vertex doesn't exist!");
            return;
        }
        this.verticies.remove(vertLabel);


        for (int i = vertexValue; i <= numberOfVertecies; i++) {
            adjacencies[i] = Arrays.copyOf(adjacencies[i+1], adjacencies[i+1].length);
        }

        for (int i = vertexValue; i <= numberOfVertecies; i++) {
            //adjacencies[i] = Arrays.copyOf(adjacencies[i+1], adjacencies[i+1].length);
            for (int j = vertexValue; j <= numberOfVertecies; j++) {
                adjacencies[i][j] = adjacencies[i][j+1];
            }
        }
        numberOfVertecies--;
    } // end of removeVertex()
	
    
    public void removeEdge(T srcLabel, T tarLabel) {
        // Implement me!

        int srcValue = verticies.get(srcLabel);
        int tarValue = verticies.get(tarLabel);

        if (!adjacencies[srcValue][tarValue] || !adjacencies[tarValue][srcValue] ) {
            System.err.println("That edge doesn't exist!");
            return;
        }

        adjacencies[srcValue][tarValue] = false;
        adjacencies[tarValue][srcValue] = false;

    } // end of removeEdges()
	
    
    public void printVertices(PrintWriter os) {
        // Implement me!
        for ( T vertex : verticies.keySet() ) {
            os.println(vertex);
        }
    } // end of printVertices()
	
    
    public void printEdges(PrintWriter os) {
        // Implement me!
        os.println(adjacencies);

    } // end of printEdges()
    
    
    public int shortestPathDistance(T vertLabel1, T vertLabel2) {
    	// Implement me!
    	List<T> path;

        if (!verticies.containsKey(vertLabel1) || !verticies.containsKey(vertLabel2)) {
    	    System.err.println("One or more of these vertecies do not exist!");
        }

        path = findPath(vertLabel2, vertLabel1, new ArrayList<T>, new ArrayList<T>());

        // if we reach this point, source and target are disconnected
        return disconnectedDist;    	
    } // end of shortestPathDistance()

    private List<T> findPath (T target, T current, List<T> visited, List<T> currentPath) {

        List<T> possibleVertecies = neighbours(current);
        List<T> possiblePath = null;
        //List<T> currentPath;
        //int possiblePaths = possibleVertecies.size();

        for (T check : visited) {
            if (visited.contains(check)) {
                possibleVertecies.remove(check);
                //possiblePaths--;
            }
        }

       currentPath.add(current);

       if ( current == target ){

            return currentPath;
        }


        for ( T node : possibleVertecies) {
            possiblePath = findPath(target, node, visited, currentPath);
            if (possiblePath.contains(target)) {
                possiblePath.add(current);
                return possiblePath;
            }
            visited.add(node);
            if (possiblePath == null) {
                continue;
            }

        }

        //currentPath.addAll(possiblePath);

        return null;

    }
    
} // end of class AdjMatrix