package scoring;

import java.util.*;
import data.*;

/**
 * Edges is an abstract class which has the purpose of serving as blueprint for other classes.
 * These have the purpose of converting the train data into an
 * adjacency matrix. An Edges object encapsulates the information needed to
 * build the MST. This state information includes:
 * <ul>
 * <li>The adjacency matrix {@link scoring.Edges#matrix}
 * </ul>
 * <p>
 *
 * @author Ricardo Antão
 * @author Francisco Quelincho
 * @author Guilherme Mascarenhas
 */

public abstract class Edges implements WeigthCalculator{

    /**
     * The adjacency matrix which stores every edge
     * */
    protected alpha[][] matrix;

    /**
     * Method which will be overwritten by subclasses.
     * It calculates the weigth of the connection described by the input alpha.
     * @param a Connection to be weighed {@link scoring.alpha}
     */
    public double calcScore(alpha a) {
        return 0;
    }

    /**
     * Returns the adjacency matrix
     * @return  Adjacency matrix {@link scoring.Edges#matrix}
     */
    public alpha[][] getMatrix() {
        return this.matrix;
    }

    /**
     * Creates an empty adjacency matrix
     * @param n_features number of features of an instance
     */
    public void setMatrix(int n_features) {
        this.matrix = new alpha[n_features][n_features];
    }

    /**
     * Defines a specific value of the adjacency matrix.
     * @param parent line of the matrix (parent node)
     * @param son column of the matrix (son node)
     * @param a Edge to be stored
     */
    public void setMatrixElement(alpha a, int parent, int son) {
        this.matrix[parent][son] = a;
    }

    /**
     * Print adjacency matrix
     */
    public void printScores() {
        int s = this.matrix.length;
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < s; j++) {
                System.out.print(" " + this.matrix[i][j].getWeigth());
            }
            System.out.println();
        }
    }

    /**
     * Creates an adjacency matrix from the data received.
     * <p>
     *
     * @param Data Instances and their classifications (training data)
     */
    public void generateScores(InputHandler Data) {
        // Initializes values
        int n_features = Data.getLabels().size() - 1;

        int i = 0, j = 0;

        this.setMatrix(n_features);

        Map<String, ArrayList<Integer>> train = Data.getValues();
        Map<String, ArrayList<Integer>> unique = Data.getValuesUnique();
        ArrayList<String> labels = Data.getLabels();

        // Creates a list with only the feature keys
        ArrayList<String> features = new ArrayList<String>();
        features.addAll(labels);
        features.remove(features.size() - 1);

        // Saves class key
        String class_key = labels.get(labels.size() - 1);

        // Iterates over the data
        for (String parent : features) {

            for (String son : features) {
                alpha a = new alpha();
                // Since only upper triangular half is filled since the matrix is symmetric
                if (j != i) {
                    // Calculate N_ijkc values for the current edge
                    a.calcN(train.get(parent), unique.get(parent), train.get(son), unique.get(son),
                            train.get(class_key), unique.get(class_key));
                    // Calculates weight of connection and stores it in alpha
                    a.setWeigth(this.calcScore(a));
                    // Saves alpha into adjacency matrix
                    this.setMatrixElement(a, i, j);
                } else {
                    // saves empty alpha into the lower half of the matrix
                    this.setMatrixElement(a, i, j);
                }
                j++;
            }
            j = 0;
            i++;
        }

    }

}