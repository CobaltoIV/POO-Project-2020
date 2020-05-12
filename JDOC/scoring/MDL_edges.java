package scoring;

/**
 * Extends class Edges. It uses the LL score to calculate the weigths
 * <p>
 * @author Ricardo Antão
 * @author Francisco Quelincho
 * @author Guilherme Mascarenhas
 */

public class MDL_edges extends Edges
{

    public double calcScore(alpha a)
    {
        double score = 0;
        int[][][] N_jkc = a.getSource();
        double [][] N_K = a.getN_K();
        double [][] N_J = a.getN_J();
        double [] N_C = a.getN_C();
        int N = a.getN();

        int q = N_jkc.length;
        int r = N_jkc[0].length;
        int s = N_jkc[0][0].length;

        double p;
        double n;
        double temp;

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

        // Only difference between LL and MDL scores
        double g = (((double) s * ((double) r - 1) * ((double) q - 1)) / 2) * Math.log((double) N);

        score = score - g;

        return score;
    }

}