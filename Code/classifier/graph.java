package classifier;

import java.util.ArrayList;

import scoring.alpha;

public class graph {
    ArrayList<connection> DAG;

    /**
     * Creates a graph using Prim's Algorithm
     * @param maxtrix matrix containing all the alphas
     */
    public void create(alpha[][] matrix) 
    {   
        int n_features=matrix.length; 
        boolean [] visited = new boolean[n_features];
        this.DAG = getRootNode(matrix);
        visited = updateVisited(this.DAG.get(this.DAG.size()-1).getParent(), visited);
        visited = updateVisited(this.DAG.get(this.DAG.size()-1).getSon(), visited);

        while(this.checkVisited(visited) == 0)
        {
            this.DAG.add(this.getNextNode(visited, matrix));
            visited = updateVisited(this.DAG.get(this.DAG.size()-1).getSon(), visited);
        }

        this.DAG.forEach(node->System.out.println((node.getParent()+1) + " " + (node.getSon()+1)));
    }

    protected connection getNextNode(boolean [] visited, alpha [][] matrix)
    {   
        double max_value = 0;
        int flag_first = 1;
        int i_max = -1;
        int j_max = -1;

        for(int i = 0; i<visited.length; i++)
        {
            if(visited[i] == true)
            {
                for(int a = 0; a<matrix.length; a++)
                {
                    if(visited[a] == false && a != i)
                    {
                        if(flag_first == 1)
                        {
                            i_max = i;
                            j_max = a;
                            max_value = matrix[i][a].getWeigth();
                            flag_first = 0;
                        }

                        else if (max_value < matrix[i][a].getWeigth())
                        {
                            i_max = i;
                            j_max = a;
                            max_value = matrix[i][a].getWeigth();
                        }
                    }
                }
            }
        }
        return new connection(i_max,j_max);
    }

    protected boolean [] updateVisited(int index, boolean[] visited)
    {
        visited[index] = true;
        return visited;
    }

    /**
     * Checks if all nodes are in the graph
     * @param visited
     * @return
     */
    protected int checkVisited(boolean [] visited)
    {
        for(int i = 0; i<visited.length; i++)
        {
            if(visited[i] == false)
            {
                return 0;
            }
        }
        return 1;
    }
    /**
     * Gets the graph's root node and the first son node
     * @param matrix matrix containing all the alphas 
     * @return array list containing the root node the first son node
     */
    protected ArrayList<connection> getRootNode(alpha[][] matrix)
    {
        ArrayList<connection> init = new ArrayList<connection>(); 
        // Flag that indicates the first iteration of matrix values
        int flag_first = 1;

        // Variables used to find the maximum weighted connection
        double max_value = 0;
        int i_max = -1;
        int j_max = -1;

        for(int n=0; n<matrix.length; n++)
        {
            for(int k=0; k<n; k++)
            {   
                // Initializes max_value, i_max and j_max with the values of the first iteration
                if(flag_first == 1)
                {
                    max_value=matrix[k][n].getWeigth();
                    i_max = k;
                    j_max = n;
                    flag_first = 0;
                }
                // Updates max_value, i_max and j_max
                else if(matrix[k][n].getWeigth() > max_value)
                {
                    i_max = k;
                    j_max = n;
                    max_value=matrix[k][n].getWeigth();
                }
            }
        }

        // Create first 2 nodes of the graph
        connection root = new connection(-1, i_max);
        init.add(root);
        connection son = new connection(i_max, j_max);
        init.add(son);

        return init;
    }

}