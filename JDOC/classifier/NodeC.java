package classifier;

import scoring.alpha;

/**
 * The NodeC class represents the only node different from the others in the graph (node C).
 * Each NodeC object encapsulates information about the probability of C (node) taking
 * certain values.
 * 
 * @author Ricardo Antão
 * @author Francisco Quelincho
 * @author Guilherme Mascarenhas
 */

public class NodeC {
    /**
     * The tetaC calculated through {@link classifier.NodeC#calcTetaC(alpha)}
     */
    private double [] _tetaC;

    /**
     * Method that calculates tetaC values for nodeC
     * @param a {@link scoring.alpha}
     * @return tetaC {@link classifier.NodeC}
     */
    public double[] calcTetaC(alpha a)
    {   
        //Gets the info cointained in alpha
        int s = a.getSource()[0][0].length;
        double [] n_c = a.getN_C();
        int n = a.getN();

        this._tetaC = new double[s];

        //Calculates tetaC for the possible C values
        for(int i=0; i<s; i++)
        {
            _tetaC[i] = (n_c[i] + 0.5) / ((double)n + (double)s*0.5 );
        }
        return _tetaC;
    }

    /**
     * Setter for tetaC
     *
     * @param teta - teta to be saved (see {@link classifier.NodeC#_tetaC})
     */
    public void setTetaC(double [] teta)
    {
        this._tetaC=teta;
    }

    /**
     * Getter for tetaC
     * @return tetaC (see {@link classifier.NodeC#_tetaC})
     */
    public double [] getTetaC()
    {
        return this._tetaC;
    }
}