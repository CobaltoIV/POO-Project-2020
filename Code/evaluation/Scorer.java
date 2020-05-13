package evaluation;

import java.util.ArrayList;

/**
 * The interface Scorer contains the functions t evaluate the classifier
 * performance.
 */
public interface Scorer {

        /**
         * Calculates the Confusions matrixes taking each possible value of the class as
         * positive
         *
         * @param predictions  predicted values
         * @param real         actual values
         * @param n_unique     number of unique values
         * @return Confusion Matrix for each value
         */
        public ArrayList<int[][]> calcConfusionMatrix(ArrayList<Integer> predictions, ArrayList<Integer> real,
                        int n_unique);

        /**
         * Calculates the sensitivy for each confusion matrix
         * @param conf_list  list of confusion matrixes
         * @param N_C {@link scoring.alpha#_N_C}
         * @param N  {@link scoring.alpha#_N}
         * @return
         */
        public ArrayList<Double> calc_Sens(ArrayList<int[][]> conf_list);

        /**
         * Calculates the specificity for each confusion matrix
         * @param conf_list  list of confusion matrixes
         * @return
         */
        public ArrayList<Double> calc_Spec(ArrayList<int[][]> conf_list);

        /**
         * Calculates the F1 Score for each confusion matrix
         * @param conf_list  list of confusion matrixes
         * @return
         */
        public ArrayList<Double> calc_F1(ArrayList<int[][]> conf_list);

        /**
         * Calculates accuracy for each confusion matrix
         * @param predictions predicted values
         * @param real real values
         * @return
         */
        public double calc_Acc(ArrayList<Integer> predictions, ArrayList<Integer> real);

        /**
         * Prints metrics
         * @param Acc Accuracy
         * @param Sens Sensitivity
         * @param Spec Specificity
         * @param F1 F1 Score
         */
        public void printMetrics(Double Acc, ArrayList<Double> Sens, ArrayList<Double> Spec, ArrayList<Double> F1);

        /**
         * Method that organizes all the methods before
         * @param predictions
         * @param real
         * @param n_unique
         */
        public void measurePerformance(ArrayList<Integer> predictions, ArrayList<Integer> real, int n_unique);

        /**
         * Prints predictions
         * @param predictions
         */
        public void printPredictions(ArrayList<Integer> predictions);

}