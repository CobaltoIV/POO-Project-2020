package scoring;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The Alpha class has the purpose of calculating and storing the
 * information of a connection between 2 nodes. Each alpha object encapsulates
 * information about the connection. The information stored in each alpha is the counts ((see {@link scoring.alpha#_Source})) and 
 * weight (see {@link scoring.alpha#_weigth})of the connection
 * @author Ricardo Antão
 * @author Francisco Quelincho
 * @author Guilherme Mascarenhas
 */
public class alpha {
    /**
     * The weight of the edge calculated through {@link scoring.Edges#calcScore(alpha)}
     */
    private double _weigth;
    /**
     * The number of every possibility of instance calculated through {@link scoring.alpha#calcN(ArrayList, ArrayList, ArrayList, ArrayList, ArrayList, ArrayList)}
     */
    private int[][][] _Source;

    /**
     * The number of instances calculated through {@link scoring.alpha#calcN(ArrayList, ArrayList, ArrayList, ArrayList, ArrayList, ArrayList)}
     */
    private int _N;
    /**
     * The number of instances regarding class possibilities calculated through {@link scoring.alpha#calcN(ArrayList, ArrayList, ArrayList, ArrayList, ArrayList, ArrayList)}
     */
    private double [] _N_C;
    /**
     * The number of instances regarding class and parent node possibilities calculated through {@link scoring.alpha#calcN(ArrayList, ArrayList, ArrayList, ArrayList, ArrayList, ArrayList)}
     */
    private double [][] _N_K;
    /**
     * The number of instances regarding class and son node possibilities calculated through {@link scoring.alpha#calcN(ArrayList, ArrayList, ArrayList, ArrayList, ArrayList, ArrayList)}
     */
    private double [][] _N_J;

    /**
     * Getter for _weigth
     * @return _weigth (see {@link scoring.alpha#_weigth})
     */
    public double getWeigth() {
        return this._weigth;
    }

    /**
     * Getter for _Source
     * @return _Source (see {@link scoring.alpha#_Source})
     */
    public int[][][] getSource() {
        return this._Source;
    }

    /**
     * Getter for _N
     * @return _N (see {@link scoring.alpha#_N})
     */
    public int getN() {
        return this._N;
    }

    /**
     * Getter for _N_C
     * @return _N_C (see {@link scoring.alpha#_N_C})
     */
    public double [] getN_C() {
        return this._N_C;
    }


    /**
     * Getter for _N_K
     * @return _N_K (see {@link scoring.alpha#_N_K})
     */
    public double [][] getN_K() {
        return this._N_K;
    }

    /**
     * Getter for _N_J
     * @return _N_J (see {@link scoring.alpha#_N_J})
     */
    public double [][] getN_J() {
        return this._N_J;
    }



    /**
     * Setter for _weigth
     *
     * @param _weigth - Weigth to be saved (see {@link scoring.alpha#_weigth})
     */
    public void setWeigth(double _weigth) {
        this._weigth = _weigth;
    }

    /**
     * Setter for _Source
     *
     * @param _Source - Source to be saved (see {@link scoring.alpha#_Source})
     */
    public void setSource(int[][][] _Source) {
        this._Source = _Source;
    }

    /**
     * Prints _Source
     */
    public void printSource() {

        for (int j = 0; j < 2; j++) {
            System.out.println("j =" + j);
            for (int k = 0; k < 2; k++) {
                for (int c = 0; c < 2; c++) {
                    System.out.print(" " + this.getSource()[j][k][c]);
                }
                System.out.println();

            }

        }

    }

    /**
     * Calculates every value of N_ijkc needed for the current edge along with the other Ns from the N_ijkc values
     *
     * @param parent       - Instances of the parent feature
     * @param parentvalues - Unique values of the parent feature
     * @param son          - Instances of the son feature
     * @param sonvalues    - Unique values of the son feature
     * @param classes      - Instances of the classes
     * @param classvalues  - Unique values of the classes
     */
    public void calcN(ArrayList<Integer> parent, ArrayList<Integer> parentvalues, ArrayList<Integer> son,
            ArrayList<Integer> sonvalues, ArrayList<Integer> classes, ArrayList<Integer> classvalues) {

        // Define iterators for each list
        Iterator<Integer> d_parent = parent.iterator();
        Iterator<Integer> d_son = son.iterator();
        Iterator<Integer> d_class = classes.iterator();

        // Initialize Counts matrix
        int[][][] N_jkc = new int[parentvalues.size()][sonvalues.size()][classvalues.size()];

        // Iterate simultaneously between the 3 lists
        while (d_parent.hasNext() && d_son.hasNext() && d_class.hasNext()) {

            int p_val = d_parent.next();
            int s_val = d_son.next();
            int c_val = d_class.next();

            for (int k : sonvalues) {

                for (int j : parentvalues) {

                    for (int c : classvalues) {

                        // See which type of instance it is
                        if (p_val == j && s_val == k && c_val == c)
                            N_jkc[j][k][c]++;
                    }
                }

            }

        }

        // Store number of unique values for each feature and class
        int q = N_jkc.length;
        int r = N_jkc[0].length;
        int s = N_jkc[0][0].length;
        int N = 0;
        // Matrixs where values of N^K_ijc and N^J_ikc and N_C are gonna be stored
        double[][] N_K = new double[q][s];
        double[][] N_J = new double[r][s];
        double[] N_C = new double[s];

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
        this._N=N;
        this._N_K=N_K;
        this._N_J=N_J;
        this._N_C=N_C;
        this._Source=N_jkc;
        return;
    }


}
