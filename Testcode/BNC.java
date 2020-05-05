import java.util.ArrayList;
import java.util.Map;

//import java.util.*;

public class BNC {

    /* protected Graph G;

    public Graph buildGraph(Edges connections){
        return 0;
    }

    public int[] classify(Map data){

       return results;
    } */

    public static void main(String[] args) { 
        InputHandler inputHandler = new InputHandler();
        inputHandler.parseFile(args[0]);
        ArrayList<String> labels = inputHandler.getLabels();
        Map<String, ArrayList<Integer>> values = inputHandler.getValues();

        System.out.println(labels);
        for (String key: labels)
            System.out.println(values.get(key));

        inputHandler.parseFile(args[1]);
        labels = inputHandler.getLabels();
        values = inputHandler.getValues();

        System.out.println(labels);
        for (String key: labels)
            System.out.println(values.get(key));
    }

    

}

/* class Graph{
    protected java.util.List<Node> Seats;
   
} */

/* class Nodes{
   // to be decided protected parent;

    int[][][] teta;
} */

