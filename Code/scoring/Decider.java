package scoring;

public class Decider {

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
            System.err.println("Invalid Mode");
            System.exit(0);
            return e = new LL_edges();
        }

    }
}
