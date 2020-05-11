package classifier;


import java.util.*;

import data.InputHandler;


public class BayesClassifier extends Classifiers {

    private graph tree;

    /**
     * @param dAG the dAG to set
     */
    public void setDAG(graph dAG) {
        tree = dAG;
    }

    public graph getDAG(){
        return this.tree;
    }

    public ArrayList<Integer> predict(InputHandler data) {
        Map<String, ArrayList<Integer>> test = data.getValues();
        Map<String, ArrayList<Integer>> unique = data.getValuesUnique();
        ArrayList<String> labels = data.getLabels();
        int n_instances = test.get(labels.get(0)).size();
        ArrayList<Integer> predictions = new ArrayList<Integer>();

        connection actualNode;
        String class_key = labels.get(labels.size() - 1);
        int uniqueClasses = unique.get(class_key).size();
        int sonValue, parentValue;
        double max = 0;
        int max_class = 0;
        double[] PB = new double[uniqueClasses];
        double[] PB_condicionada = new double[uniqueClasses];

        for (int i = 0; i < n_instances; i++) {

            for (int c = 0; c < uniqueClasses; c++) {
                PB[c] = 1;
                PB_condicionada[c] = 1;
                Iterator<connection> auxIterator = this.tree.getDAG().iterator();
                actualNode = auxIterator.next();
                sonValue = test.get(labels.get(actualNode.getSon())).get(i);
                PB[c] *= actualNode.getTeta()[0][sonValue][c];
                while (auxIterator.hasNext()) {
                    actualNode = auxIterator.next();
                    sonValue = test.get(labels.get(actualNode.getSon())).get(i);
                    parentValue = test.get(labels.get(actualNode.getParent())).get(i);
                    PB[c] *= actualNode.getTeta()[parentValue][sonValue][c];
                }
                PB[c] *= this.tree.getNodeC().getTetaC()[c];
            }
            for (int c = 0; c < uniqueClasses; c++) {
                if (c == 0) {
                    max = PB[c];
                    max_class = c;
                } else if (PB[c] > max) {
                    max = PB[c];
                    max_class = c;
                }
            }

            predictions.add(max_class);
        }
        return predictions;
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

    public ArrayList<Double> calc_Sens(ArrayList<int[][]> conf_list, double[] N_C, int N) {

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

    public ArrayList<Double> calc_Spec(ArrayList<int[][]> conf_list, double[] N_C, int N) {

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

    public ArrayList<Double> calc_F1(ArrayList<int[][]> conf_list, double[] N_C, int N) {

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

    public void measurePerformance(ArrayList<Integer> predictions ,ArrayList<Integer> real, int n_unique, double[] N_C, int N){

        ArrayList<int[][]> conf_list = this.calcConfusionMatrix(predictions, real, n_unique);
        ArrayList<Double> Sens = this.calc_Sens(conf_list, N_C, N);
        ArrayList<Double> Spec =this.calc_Spec(conf_list, N_C, N);
        ArrayList<Double> F1 =this.calc_F1(conf_list, N_C, N);
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