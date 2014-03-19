
public class CreateAuxiliaryGraph {
	private static Iterable<DirectedEdge> cycle;  // negative cycle (or null if no such cycle)

	public static void main (String[] args) {
		
		//read in number of verteces and edges and create new graph
		int V = StdIn.readInt();
		int E = StdIn.readInt();
		
		//create new graph
		EdgeWeightedDigraph g = new EdgeWeightedDigraph(V);
		
		//read in the edges of the graph from the pipe
		while (!StdIn.isEmpty()) {
			for (int i = 0; i < E; i++) {
				int v = StdIn.readInt();
				int w = StdIn.readInt();
				double weight = StdIn.readDouble();
				if (v < 0 || v >= V) throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
	            if (w < 0 || w >= V) throw new IndexOutOfBoundsException("vertex " + w + " is not between 0 and " + (V-1));
				
				g.addEdge(new DirectedEdge(v, w, weight));
				
			}
		}
		
		//make sure there are no negative cycles
		 EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(g);
	     cycle = finder.cycle();             //find cycle
	     
	     double cycleWeight = 0.0;
	     for (DirectedEdge e : cycle) {
	    	 cycleWeight += e.weight();   //find weight of cycle
	     }
	     
	     if (cycleWeight < 0) {                   //This finds if the cycle is negative
	    	 StdOut.println(-2);   //create error code for negative cycle
	     } else {
	    	 StdOut.println(2);
	     }
		
	     //create auxiliary graph and add edges
	     EdgeWeightedDigraph gPrime = new EdgeWeightedDigraph(V+1);
	     for (DirectedEdge e : g.edges()) {
	    	 gPrime.addEdge(e);
	     }
	     
	     //add edge from new vertex V to each other vertex
	     for (int k = 0; k<V; k++) {
	    	 gPrime.addEdge(new DirectedEdge(V, k, 0.0));
	     }
	     
	     //Output graph in terms of number vertices, number edges, and each edge from, to, and weight
	     //This will allow it to be read in correctly by the ApplyBellman method next in the pipe
	     //StdOut.println(gPrime);
	     StdOut.println(gPrime.V());
	     StdOut.println(gPrime.E());
	     for (DirectedEdge e : gPrime.edges()) {
	    	 StdOut.println(e.from());
	    	 StdOut.println(e.to());
	    	 StdOut.println(e.weight());
	     }
	     
	}
}
