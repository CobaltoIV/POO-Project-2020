package classifier;


import java.util.*;

import data.InputHandler;

/**
 * The BayesClassifier class extends the Classifiers class {@link classifier.Classifiers}
 * A BayesClassifier object has a strucutre of nodes (graph) that is used to elaborate a 
 * list of predictions.
 * 
 * @author Ricardo Antão
 * @author Francisco Quelincho
 * @author Guilherme Mascarenhas
 */
public class BayesClassifier extends Classifiers {

    /**
     * Graph used by the classifier {see @link classifier.graph}
     */
    private graph tree;

    /**
     * Setter for DAG
     * @param DAG - DAG to be saved {@link classifier.graph#DAG}
     */
    public void setDAG(graph DAG) {
        tree = DAG;
    }

    /**
     * Getter for DAG
     * @return tree (DAG) {@link classifier.graph#DAG}
     */
    public graph getDAG(){
        return this.tree;
    }

    /**
     * See {@link classifier.Classifiers#predict(data.InputHandler)}
     * @param data - received data {@link data.InputHandler}
     * @return predict - list of predictions
     */
    public ArrayList<Integer> predict(InputHandler data) {
        //Gets all needed Values
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

        //Calculates the prediction for every instance 
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

}