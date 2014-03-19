
public class ApplyBellmanFord {

	public static void main (String[] args) {
		
		//make sure there were no negative cycles
		int error = StdIn.readInt();
		if (error == -2) {
			StdOut.println(-2);
		} else {
			StdOut.println(2);
		}
		
		//check to make sure there are no arguments given for this program
		if (args.length != 0) {
			StdOut.println(-1);            //create error -1 for arguments to ApplyBellmanFord
		} else {
			StdOut.println(1);
		}
		
		//read in vertices and edges from CreateAuxiliaryGraph in pipe
		int V = StdIn.readInt();
		int E = StdIn.readInt();
		
		//generate new graph gPrime that is the same as gPrime created in previous step in pipe
		EdgeWeightedDigraph gPrime = new EdgeWeightedDigraph(V);
		
		//read in all edges from pipe and add them to the graph
		for (int i = 0; i < E; i++) {
			int v = StdIn.readInt();
			int w = StdIn.readInt();
			double weight = StdIn.readDouble();
			//StdOut.println("v = " + v + " w = " + w + " weight = " + weight);
			gPrime.addEdge(new DirectedEdge(v, w, weight));
			
		}
		
		//s is the source vertex
		int s = V-1;
		
		//compute bellman ford shortest paths of gPrime from s
		BellmanFordSP sp = new BellmanFordSP(gPrime, s);
		
		/*
		//print out shortest paths
		for (int v = 0; v < gPrime.V(); v++) {
            if (sp.hasPathTo(v)) {
                StdOut.printf("%d to %d (%5.2f)  ", s, v, sp.distTo(v));
                for (DirectedEdge e : sp.pathTo(v)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d           no path\n", s, v);
            }
        }
		*/
		
		//Compute vertex weights from shortest paths
		double[] vertexWeights = new double[V];
		for (int v = 0; v < gPrime.V(); v++) {
			if (sp.hasPathTo(v)) {
				vertexWeights[v] = sp.distTo(v);
				//StdOut.println("Weight of " + v + " is " + vertexWeights[v]);
			}
		}
		
		//recalibrate edge weights by using vertex weights so that edgeweights are non-negative
		//create G''
		EdgeWeightedDigraph gDoublePrime = new EdgeWeightedDigraph(V-1);
		for (DirectedEdge e : gPrime.edges()) {
			if (e.from() != s) {              //make sure to not include the added vertex
				double newWeight = e.weight() + vertexWeights[e.from()] - vertexWeights[e.to()];  //calculate the new edgeweight
				gDoublePrime.addEdge(new DirectedEdge(e.from(), e.to(), newWeight));
			}
		}
		
		
		//Output graph in terms of number vertices, number edges, and each edge from, to, and weight
	    //This will allow it to be read in correctly by the ApplyBellman method next in the pipe
		//StdOut.println(gDoublePrime);
	    StdOut.println(gDoublePrime.V());
	    StdOut.println(gDoublePrime.E());
	    for (DirectedEdge e : gDoublePrime.edges()) {
	    	StdOut.println(e.from());
	    	StdOut.println(e.to());
	    	StdOut.println(e.weight());
	    }
	    
	    //output vertex weights for reverse calibrating
	    for (int m = 0; m < vertexWeights.length; m++) {
	    	StdOut.println(vertexWeights[m]);
	    }
		
	}
}
