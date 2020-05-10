package evaluation;
import java.util.ArrayList;
public interface Scorer{

    public  ArrayList<int[][]> calcConfusionMatrix(ArrayList<Integer> predictions ,ArrayList<Integer> real, int n_unique);

    public  ArrayList<Double> calc_Sens(ArrayList<int[][]> conf_list, int[] N_C, int N);

    public  ArrayList<Double> calc_Spec(ArrayList<int[][]> conf_list, int[] N_C, int N);

    public  ArrayList<Double> calc_F1(ArrayList<int[][]> conf_list,int[] N_C, int N);

    public  double calc_Acc(ArrayList<Integer> predictions ,ArrayList<Integer> real);

    public void printMetrics(Double Acc, ArrayList<Double> Sens, ArrayList<Double> Spec, ArrayList<Double> F1);



}