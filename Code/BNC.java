import java.util.ArrayList;

import classifier.BayesClassifier;
import classifier.graph;
import data.*;
import scoring.*;
//import java.util.*;

public class BNC {

    public static void main(String[] args) {
        InputHandler TrainData = new InputHandler();
        TrainData.parseFile(args[0]);
        InputHandler TestData = new InputHandler();
        TestData.parseFile(args[1]);
        Decider banana = new Decider();
        Edges e = banana.decideType(args[2]);
        e.generateScores(TrainData);
        graph g = new graph();
        g.create(e.getMatrix());
        BayesClassifier clf = new BayesClassifier();
        clf.setDAG(g);
        ArrayList<Integer> predictions;
        predictions=clf.predict(TestData);
        predictions.forEach(c->System.out.println(c));


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


