package classifier;

import scoring.alpha;

public class NodeC {
    private double [] tetaC;

    public double[] calcTetaC(alpha a)
    {
        int s = a.getSource()[0][0].length;
        this.tetaC = new double[s];
        double [] n_c = a.getN_C();
        int n = a.getN();

        for(int i=0; i<2; i++)
        {
            tetaC[i] = (n_c[i] + 0.5) / ((double)n + (double)s*0.5 );
        }
        return tetaC;
    }

    public void setTetaC(double [] teta)
    {
        this.tetaC=teta;
    }

    public double [] getTetaC()
    {
        return this.tetaC;
    }
}