//Ford Fulkerson algorithm for Random Graphs
import java.util.*;
import java.lang.*;
import java.io.*;
 
class FordFulkerson
{
	//Used to keep track of the running time of the program
	static long startTime = System.currentTimeMillis();
    static int V ; //Number of vertices in graph
 
    /* Returns true if there is a path from source 's' to sink
      't' in residual graph. Also fills parent[] to store the
      path */
    boolean bfs(int residualGraph[][], int s, int t, int parent[])
    {
        // Create a visited array and mark all vertices as not
        // visited
        boolean visited[] = new boolean[V];
        for(int i=0; i<V; ++i)
            visited[i]=false;
 
        // Create a queue, enqueue source vertex and mark
        // source vertex as visited
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        visited[s] = true;
        parent[s]=-1;
 
        // Standard BFS Loop
        while (queue.size()!=0)
        {
            int u = queue.poll();
 
            for (int v=0; v<V; v++)
            {
                if (visited[v]==false && residualGraph[u][v] > 0)
                {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
 
        // If we reached sink in BFS starting from source, then
        // return true, else false
        return (visited[t] == true);
    }
 
    // Returns the maximum flow from s to t in the given graph
    int fordFulkerson(int graph[][], int s, int t)
    {
        int u, v;
        
        long endTime   = System.currentTimeMillis();
    	long totalTime = endTime - startTime;
    	System.out.println("The Time Taken is... " + totalTime + " ms");
    	
    	System.out.println("The Source is "+s+" and the Sink is "+t);
    	
    	//If the source and sink are same then the program terminates
        if(s==t)
        System.out.println("Cannot calculate Max Flow as source and sink are the same\n");

        // Create a residual graph and fill the residual graph
        // with given capacities in the original graph as
        // residual capacities in residual graph
 
        // Residual graph where residualGraph[i][j] indicates
        // residual capacity of edge from i to j (if there
        // is an edge. If residualGraph[i][j] is 0, then there is
        // not)
        int residualGraph[][] = new int[V][V];
 
        for (u = 0; u < V; u++)
            for (v = 0; v < V; v++)
                residualGraph[u][v] = graph[u][v];
 
        // This array is filled by BFS and to store path
        int parent[] = new int[V];
 
        int max_flow = 0;  // There is no flow initially
 
        // Augment the flow while there is path from source
        // to sink
        while (bfs(residualGraph, s, t, parent))
        {
            // Find minimum residual capacity of the edges
            // along the path filled by BFS. Or we can say
            // find the maximum flow through the path found.
            int path_flow = Integer.MAX_VALUE;
            for (v=t; v!=s; v=parent[v])
            {
                u = parent[v];
                path_flow = Math.min(path_flow, residualGraph[u][v]);
            }
 
            // update residual capacities of the edges and
            // reverse edges along the path
            for (v=t; v != s; v=parent[v])
            {
                u = parent[v];
                residualGraph[u][v] -= path_flow;
                residualGraph[v][u] += path_flow;
            }
 
            // Add path flow to overall flow
            max_flow += path_flow;
        }
 
        // Return the overall flow
        return max_flow;
    }

    // Driver program to test above functions
    public static void main (String[] args) throws java.lang.Exception
    {
    	System.out.println("Enter the number of nodes to calculate the Max Flow through it!\n");
    	
    	Scanner s = new Scanner(System.in);
    	V = s.nextInt();
    	int graph[][] = new int[V][V];
    	//Create a single node then populate the matrix with nodes and their weights
    	graph[0][1] = 1;
		int w= 0;

			//Inserting Random Numbers in matrix       
			for(int i=1;i<4;i++)
			{
				Random rand = new Random();
				int a = rand.nextInt(i)+1;
				w = rand.nextInt(5)+1;	
				graph[i+1][a] = w;
			}
			
			System.out.println( " ");
			
			for(int i = 0; i<V; i++)
			{
				for(int j = 0; j<V; j++)
				{
					
					if(i!=j && i<j)
					{
						//Random Number	Generator
						Random rand = new Random();   				
						graph[i][j] =rand.nextInt(10);
					}
				}
			}
			
        FordFulkerson m = new FordFulkerson();
        
        Random rand = new Random();
        int a;int b = rand.nextInt(V);
        if(b==0)
        { 
        	a=0; b=V-1;
        }        
        else
        {
        	a = rand.nextInt(b)+1;
        }
        
        if(b==a)        	
        {
        	b=rand.nextInt(V);
        }
        else if (b<a)
        {
        	  a = a + b; 
        	  b = a - b; 
        	  a = a - b;
        }
        
        System.out.println("The maximum possible flow through the graph is " +
                           m.fordFulkerson(graph, a, b));
        
        System.out.println("\nThe Adjacency Matrix for the Random Graph is\n");
        for (int i = 0; i < V; i++) 
        {
    	    for (int j = 0; j < V; j++) 
    	    {
    	        System.out.print(graph[i][j] + " ");
    	    }
    	    System.out.println();    	    
    	} 
    }
}