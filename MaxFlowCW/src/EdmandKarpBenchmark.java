/* *****************************************************************************
 *  Name:    Mohomed Rashad
 *  UOW ID: 1930176   
 *  IIT ID: 2018470
 *
 *  Description: This represents a class contains all the metrics used to display
 *  the benchmark of Edmand Karp Algorithm.
 *
 *  Written:       27/03/2021
 *  Last updated:  29/03/2021
 *
 **************************************************************************** */

public class EdmandKarpBenchmark {
	String inputFile;
	int vertices;
	int edges;
	long maxFlow;
	double avgDuration;

	public EdmandKarpBenchmark(String inputFile, int vertices, int edges, long maxFlow, double avgDuration) {
		this.inputFile = inputFile;
		this.vertices = vertices;
		this.edges = edges;
		this.maxFlow = maxFlow;
		this.avgDuration = avgDuration;
	}
}
