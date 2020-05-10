package evaluation;

import java.util.*;


public class dummy implements Scorer {

    public ArrayList<int[][]> calcConfusionMatrix(ArrayList<Integer> predictions, ArrayList<Integer> real,
            int n_unique) {
        ArrayList<int[][]> conf_list = new ArrayList<int[][]>();
        Iterator<Integer> pred = predictions.iterator();
        Iterator<Integer> actual = real.iterator();

        for (int i = 0; i < n_unique; i++) {

            int[][] temp = new int[2][2];
            while (pred.hasNext() && actual.hasNext()) {
                int pred_val = pred.next();
                int real_val = actual.next();

                if (pred_val == i) {
                    if (real_val == i) {
                        // TP
                        temp[0][0]++;
                    }
                    if (real_val != i) {
                        // FP
                        temp[0][1]++;
                    }
                }
                if (pred_val != i) {
                    if (real_val != i) {
                        // TN
                        temp[1][1]++;
                    }
                    if (real_val == i) {
                        // FN
                        temp[1][0]++;
                    }
                }

            }
            // Reset Iterators
            pred = predictions.iterator();
            actual = real.iterator();
            // add confusion matrix
            conf_list.add(temp);
        }

        return conf_list;
    }

    public ArrayList<Double> calc_Sens(ArrayList<int[][]> conf_list, int[] N_C, int N) {

        ArrayList<Double> Sens = new ArrayList<Double>();
        Iterator<int[][]> matrix = conf_list.iterator();
        while (matrix.hasNext()) {
            int[][] aux = matrix.next();

            double TP = (double) aux[0][0];
            double FN = (double) aux[1][0];
            Double sensitivity = (TP) / (TP + FN);
            Sens.add(sensitivity);
        }

        double num = 0;
        for (int i = 0; i < N_C.length; i++) {
            num += ((double) N_C[i]) * (Sens.get(i));
        }
        Double weigthed = num / ((double) N);

        Sens.add(weigthed);
        return Sens;
    }

    public ArrayList<Double> calc_Spec(ArrayList<int[][]> conf_list, int[] N_C, int N) {

        ArrayList<Double> Spec = new ArrayList<Double>();
        Iterator<int[][]> matrix = conf_list.iterator();
        while (matrix.hasNext()) {
            int[][] aux = matrix.next();

            double TN = (double) aux[1][1];
            double FP = (double) aux[0][1];
            Double specificity = (TN) / (TN + FP);
            Spec.add(specificity);
        }

        double num = 0;
        for (int i = 0; i < N_C.length; i++) {
            num += ((double) N_C[i]) * (Spec.get(i));
        }
        Double weigthed = num / ((double) N);

        Spec.add(weigthed);
        return Spec;
    }



    public ArrayList<Double> calc_F1(ArrayList<int[][]> conf_list, int[] N_C, int N) {

        ArrayList<Double> F1 = new ArrayList<Double>();
        Iterator<int[][]> matrix = conf_list.iterator();
        while (matrix.hasNext()) {
            int[][] aux = matrix.next();

            double TP = (double) aux[0][0];
            double FN = (double) aux[1][0];
            double FP = (double) aux[0][1];
            Double f1_score = (2 * TP) / (2 * TP + FN + FP);
            F1.add(f1_score);
        }

        double num = 0;
        for (int i = 0; i < N_C.length; i++) {
            num += ((double) N_C[i]) * (F1.get(i));
        }
        Double weigthed = num / ((double) N);

        F1.add(weigthed);
        return F1;

    }

    public double calc_Acc(ArrayList<Integer> predictions, ArrayList<Integer> real) {

        double Acc;
        Iterator<Integer> pred = predictions.iterator();
        Iterator<Integer> actual = real.iterator();

        double hit = 0;
        double miss = 0;
        while (pred.hasNext() && actual.hasNext()) {
            int pred_val = pred.next();
            int real_val = actual.next();

            if(pred_val == real_val)
            hit++;
            else
            miss++;
        }

        Acc = hit/(miss+hit);

        return Acc;

    }

    public void printMetrics(Double Acc, ArrayList<Double> Sens, ArrayList<Double> Spec, ArrayList<Double> F1){
        Iterator<Double> sens = Sens.iterator();
        Iterator<Double> spec = Spec.iterator();
        Iterator<Double> f1 = F1.iterator();

        System.out.println("Acc: " + Acc);

        System.out.print("Sensitivity: [");
        while(sens.hasNext()){
            System.out.print(" " + sens.next());
        }
        System.out.println(" ]");

        System.out.print("Specificity: [");
        while(spec.hasNext()){
            System.out.print(" " + spec.next());
        }
        System.out.println(" ]");

        System.out.print("F1_score: [");
        while(f1.hasNext()){
            System.out.print(" " + f1.next());
        }
        System.out.println(" ]");


    }

}