import java.util.*;
import scoring.*;

public class testMain {

    public static void main(String[] args) {

        ArrayList<Integer> X1 =new ArrayList<Integer>();
        X1.add(1);
        X1.add(0);
        X1.add(0);
        X1.add(1);
        X1.add(1);
        X1.add(0);
        X1.add(0);
        X1.add(1);

        ArrayList<Integer> X2 =new ArrayList<Integer>();
        X2.add(0);
        X2.add(1);
        X2.add(0);
        X2.add(0);
        X2.add(1);
        X2.add(0);
        X2.add(1);
        X2.add(1);

        ArrayList<Integer> C =new ArrayList<Integer>();
        C.add(0);
        C.add(0);
        C.add(0);
        C.add(0);
        C.add(1);
        C.add(0);
        C.add(1);
        C.add(1);

        int [] values  = {0,1};

        Decider banana = new Decider();
        Edges e = banana.decideType(0);
        alpha a = new alpha();




        a.setSource(a.calcN(X1, values, X2, values, C, values));

        System.out.println("N_111 = " + a.getSource()[0][0][0]);
        System.out.println("N_112 = " + a.getSource()[0][0][1]);
        System.out.println("N_121 = " + a.getSource()[0][1][0]);
        System.out.println("N_211 = " + a.getSource()[1][0][0]);
        System.out.println("N_122 = " + a.getSource()[0][1][1]);
        System.out.println("N_221 = " + a.getSource()[1][1][0]);
        System.out.println("N_212 = " + a.getSource()[1][0][1]);
        System.out.println("N_222 = " + a.getSource()[1][1][1]);



        a.setWeigth(e.calcScore(a));

        System.out.println("Weight = "+ a.getWeigth());








    }


}