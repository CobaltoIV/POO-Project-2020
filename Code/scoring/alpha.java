package scoring;

import java.util.ArrayList;
import java.util.Iterator;

public class alpha {

    private double weigth;
    private int[][][] Source;

    public double getWeigth() {
        return this.weigth;
    }

    public int[][][] getSource() {
        return this.Source;
    }

    public double setWeigth(double weigth) {
        return this.weigth = weigth;
    }

    public int[][][] setSource(int[][][] Source) {
        return this.Source = Source;
    }
    public void printSource(){


        for(int j=0;j<2;j++){
            System.out.println("j ="+j);
            for(int k=0;k<2;k++){
                for(int c=0;c<2;c++){
                    System.out.print(" " + this.getSource()[j][k][c]);
                }
                System.out.println();

            }

        }

    }

    public int[][][] calcN(ArrayList<Integer> parent, ArrayList<Integer> parentvalues, ArrayList<Integer> son,
            ArrayList<Integer> sonvalues, ArrayList<Integer> classes, ArrayList<Integer> classvalues) {

        Iterator<Integer> d_parent = parent.iterator();
        Iterator<Integer> d_son = son.iterator();
        Iterator<Integer> d_class = classes.iterator();

        int[][][] N_jkc = new int[parentvalues.size()][sonvalues.size()][classvalues.size()];

        while (d_parent.hasNext() && d_son.hasNext() && d_class.hasNext()) {

            int p_val = d_parent.next();
            int s_val = d_son.next();
            int c_val = d_class.next();

            for (int k : sonvalues) {

                for (int j : parentvalues) {

                    for (int c : classvalues) {

                        if (p_val == j && s_val == k && c_val == c)
                            N_jkc[j][k][c]++;
                    }
                }

            }

        }

        return N_jkc;
    }

}
