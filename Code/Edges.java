public abstract class Edges {

    alpha[][] matrix;

    public alpha[][] calcScore() {
       
        return matrix;  
    }

}

class alpha{
    float weigth;
    int [][][] Source;

    alpha(){
        this.weigth = 0;
        this.Source =  new int[2][2][2];
    }
}

class LL_edges extends Edges{

    public alpha[][] calcScore() {
        alpha[][] matrix = 0;  
        return matrix;  
    }
}

class MDL_edges extends Edges{

    public alpha[][] calcScore() {
        alpha[][] matrix = 0;  
        return matrix;  
    }
}