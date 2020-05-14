package classifier;

import java.util.*;

import scoring.alpha;

/**
 * The graph class has the purpose of creating a graph to
 * be used by the classifier. It contains all the methods needed to 
 * use Prim's algorithm, get values from the graph and print it.
 * A graph object encapsulates the information about the structure of the graph.
 * 
 * @author Ricardo Antão
 * @author Francisco Quelincho
 * @author Guilherme Mascarenhas
 */


public class graph {

    /**
     * List of connections that represents the graph used by the classifier 
     */
    private ArrayList<connection> DAG;

    /**
     * Class node (C). This node is different from the others in the graph which are 
     * related to the features (X1, ..., Xn). 
     */
    private NodeC _class_node;

    /**
     * Creates a graph using Prim's Algorithm
     *
     * @param matrix matrix containing all the alphas which represent 
     * the weight of each connection {@link scoring.Edges#matrix}
     */
    public void create(alpha[][] matrix) {
        int n_features = matrix.length;
        boolean[] visited = new boolean[n_features];
        connection aux;
        NodeC C = new NodeC();

        //Gets the firt 2 nodes of the graph
        this.DAG = getRootNode(matrix);
        visited = updateVisited(this.DAG.get(this.DAG.size() - 1).getParent(), visited);
        visited = updateVisited(this.DAG.get(this.DAG.size() - 1).getSon(), visited);

        //Keeps searching for new connections to the graph until all nodes are inserted
        while (this.checkVisited(visited) == 0) {
            this.DAG.add(this.getNextNode(visited, matrix));
            visited = updateVisited(this.DAG.get(this.DAG.size() - 1).getSon(), visited);
        }

        //Iterator for the graph
        Iterator<connection> pizzi = this.DAG.iterator();

        //Calculates teta values for the root node (1st node of the graph)
        aux = pizzi.next();
        aux.setTeta(aux.calcTetaRoot(matrix, this.DAG.get(1).getSon(), aux.getSon()));

        //Calculates teta values for the other nodes in the graph (excluding C)
        while (pizzi.hasNext()) {
            aux = pizzi.next();
            aux.setTeta(aux.calcTeta(matrix, aux.getParent(), aux.getSon()));
        }

        //Calculates tetaC values for node C
        C.setTetaC(C.calcTetaC(
                matrix[this.DAG.get(this.DAG.size() - 1).getParent()][this.DAG.get(this.DAG.size() - 1).getSon()]));
        this._class_node = C;
    }

    /**
     * The getNextNode method is the core of the implemented Prim's Algorithm
     * It calculates the next node to be included in the graph
     * @param visited List of all nodes already in the graph {@link classifier.graph#create(alpha[][])}
     * @param matrix matriz of alphas containing all the connections between nodes {@link scoring.Edges#matrix}
     * @return the next connection to be included in the graph {@link classifier.connection}
     */
    private connection getNextNode(boolean[] visited, alpha[][] matrix) {
        double max_value = 0;
        int flag_first = 1;
        int i_max = -1;
        int j_max = -1;

        
        for (int i = 0; i < visited.length; i++) {
            if (visited[i] == true) {
                for (int a = 0; a < matrix.length; a++) {
                    if (visited[a] == false && a != i) {
                        //initializes the maximum weighted connection with the first value found
                        if (flag_first == 1) {
                            i_max = i;
                            j_max = a;
                            max_value = matrix[i][a].getWeigth();
                            flag_first = 0;
                        }

                        //checks if the current connection is the maximum weighted
                        else if (matrix[i][a].getWeigth() > max_value) {
                            i_max = i;
                            j_max = a;
                            max_value = matrix[i][a].getWeigth();
                        }
                    }
                }
            }
        }
        return new connection(i_max, j_max);
    }

    /**
     * Method called after a new node is inserted in the graph to update the list of the visited nodes 
     * 
     * @param index index in the visited array to be updated 
     * @param visited List of all nodes already in the graph {@link classifier.graph#create(alpha[][])}
     * @return updated visited array
     */
    private boolean[] updateVisited(int index, boolean[] visited) {
        visited[index] = true;
        return visited;
    }

    /**
     * Checks if all nodes are in the graph
     *
     * @param visited List of all nodes already in the graph {@link classifier.graph#create(alpha[][])}
     * @return 1 if all nodes are inserted, 0 if not
     */
    private int checkVisited(boolean[] visited) {
        for (int i = 0; i < visited.length; i++) {
            if (visited[i] == false) {
                return 0;
            }
        }
        return 1;
    }

    /**
     * Gets the graph's root node and the first son node
     *
     * @param matrix matrix containing all the alphas {@link scoring.Edges#matrix}
     * @return array list containing the root node the first son node {@link classifier.connection}
     */
    private ArrayList<connection> getRootNode(alpha[][] matrix) {
        ArrayList<connection> init = new ArrayList<connection>();
        // Flag that indicates the first iteration of matrix values
        int flag_first = 1;

        // Variables used to find the maximum weighted connection
        double max_value = 0;
        int i_max = -1;
        int j_max = -1;

        for (int n = 0; n < matrix.length; n++) {
            for (int k = 0; k < n; k++) {
                // Initializes max_value, i_max and j_max with the values of the first iteration
                if (flag_first == 1) {
                    max_value = matrix[k][n].getWeigth();
                    i_max = k;
                    j_max = n;
                    flag_first = 0;
                }
                // Updates max_value, i_max and j_max
                else if (matrix[k][n].getWeigth() > max_value) {
                    i_max = k;
                    j_max = n;
                    max_value = matrix[k][n].getWeigth();
                }
            }
        }

        // Creates first 2 nodes of the graph
        connection root = new connection(-1, i_max);
        init.add(root);
        connection son = new connection(i_max, j_max);
        init.add(son);

        return init;
    }

    /**
     * Getter for DAG
     * @return this.DAG (see {@link classifier.connection})
     */
    public ArrayList<connection> getDAG() {
        return this.DAG;
    }

    /**
     * Getter for NodeC
     * @return this._class_node (see {@link classifier.NodeC})
     */
    public NodeC getNodeC() {
        return this._class_node;
    }

    /**
     * Prints the graph
     * @param labels ArrayList of the labels extracted from the first line of the CSV file
     * {@link data.InputHandler}
     */
    public void printGraph(ArrayList<String> labels) {

        connection temp;
        Iterator<connection> node = this.DAG.iterator();
        System.out.println("Classifier:");

        for (int i = 0; i < labels.size(); i++) {
            while (node.hasNext()) {

                temp = node.next();

                if (temp.getSon() == i) {

                    if (temp.getParent() == -1) {
                        System.out.println("                    " + labels.get(temp.getSon()) + ": class  ");
                    } else
                        System.out.println("                    " + labels.get(temp.getSon()) + ": class  "
                                + labels.get(temp.getParent()));

                    break;
                }
            }
            node = this.DAG.iterator();
        }
        System.out.println("                    class:");
        System.out.println();
    }
}