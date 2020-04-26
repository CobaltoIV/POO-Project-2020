package scoring;

import java.util.Arrays;

public class Edges {

    protected alpha[][] matrix;

    public double calcScore(alpha a) {

        double score = 33.9;

        return score;
    }

    public alpha[][] getMatrix() {
        return this.matrix;
    }

    public void setMatrixElement(alpha a, int parent, int son) {
        this.matrix[parent][son] = a;
    }

}

class LL_edges extends Edges {

    
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

        System.out.println("N_K11 = " + N_K[0][0]);
        System.out.println("N_K12 = " + N_K[0][1]);
        System.out.println("N_K21 = " + N_K[1][0]);
        System.out.println("N_K22 = " + N_K[1][1]);
        System.out.println("N_J11 = " + N_J[0][0]);
        System.out.println("N_J12 = " + N_J[0][1]);
        System.out.println("N_J21 = " + N_J[1][0]);
        System.out.println("N_J22 = " + N_J[1][1]);
        System.out.println("N" + N);
        System.out.println("N_C1"+ N_C[0]);
        System.out.println("N_C2"+ N_C[1]);
        

        for (int k = 0; k < r; k++) {

            for (int j = 0; j < q; j++) {

                for (int c = 0; c < s; c++) {
                    temp = (double) ((N_C[c] * N_jkc[j][k][c]) / (N_K[j][c] * N_J[k][c]));
                    p = (double) N_jkc[j][k][c] / N;

                    if (temp != 0) {

                        n = Math.log10(temp) / Math.log10(2);

                        score += p * n;

                    } else {
                        score += 0;
                    }

                }
            }

        }

        return score;
    }

}

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
                    temp = (double) ((N_C[c] * N_jkc[j][k][c]) / (N_K[j][c] * N_J[k][c]));
                    p = (double) N_jkc[j][k][c] / N;

                    if (temp != 0) {

                        n = Math.log10(temp) / Math.log10(2);

                        score += p * n;

                    } else {
                        score += 0;
                    }

                }
            }

        }

        score = score - ((s*(r-1)*(q-1))/2)/Math.log((double) N);
        return score;
    }

}