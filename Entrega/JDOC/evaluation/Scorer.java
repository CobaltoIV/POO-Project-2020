package evaluation;

import java.util.ArrayList;

/**
 * The interface Scorer contains the functions to evaluate the classifier
 * performance.
 * It is to be implemented by all classifiers.
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
         * @return Sensitivity
         */
        public ArrayList<Double> calc_Sens(ArrayList<int[][]> conf_list);

        /**
         * Calculates the specificity for each confusion matrix
         * @param conf_list  list of confusion matrixes
         * @return Specificity
         */
        public ArrayList<Double> calc_Spec(ArrayList<int[][]> conf_list);

        /**
         * Calculates the F1 Score for each confusion matrix
         * @param conf_list  list of confusion matrixes
         * @return F1 Score
         */
        public ArrayList<Double> calc_F1(ArrayList<int[][]> conf_list);

        /**
         * Calculates accuracy for each confusion matrix
         * @param predictions predicted values
         * @param real real values
         * @return Accuracy
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
         * @param predictions predicted values
         * @param real real values
         * @param n_unique number of unique values
         */
        public void measurePerformance(ArrayList<Integer> predictions, ArrayList<Integer> real, int n_unique);

        /**
         * Prints predictions
         * @param predictions predicted values
         */
        public void printPredictions(ArrayList<Integer> predictions);

}