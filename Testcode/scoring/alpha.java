package scoring;

import java.util.ArrayList;
import java.util.Iterator;
/**
 * Alpha is a class which has the purpose of  calculating and
 * storing the information of a connection between 2 nodes.
 * Each alpha object encapsulates information about the connection.
 * This state of information contains:
 * <ul>
 * <li> _weigth
 * <li> _Source
 * <p>
 *
 * @author Ricardo Antão
 * @author Francisco Quelincho
 * @author Guilherme Mascarenhas
 */
public class alpha {

    /**
     * The weight of the edge
     */
    private double _weigth;
    /**
     * The counts of every possibility of instance (N_ijkc)
     */
    private int[][][] _Source;

    /**
     * Getter for _weigth
     */
    public double getWeigth() {
        return this._weigth;
    }
     /**
     * Getter for _Source
     */
    public int[][][] getSource() {
        return this._Source;
    }
     /**
     * Setter for _weigth
     * @param _weigth - Weigth to be saved
     */
    public double setWeigth(double _weigth) {
        return this._weigth = _weigth;
    }

    /**
     * Setter for _Source
     * @param _Source - Source to be saved
     * @return _Source
     */
    public int[][][] setSource(int[][][] _Source) {
        return this._Source = _Source;
    }
    /**
     * Prints _Source
     */
    public void printSource(){


        for(int j=0;j<2;j++){
            System.out.println("j ="+j);
            for(int k=0;k<2;k++){
                for(int c=0;c<2;c++){
                    System.out.print(" " + this.getSource()[j][k][c]);
                }
                System.out.println();

            }

        }

    }

    /**
     * Calculates every value of N_ijkc needed for the current edge
     * @param parent - Instances of the parent feature
     * @param parentvalues - Unique values of the parent feature
     * @param son - Instances of the son feature
     * @param sonvalues - Unique values of the son feature
     * @param classes - Instances of the classes
     * @param classvalues - Unique values of the classes
     * @return _Source
     */
    public int[][][] calcN(ArrayList<Integer> parent, ArrayList<Integer> parentvalues, ArrayList<Integer> son,
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

        return N_jkc;
    }

}
