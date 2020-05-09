package classifier;
import scoring.alpha;

public class connection {
    // _son_node's value equals the variable i in Nijkc  
    private int _son_node;
    private int _parent_node;
    private double [][][] teta;

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

    connection(int parent, int son)
    {
        this._parent_node=parent;
        this._son_node=son;
    }

    public int getParent()
    {
        return this._parent_node;
    }

    public int getSon()
    {
        return this._son_node;
    }
}