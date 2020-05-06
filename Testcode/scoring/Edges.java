package scoring;

import java.util.*;
import data.*;

/**
 * Edges is an abstract class which has the purpose of serving as blueprint for other classes.
 * These have the purpose of converting the train data into an
 * adjacency matrix. An Edges object encapsulates the information needed to
 * build the MST. This state information includes:
 * <ul>
 * <li>The adjacency matrix
 * </ul>
 * <p>
 *
 * @author Ricardo Antão
 * @author Francisco Quelincho
 * @author Guilherme Mascarenhas
 */

public abstract class Edges {

    /**
     * The adjacency matrix which stores every edge
     * */
    protected alpha[][] matrix;

    /**
     * Method which will be overwritten by subclasses.
     * It calculates the weigth of the connection described by the input alpha.
     * @param a
     * @return score
     */
    public double calcScore(alpha a) {

        double score = 33.9;

        return score;
    }

    /**
     * Returns the adjacency matrix
     * @return @link matrix
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
     * @param i line of the matrix
     * @param j column of the matrix
     * @param a Edge to be stored
     */
    public void setMatrixElement(alpha a, int parent, int son) {
        this.matrix[parent][son] = a;
    }

    /**
     * Print adjacency matrix
     */
    public void printMatrix() {
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
                if (j > i) {
                    // Calculate N_ijkc values for the current edge
                    a.setSource(a.calcN(train.get(parent), unique.get(parent), train.get(son), unique.get(son),
                            train.get(class_key), unique.get(class_key)));
                    System.out.println("i= " + i + "j= " + j + " ");
                    System.out.println(parent + " " + " " + son + " " + class_key);
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

        this.printMatrix();

    }

}
/**
 * Extends class Edges. It uses the LL score to calculate the weigths
 * <p>
 * @author Ricardo Antão
 * @author Francisco Quelincho
 * @author Guilherme Mascarenhas
 */

class LL_edges extends Edges {

    public double calcScore(alpha a) {
        double score = 0;
        int[][][] N_jkc = a.getSource();

        // Store number of unique values for each feature and class
        int q = N_jkc.length;
        int r = N_jkc[0].length;
        int s = N_jkc[0][0].length;
        int N = 0;
        // Matrixs where values of N^K_ijc and N^J_ikc and N_C are gonna be stored
        double[][] N_K = new double[q][s];
        double[][] N_J = new double[r][s];
        double[] N_C = new double[s];

        double p;
        double n;
        double temp;

        // Obtain values from N_ijkc
        for (int k = 0; k < r; k++) {

            for (int j = 0; j < q; j++) {

                for (int c = 0; c < s; c++) {

                    N += N_jkc[j][k][c];
                    N_K[j][c] += N_jkc[j][k][c];
                    N_J[k][c] += N_jkc[j][k][c];
                    N_C[c] += N_jkc[j][k][c];

                }
            }
        }

        // Calculate the LL score
        for (int k = 0; k < r; k++) {

            for (int j = 0; j < q; j++) {

                for (int c = 0; c < s; c++) {

                    // Conditions to prevent mathematical impossibilities
                    if ((N_K[j][c] * N_J[k][c]) != 0) {

                        temp = ((double) N_C[c] * (double) N_jkc[j][k][c]) / ((double) N_K[j][c] * (double) N_J[k][c]);
                        p = (double) N_jkc[j][k][c] / (double) N;

                        if (temp != 0) {

                            n = Math.log10(temp) / Math.log10(2);

                            score += p * n;

                        } else {
                            score += 0;
                        }

                    } else {
                        score += 0;
                    }
                }
            }

        }

        return score;
    }

}
/**
 * Extends class Edges. It uses the MDL score to calculate the weigths
 * <p>
 * @author Ricardo Antão
 * @author Francisco Quelincho
 * @author Guilherme Mascarenhas
 */
class MDL_edges extends Edges {

    public double calcScore(alpha a) {
        double score = 0;
        int[][][] N_jkc = a.getSource();

        int q = N_jkc.length;
        int r = N_jkc[0].length;
        int s = N_jkc[0][0].length;
        int N = 0;
        int[][] N_K = new int[q][s];
        int[][] N_J = new int[r][s];
        int[] N_C = new int[s];

        double p;
        double n;
        double temp;

        for (int k = 0; k < r; k++) {

            for (int j = 0; j < q; j++) {

                for (int c = 0; c < s; c++) {

                    N += N_jkc[j][k][c];
                    N_K[j][c] += N_jkc[j][k][c];
                    N_J[k][c] += N_jkc[j][k][c];
                    N_C[c] += N_jkc[j][k][c];

                }
            }
        }


        for (int k = 0; k < r; k++) {

            for (int j = 0; j < q; j++) {

                for (int c = 0; c < s; c++) {

                    if (N_K[j][c] * N_J[k][c] != 0) {

                        temp = ((double) N_C[c] * (double) N_jkc[j][k][c]) / ((double) N_K[j][c] * (double) N_J[k][c]);
                        p = (double) N_jkc[j][k][c] / (double) N;

                        if (temp != 0) {

                            n = Math.log10(temp) / Math.log10(2);

                            score += p * n;

                        } else {
                            score += 0;
                        }

                    } else {
                        score += 0;
                    }

                }
            }

        }

        // Only difference between LL and MDL scores
        double g = (((double) s * ((double) r - 1) * ((double) q - 1)) / 2) * Math.log((double) N);

        score = score - g;

        return score;
    }

}