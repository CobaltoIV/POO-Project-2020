package scoring;

import java.util.ArrayList;

public class alpha{

    private double weigth;
    private int [][][] Source;

    public double getWeigth(){
       return this.weigth;
    }
    public int [][][]getSource(){
        return this.Source;
    }
    public double setWeigth(double weigth){
        return this.weigth = weigth;
    }
    public int[][][] setSource (int [][][] Source){
        return this.Source = Source;
    }


    public int[][][] calcN(ArrayList<Integer> parent, int[] parentvalues,ArrayList<Integer> son, int[] sonvalues , ArrayList<Integer> classes, int[] classvalues)
    {

        int [][][] N_jkc = new int[parentvalues.length][sonvalues.length][classvalues.length];

        for (int d=0;  d< parent.size() ;d++){

            for (int k=0; k<sonvalues.length; k++){

                for (int j=0;j< parentvalues.length;j++){

                    for (int c = 0; c< classvalues.length;c++){

                       if (parent.get(d) == parentvalues[j] && son.get(d) == sonvalues[k] && classes.get(d) == classvalues[c])
                       N_jkc[j][k][c]++;
                    }
                }

            }

        }
        return N_jkc;
    }

}
