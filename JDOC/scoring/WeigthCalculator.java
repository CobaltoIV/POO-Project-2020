package scoring;
import data.*;
/**
 * An interface for the objects which take the training data and generate the fully connected graph
 */
public interface WeigthCalculator {

    /**
     * Method to calculate the weigth of a connection
     * @param a alpha corresponding to the connection
     * @return score
     */
    public double calcScore(alpha a);
    /**
     * Method to generate all the connection weigths between nodes
     * @param Data Training patterns
     */
    public void generateScores(InputHandler Data);
    /**
     * Method to print all the connection weigths between nodes
     */
    public void printScores();

}