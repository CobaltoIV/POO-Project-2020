package main;
import java.util.ArrayList;

import classifier.BayesClassifier;
import classifier.graph;
import data.*;
import scoring.*;

/**
 * The BNC class only contains the main of the project. It's where the workflow of the program is controlled
 */
public class BNC {

    public static void main(String[] args) {
        long start_time = System.nanoTime();
        InputHandler TrainData = new InputHandler();
        TrainData.parseFile(args[0]);
        InputHandler TestData = new InputHandler();
        TestData.parseFile(args[1]);

        Edges e = TrainData.decideType(args[2]);
        e.generateScores(TrainData);

        graph g = new graph();
        g.create(e.getMatrix());

        BayesClassifier clf = new BayesClassifier();
        clf.setDAG(g);

        long stop_train = System.nanoTime();

        ArrayList<Integer> predictions;
        predictions=clf.predict(TestData);

        long stop_test = System.nanoTime();

        ArrayList<Integer> real = TestData.getValues().get(TestData.getLabels().get(TestData.getLabels().size()-1));
        int unique =TestData.getValuesUnique().get(TestData.getLabels().get(TestData.getLabels().size()-1)).size();
        double train_time = stop_train - start_time;
        double test_time = stop_test - stop_train;
        clf.getDAG().printGraph(TestData.getLabels());
        System.out.println("Time to build:        " + train_time / 1000000+ " (ms) time");
        System.out.println("Testing the Classifier:");
        clf.printPredictions(predictions);
        System.out.println("Time to test:        " + test_time / 1000000+ " (ms) time");
        clf.measurePerformance(predictions,real,unique);



    }

    // Code to print files
    /* ArrayList<String> labels = inputHandler.getLabels();
        Map<String, ArrayList<Integer>> values = inputHandler.getValues();

        System.out.println(labels);
        for (String key: labels)
            System.out.println(values.get(key));

        inputHandler.parseFile(args[1]);
        labels = inputHandler.getLabels();
        values = inputHandler.getValues();

        System.out.println(labels);
        for (String key: labels)
            System.out.println(values.get(key)); */


}


