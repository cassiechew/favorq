import java.io.*;
import java.util.*;


/**
 * Incidence matrix implementation for the FriendshipGraph interface.
 * 
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2016.
 */
public class IndMatrix <T extends Object> implements FriendshipGraph<T>
{

    public List<T> vertices;
    public List<List<T>> edges;


	/**
	 * Contructs empty graph.
	 */
    public IndMatrix() {
    	// Implement me!
        vertices = new ArrayList<T>();
        edges = new ArrayList<List<T>>();
    } // end of IndMatrix()
    
    
    public void addVertex(T vertLabel) {
        // Implement me!
        if (vertices.contains(vertLabel)) {
            System.err.println("Vertex already exists!");
            return;
        }
        vertices.add(vertLabel);
        edges.add(new ArrayList<T>());
    } // end of addVertex()
	
    
    public void addEdge(T srcLabel, T tarLabel) {
        // Implement me!

        if (!vertices.contains(srcLabel) || !vertices.contains(tarLabel)) {
            System.err.println("One of these vertices does not exist!");
            return;
        }

        List<T> srcList = edges.get(vertices.indexOf(srcLabel));
        List<T> tarList = edges.get(vertices.indexOf(tarLabel));

        if (srcList.contains(tarLabel) || tarList.contains(srcLabel)) {
            //System.err.println("An edge already exists!");
            return;
        }

        srcList.add(tarLabel);
        tarList.add(srcLabel);
    } // end of addEdge()
	

    public ArrayList<T> neighbours(T vertLabel) {
        ArrayList<T> neighbours;// = new ArrayList<T>();
        
        // Implement me!

        if (!vertices.contains(vertLabel)) {
            System.err.println ("This vertex does not exist!");
            return null;
        }

        return (ArrayList<T>) edges.get(vertices.indexOf(vertLabel));

        //return neighbours;
    } // end of neighbours()
    
    
    public void removeVertex(T vertLabel) {
        // Implement me!
        if (!vertices.contains(vertLabel)) {
            System.err.println("This vertex does not exist!");
            return;
        }

        for (List<T> edge : edges) {
            if (edge.contains(vertLabel)) edge.remove(vertLabel);
        }
        edges.remove(vertices.indexOf(vertLabel));
        vertices.remove(vertLabel);
    } // end of removeVertex()
	
    
    public void removeEdge(T srcLabel, T tarLabel) {
        // Implement me!
        List<T> srcList = edges.get(vertices.indexOf(srcLabel));
        List<T> tarList = edges.get(vertices.indexOf(tarLabel));

        if (!srcList.contains(tarLabel) || !tarList.contains(srcLabel)) {
            System.err.println("Edge does not exist!");
            return;
        }

        srcList.remove(tarLabel);
        tarList.remove(srcLabel);



    } // end of removeEdges()
	
    
    public void printVertices(PrintWriter os) {
        // Implement me!
        for (T vertex : vertices) {
            os.print(vertex + " ");
        }
	os.println();
    } // end of printVertices()
	
    
    public void printEdges(PrintWriter os) {
        // Implement me!
        //System.err.println("PRINTING EDGES");
        for (int i = 0; i < edges.size(); i++) {
	    if (edges.get(i).size() == 0) continue;
	    for (int j = 0; j < edges.get(i).size(); j++) {
                os.print(vertices.get(i) + " ");
                os.println(edges.get(i).get(j));
	    }
        }
    } // end of printEdges()
    
    
    public int shortestPathDistance(T vertLabel1, T vertLabel2) {
    	// Implement me!
    	//
    	Queue<T> path;
        boolean[] flags = new boolean[vertices.size()];
        int[] pred = new int[vertices.size()];
        T node;
        for (T vertex : vertices) {
            flags[vertices.indexOf(vertex)] = false;
            pred[vertices.indexOf(vertex)] = -1;

        }

        path = new ArrayDeque();

        flags[vertices.indexOf(vertLabel1)] = true;
        path.add(vertLabel1);
        //System.out.println("starting search");
        while(!path.isEmpty()) {

            node = path.remove();
            //System.out.println(node);
            for (T child : neighbours(node)) {
                if (flags[vertices.indexOf(child)] == false)
                {
                    flags[vertices.indexOf(child)] = true;
                    pred[vertices.indexOf(child)] = vertices.indexOf(node);
                    path.add(child);
                }
            }
        }

        int pointer = pred[vertices.indexOf(vertLabel2)];
        int pathLength = 1;
        //System.out.println("finished search");
        if (pointer == -1) return -1;

        while(pointer != vertices.indexOf(vertLabel1)) {
            pathLength += 1;
            pointer = pred[pointer];
        }
        //System.out.println("Length: " + pathLength);
        return pathLength;

        /*Queue<T> path;

    	/ Dictionary to store path to T 
        Map<T, Queue<T>> savedPath;

    	Set<T> visited;

    	T node;

        if (!vertices.contains(vertLabel1) || !vertices.contains(vertLabel2)) {
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
        return disconnectedDist;    	*/
    } // end of shortestPathDistance()
    
} // end of class IndMatrix
