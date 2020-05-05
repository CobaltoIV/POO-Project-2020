package scoring;

import java.util.*;
import data.*;

public class Edges {

    protected alpha[][] matrix;

    public double calcScore(alpha a) {

        double score = 33.9;

        return score;
    }

    public alpha[][] getMatrix() {
        return this.matrix;
    }

    public void setMatrix(int n_features) {
        this.matrix = new alpha[n_features][n_features];
    }

    public void setMatrixElement(alpha a, int parent, int son) {
        this.matrix[parent][son] = a;
    }

    public void printMatrix() {
        int s = this.matrix.length;
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < s; j++) {
                System.out.print(" " + this.matrix[i][j].getWeigth());
            }
            System.out.println();
        }
    }

    public void generateScores(InputHandler Data) {

        int n_features = Data.getLabels().size() - 1;

        int i = 0, j = 0;

        this.setMatrix(n_features);

        Map<String, ArrayList<Integer>> train = Data.getValues();
        Map<String, ArrayList<Integer>> unique = Data.getValuesUnique();
        ArrayList<String> labels = Data.getLabels();
        ArrayList<String> features = labels;
        features.remove(features.size() - 1);

        String class_key = labels.get(labels.size() - 1);
        for (String parent : features) {

            for (String son : features) {
                alpha a = new alpha();
                if (j > i) {
                    a.setSource(a.calcN(train.get(parent), unique.get(parent), train.get(son), unique.get(son),
                            train.get(class_key), unique.get(class_key)));
                    a.setWeigth(this.calcScore(a));
                    this.setMatrixElement(a, i, j);
                } else
                    this.setMatrixElement(a, i, j);
                j++;
            }
            j = 0;
            i++;
        }

        this.printMatrix();

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
        double[][] N_K = new double[q][s];
        double[][] N_J = new double[r][s];
        double[] N_C = new double[s];

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

                    if ((N_K[j][c] * N_J[k][c]) != 0) {

                        temp = ((N_C[c] * (double) N_jkc[j][k][c]) / (N_K[j][c] * N_J[k][c]));
                        p = (double) N_jkc[j][k][c] / N;

                        if (temp != 0) {

                            System.out.println("temp = " + temp);
                            n = Math.log10(temp) / Math.log10(2);

                            score += p * n;

                            System.out.println("Passou aqui" + n);
                            System.out.println("Passou aqui" + p);
                            System.out.println("Passou aqui" + score);
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

                        temp = ((N_C[c] * (double) N_jkc[j][k][c]) / (N_K[j][c] * N_J[k][c]));
                        p = (double) N_jkc[j][k][c] / N;

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

        double g = (((double) s * ((double) r - 1) * ((double) q - 1)) / 2) * Math.log((double) N);

        score = score - (((double) s * ((double) r - 1) * ((double) q - 1)) / 2) / Math.log((double) N);
        System.out.println("Score = "+score);
        return score;
    }

}