package classifier;

import evaluation.Scorer;
import java.util.*;
import data.*;

public abstract class Classifiers implements Scorer{

    public ArrayList<Integer> predict(InputHandler data) {

        return new ArrayList<Integer>();
    }

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

    public ArrayList<Double> calc_Sens(ArrayList<int[][]> conf_list) {

        ArrayList<Double> Sens = new ArrayList<Double>();
        Iterator<int[][]> matrix = conf_list.iterator();
        double[] N_C  = new double[conf_list.size()];
        int c = 0;
        double N =0;
        while (matrix.hasNext()) {
            int[][] aux = matrix.next();

            double TP = (double) aux[0][0];
            double FN = (double) aux[1][0];
            double FP = (double) aux[0][1];
            double TN = (double) aux[1][1];
            N = TP+FN+FP+TN;
            N_C[c] = TP+FN;
            Double sensitivity = (TP) / (TP + FN);
            Sens.add(sensitivity);
            c++;
        }

        double num = 0;
        for (int i = 0; i < N_C.length; i++) {
            num += ((double) N_C[i]) * (Sens.get(i));
        }
        Double weigthed = num / ((double) N);

        Sens.add(weigthed);
        return Sens;
    }

    public ArrayList<Double> calc_Spec(ArrayList<int[][]> conf_list) {

        ArrayList<Double> Spec = new ArrayList<Double>();
        Iterator<int[][]> matrix = conf_list.iterator();
        double[] N_C  = new double[conf_list.size()];
        int c = 0;
        double N =0;
        while (matrix.hasNext()) {
            int[][] aux = matrix.next();


            double TP = (double) aux[0][0];
            double FN = (double) aux[1][0];
            double FP = (double) aux[0][1];
            double TN = (double) aux[1][1];
            N = TP+FN+FP+TN;
            N_C[c] = TP+FN;

            Double specificity = (TN) / (TN + FP);
            Spec.add(specificity);
            c++;
        }

        double num = 0;
        for (int i = 0; i < N_C.length; i++) {
            num += ((double) N_C[i]) * (Spec.get(i));
        }
        Double weigthed = num / ((double) N);

        Spec.add(weigthed);
        return Spec;
    }

    public ArrayList<Double> calc_F1(ArrayList<int[][]> conf_list) {

        ArrayList<Double> F1 = new ArrayList<Double>();
        Iterator<int[][]> matrix = conf_list.iterator();
        double[] N_C  = new double[conf_list.size()];
        int c = 0;
        double N =0;
        while (matrix.hasNext()) {
            int[][] aux = matrix.next();

            double TP = (double) aux[0][0];
            double FN = (double) aux[1][0];
            double FP = (double) aux[0][1];
            double TN = (double) aux[1][1];
            N = TP+FN+FP+TN;
            N_C[c] = TP+FN;


            Double f1_score = (2 * TP) / (2 * TP + FN + FP);
            F1.add(f1_score);
            c++;
        }

        double num = 0;
        for (int i = 0; i < N_C.length; i++) {
            num += ((double) N_C[i]) * (F1.get(i));
        }
        Double weigthed = num / N;

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

            if (pred_val == real_val)
                hit++;
            else
                miss++;
        }

        Acc = hit / (miss + hit);

        return Acc;

    }

    public void printMetrics(Double Acc, ArrayList<Double> Sens, ArrayList<Double> Spec, ArrayList<Double> F1){
        Iterator<Double> sens = Sens.iterator();
        Iterator<Double> spec = Spec.iterator();
        Iterator<Double> f1 = F1.iterator();

        int i =0;
        System.out.print("Resume:     ");
        System.out.printf("Acc: %.2f",Acc);
        System.out.println();

        System.out.print("            Sensitivity: [");
        while(sens.hasNext()){
            if(i == Sens.size()-1)
            System.out.printf("W_Avg : %.2f  ",sens.next());
            else
            System.out.printf(i + ": %.2f  ",sens.next());
            i++;
        }
        System.out.println(" ]");

        i = 0;
        System.out.print("            Specificity: [");
        while(spec.hasNext()){
            if(i == Spec.size()-1)
            System.out.printf( "W_Avg: %.2f  " , spec.next());
            else
            System.out.printf(i + ": %.2f  " , spec.next());
            i++;
        }
        System.out.println(" ]");

        i=0;
        System.out.print("            F1_score: [");
        while(f1.hasNext()){
            if(i == F1.size()-1)
            System.out.printf("W_Avg: %.2f  " , f1.next());
            else
            System.out.printf(i + ": %.2f  " ,f1.next());
            i++;
        }
        System.out.println(" ]");


    }

    public void measurePerformance(ArrayList<Integer> predictions ,ArrayList<Integer> real, int n_unique){

        ArrayList<int[][]> conf_list = this.calcConfusionMatrix(predictions, real, n_unique);
        ArrayList<Double> Sens = this.calc_Sens(conf_list);
        ArrayList<Double> Spec =this.calc_Spec(conf_list);
        ArrayList<Double> F1 =this.calc_F1(conf_list);
        Double Acc = this.calc_Acc(predictions, real);
        this.printMetrics(Acc, Sens, Spec, F1);


    }

    public void printPredictions(ArrayList<Integer> predictions){
        Iterator<Integer> pred = predictions.iterator();
        int aux;
        int i=1;
        while(pred.hasNext()){
            aux = pred.next();

            System.out.println("-> instance "+ i +":   " + aux );
            i++;
        }
    }

}

