
public class ApplyDijkstraAllPairs {
	public static void main (String[] args) {
		
		//make sure there is no negative cycle
		int negCycleError = StdIn.readInt();
		if (negCycleError == -2) {
			StdOut.println("Graph has a negative cycle.");
			System.exit(0);
		}
		
		//make sure there was no error from ApplyBellmanFord
		int error = StdIn.readInt();
		if (error == -1) {
			StdOut.println("Please do not include any arguments with ApplyBellmanFord. The program takes care of input to BellmanFordSP");
			System.exit(0);
		}
		
		if (args.length != 0) {
			StdOut.println("Please do not provide an argument for Dijkstra All Pairs");
			System.exit(0);
		}
		//read in vertices and edges from CreateAuxiliaryGraph in pipe
		int V = StdIn.readInt();
		int E = StdIn.readInt();
				
		//generate new graph gDoublePrime that is the same as gPrime created in previous step in pipe
		EdgeWeightedDigraph gDoublePrime = new EdgeWeightedDigraph(V);
				
		//read in all edges from pipe and add them to the graph
		for (int i = 0; i < E; i++) {
			int v = StdIn.readInt();
			int w = StdIn.readInt();
			double weight = StdIn.readDouble();
			//StdOut.println("v = " + v + " w = " + w + " weight = " + weight);
			gDoublePrime.addEdge(new DirectedEdge(v, w, weight));
					
		}
				
		//StdOut.println(gDoublePrime);
		
		//read in vertex weights
		Double[] vertexWeights = new Double[V];
		for (int i = 0; i < gDoublePrime.V(); i++) {
			vertexWeights[i] = StdIn.readDouble();
			//StdOut.println("Vertex " + i + ": " + vertexWeights[i]);
		}
		
		
		
		//Apply Dijkstra's Shortest Path to G''
		DijkstraAllPairsSP sp = new DijkstraAllPairsSP(gDoublePrime);
		
		//Recreate original graph G by reverse calibrating edges
		EdgeWeightedDigraph originalG = new EdgeWeightedDigraph(V);
		for (DirectedEdge e : gDoublePrime.edges()) {
			double oldWeight = e.weight() + vertexWeights[e.to()] - vertexWeights[e.from()];
			originalG.addEdge(new DirectedEdge(e.from(), e.to(), oldWeight));
		}
		
		//StdOut.println(originalG);
		
		//print shortest paths
		/*
		for (int t = 0; t < gDoublePrime.V(); t++) {
			for (int u = 0; u < gDoublePrime.V(); u++) {
	            if (sp.hasPath(t, u)) {
	                StdOut.printf("%d to %d (%.2f)  ", t, u, sp.dist(t, u));
	                if (sp.hasPath(t, u)) {
	                    for (DirectedEdge e : sp.path(t, u)) {
	                        StdOut.print(e + "   ");
	                    }
	                }
	                StdOut.println();
	            }
	            else {
	                StdOut.printf("%d to %d         no path\n", t, u);
	            }
			}	        
        }
		*/
		
		//print shortest paths --> reverse calibrated 
		for (int t = 0; t < gDoublePrime.V(); t++) {
			for (int u = 0; u < gDoublePrime.V(); u++) {
	            if (sp.hasPath(t, u)) {
	            	double distTo = 0.0;                     //initialize distTo variable which keeps track of actual distance in G
	            	if (sp.hasPath(t, u)) {
	                    for (DirectedEdge e : sp.path(t, u)) {
	                    	for (DirectedEdge ee : originalG.edges()) {
	                    		if (e.to() == ee.to() && e.from() == ee.from()) {
	                    			distTo = distTo + ee.weight();                  //calculate distTo from original G weights
	                    		}	
	                    	}
	                    }
	                }
	                StdOut.printf("%d to %d (%.2f)  ", t, u, distTo);             //print distance of path (distTo)
	                if (sp.hasPath(t, u)) {
	                    for (DirectedEdge e : sp.path(t, u)) {
	                    	for (DirectedEdge ee : originalG.edges()) {
	                    		if (e.to() == ee.to() && e.from() == ee.from()) {
	                    			StdOut.print(ee + "   ");                         //print original G edge that corresponds to shortest path
	                    		}	
	                    	}
	                    }
	                }
	                StdOut.println();
	            }
	            else {
	                StdOut.printf("%d to %d         no path\n", t, u);
	            }
			}
        }
		
		//Print running time and space requirement
		StdOut.println("\nNovacky All Paths Running time: E*V + E*V*logV = E*V(1 + logV)--> reduces to E*V*logV");
	}
}
