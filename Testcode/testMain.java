import java.util.*;
import scoring.*;

public class testMain {
    
    public static void main(String[] args) {
        
        int [] X1 = {1,0,0,1,1,0,0,1};
        int [] X2 = {0,1,0,0,1,0,1,1};
        int [] C  = {1,1,0,1,0,0,1,0};
        int [] values  = {0,1}; 
        
        Decider banana = new Decider();
        Edges e = banana.decideType(0);
        alpha a = new alpha();

        System.out.println(Arrays.toString(X1)+ "\n" + Arrays.toString(X2)+ "\n" + Arrays.toString(C));


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

        System.out.println(a.getWeigth());




       
        
    
        
    }
    

}