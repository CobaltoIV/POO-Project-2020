package scoring;
import data.*;
/**
 * An interface for the objects which take the training data and generate the fully connected graph
 */
public interface WeigthCalculator {

    /**
     * Method to calculate the weigth of a connection. It
     * depends on the type of subclass of Edges, where each one will override this method to use its own criterion.
     * @param   a - Connection to be weighed(see {@link scoring.alpha})
     * @return {@link scoring.alpha#_weigth}
     */
    public double calcScore(alpha a);
       
    /**
     * Method to generate the adjacency matrix
     * @param Data - Training patterns (see {@link data.InputHandler})
     */
    public void generateScores(InputHandler Data);
    /**
     * Method to print the adjacency matrix
     */
    public void printScores();

}