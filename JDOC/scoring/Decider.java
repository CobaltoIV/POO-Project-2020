package scoring;

/**
 * The Decider class has the porpuse of deciding which type of Edges object will be built (@see scoring.Edges) 
 * according to the input mode.
 * <p>
 * @author Ricardo Antão
 * @author Francisco Quelincho
 * @author Guilherme Mascarenhas
 */
public class Decider {

    /**
     * Creates the Edges object with the rigth scoring metric or returns an error when mode is invalid
     * @param mode
     * @return
     */
    public Edges decideType(String mode) {

        Edges e;

        if (mode.equals("LL")) {
            e = new LL_edges();
            System.out.println("LL Scorer created");
            return e;
        } else if (mode.equals("MDL")) {
            e = new MDL_edges();
            System.out.println("MDL Scorer created");
            return e;
        } else {
            System.err.println("Error: Invalid Mode");
            System.exit(0);
            return e = new LL_edges();
        }

    }
}
