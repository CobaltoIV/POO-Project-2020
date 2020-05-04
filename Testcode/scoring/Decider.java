package scoring;


public class Decider{

    public Edges decideType(int mode){

        Edges e;

        if (mode==0){
            e= new LL_edges();
            return e;
        }
        else{
             e = new MDL_edges();
             return e;
        }

    }
}
