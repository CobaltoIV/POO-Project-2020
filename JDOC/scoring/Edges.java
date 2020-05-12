package scoring;

import java.util.*;
import data.*;

/**
 * Edges is an abstract class which has the purpose of serving as blueprint for any classes
 * that wish to generate a fully connected graph from the training data.
 * <p>
 * An Edges object consists of an adjacency matrix and the methods
 * to generate it (implemented through the {@link scoring.WeigthCalculator} Interface).
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
     * Returns the weigth of the connection described by the input alpha.
     * @param a Connection to be weighed (see {@link scoring.alpha})
     * @return {@link scoring.alpha#_weigth}
     */
    public double calcScore(alpha a) {
        return 0;
    }

    /**
     * Getter for the adjacency matrix
     * @return {@link scoring.Edges#matrix}
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