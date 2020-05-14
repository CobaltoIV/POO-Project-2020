package classifier;
import scoring.alpha;

/**
 * The connection class has the purpose of saving information about the connections
 * in the graph. Since the graph uses directed connections, instead of storing each node 
 * the connections between them are stored and how they relate (probability of different values)
 * Each connection object encapsulates information about the direction of the connection (parent and son)
 * and how the son's values are affected by the parent
 * 
 * @author Ricardo Antão
 * @author Francisco Quelincho
 * @author Guilherme Mascarenhas
 */
public class connection {

    /**
     * Son in connection
     */
    private int _son_node;

    /**
     * Parent in connection
     */
    private int _parent_node;

    /**
     * Tetas related to each connection
     */
    private double [][][] _teta;

    /**
     * Method that caluclates teta values for all nodes exluding the root
     * 
     * @param matrix matrix containing all the alphas which represent 
     * the weight of each connection {@link scoring.Edges#matrix}
     * @param parent information about the parent in one connection {@link classifier.connection#_parent_node}
     * @param son information about the son in one connection {@link classifier.connection#_son_node}
     * @return teta's for a certain connection {@link classifier.connection#_teta}
     */

    public double [][][] calcTeta(alpha [][] matrix, int parent,  int son)
    {
        alpha a = matrix[parent][son];

        int q = a.getSource().length;
        int r = a.getSource()[0].length;
        int s = a.getSource()[0][0].length;

        double [][][] teta = new double[q][r][s];

        for (int k = 0; k < r; k++)
        {

            for (int j = 0; j < q; j++)
            {

                for (int c = 0; c < s; c++)
                {
                    teta [j][k][c] =( (double)a.getSource()[j][k][c] + 0.5 ) / (a.getN_K()[j][c] + r * 0.5);
                }
            }

        }
        return teta;
    }

    /**
     * Method that caluclates teta values for the root node
     * 
     * @param matrix matrix containing all the alphas which represent 
     * the weight of each connection {@link scoring.Edges#matrix}
     * @param parent information about the parent in one connection {@link classifier.connection#_parent_node}
     * @param son information about the son in one connection {@link classifier.connection#_son_node}
     * @return teta's for a certain connection {@link classifier.connection#_teta}
     */
    public double [][][] calcTetaRoot(alpha [][] matrix, int parent,  int son)
    {
        alpha a = matrix[parent][son];

        int q = 1;
        int r = a.getSource()[0].length;
        int s = a.getSource()[0][0].length;
        int j = 0;

        double [][][] teta = new double[q][r][s];

        for (int k = 0; k < r; k++)
        {
                for (int c = 0; c < s; c++)
                {
                    teta [j][k][c] =( (double)a.getN_J()[k][c] + 0.5 ) / (a.getN_C()[c] + r * 0.5);
                }

        }
        return teta;
    }

    /**
     * Connection's contructor which initializes parent and son's value 
     * @param parent parent node of connection 
     * @param son son node of connection 
     */
    connection(int parent, int son)
    {
        this._parent_node=parent;
        this._son_node=son;
    }

    /**
     * Getter for _parent_node
     * @return _parent_node (see {@link classifier.connection#_parent_node})
     */
    public int getParent()
    {
        return this._parent_node;
    }

    /**
     * Getter for _son_node
     * @return _son_node (see {@link classifier.connection#_son_node})
     */
    public int getSon()
    {
        return this._son_node;
    }

    /**
     * Setter for _teta
     *
     * @param teta - teta to be saved (see {classifier.connection#_teta})
     */
    public void setTeta(double [][][] teta)
    {
        this._teta=teta;
    }

    /**
     * Getter for _teta
     * @return _teta (see {@link classifier.connection#_teta})
     */
    public double [][][] getTeta()
    {
        return this._teta;
    }

}