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

}