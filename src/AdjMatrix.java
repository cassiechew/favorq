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
            numberOfVertices++;
        }
    } // end of addVertex()

    
    public void addEdge(T srcLabel, T tarLabel) {
        // Implement me!
        int srcValue;
        int tarValue;

        if (!vertices.containsKey(srcLabel) || !vertices.containsKey(tarLabel)) {
           System.err.print("One of these Edges doesn't Exist!");
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
        for (int i = this.numberOfVertices; i >= 0; i-- ) {
            if (this.adjacencies[vertValue][i] == true) neighbours.add(getKeyByValue(i));
        }
        return neighbours;
    } // end of neighbours()
    
    
    public void removeVertex(T vertLabel) {
        // Implement me!
        int vertexValue = vertices.get(vertLabel);

        if (!vertices.containsKey(vertLabel)) {
            System.err.println("That vertex doesn't exist!");
            return;
        }
        this.vertices.remove(vertLabel);


        for (int i = vertexValue; i <= numberOfVertices; i++) {
            adjacencies[i] = Arrays.copyOf(adjacencies[i+1], adjacencies[i+1].length);
        }

        for (int i = vertexValue; i <= numberOfVertices; i++) {
            //adjacencies[i] = Arrays.copyOf(adjacencies[i+1], adjacencies[i+1].length);
            for (int j = vertexValue; j <= numberOfVertices; j++) {
                adjacencies[i][j] = adjacencies[i][j+1];
            }
        }
        numberOfVertices--;
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
        for ( T vertex : vertices.keySet() ) {
            os.println(vertex);
        }
    } // end of printVertices()
	
    
    public void printEdges(PrintWriter os) {
        // Implement me!
        //os.println(adjacencies);

        for (int i = numberOfVertices; i >= 0; i--) {
            for (int j = numberOfVertices; j >=0; j--) {
                if (adjacencies[i][j]) System.out.println(getKeyByValue(i) + " " + getKeyByValue(j));
            }
        }

    } // end of printEdges()
    
    
    public int shortestPathDistance(T vertLabel1, T vertLabel2) {
    	// Implement me!
    	Queue<T> path;

    	/* Dictionary to store path to T */
        Map<T, Queue<T>> savedPath;

    	Set<T> visited;

    	T node;

        if (!vertices.containsKey(vertLabel1) || !vertices.containsKey(vertLabel2)) {
    	    System.err.println("One or more of these vertecies do not exist!");
        }

        path = new ArrayDeque<T>();
        savedPath = new HashMap<T, Queue<T>>();
        //savedPath = new ArrayDeque<T>();
        visited = new HashSet<T>();

        //path = findPath(vertLabel2, vertLabel1, new ArrayList<T>(), new ArrayList<T>());

        path.add(vertLabel1);

        while (!path.isEmpty()) {

            node = path.remove();

            if (node == vertLabel2) {
                //savedPath.add(node);
                return savedPath.get(vertLabel2).size();
            }

            for (T child : neighbours(node)) {
                if (visited.contains(node)) {
                    continue;
                }
                else {
                    //visited.add(node);
                    //savedPath.add(node);
                    savedPath.put(child, new ArrayDeque<>());
                    if (savedPath.containsKey(node)) {
                        savedPath.get(child).addAll(savedPath.get(node));
                    }
                    else {
                        savedPath.get(child).add(node);
                    }
                    path.addAll(neighbours(node));
                }
            }
            visited.add(node);


            //savedPath.remove(node);
        }


        // if we reach this point, source and target are disconnected
        return disconnectedDist;    	
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