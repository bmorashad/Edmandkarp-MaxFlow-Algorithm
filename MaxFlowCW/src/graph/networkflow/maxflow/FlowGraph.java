/* *****************************************************************************
 *  Name:    Mohomed Rashad
 *  UOW ID: 1930176   
 *  IIT ID: 2018470
 *
 *  Description: A Class to represent Graph representation, specialized to work 
 *  efficiently with maxflow solving algorithms.
 *
 *  Written:       27/03/2021
 *  Last updated:  29/03/2021
 *
 **************************************************************************** */

package graph.networkflow.maxflow;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FlowGraph {
	protected int n;
	protected int s;
	protected int t;
	protected int edgeCount;
	protected Node[] nodes;

	public FlowGraph(int n, int s, int t) {
		this.n = n;
		this.s = s;
		this.t = t;
		this.edgeCount = 0;
		initNodes(n);
	}

	private void initNodes(int n) {
		nodes = new Node[n];
		for (int i = 0; i < n; i++) {
			nodes[i] = new Node(i, n);
		}
	}

	public int size() {
		return n;
	}

	public int getSource() {
		return s;
	}

	public int getSink() {
		return t;
	}

	public void addAdjNode(int u, int a, int cap) {
		nodes[u].addAdjNode(a, cap);
		nodes[a].addAdjNode(u, 0);
		edgeCount++;
	}

	public int edgeCount() {
		return edgeCount;
	}

	public ArrayList<Integer> getAdjList(int u) {
		return nodes[u].adjNodes;
	}

	public void addFlow(int u, int a, long flow) {
		nodes[u].addFlow(a, flow);
		nodes[a].addFlow(u, -flow);
	}

	public long getResidualCapacity(int u, int to) {
		return nodes[u].getResidualCapacity(to);
	}

	public long getCapacity(int u, int to) {
		return nodes[u].getCapacity(to);
	}

	public long getFlow(int u, int to) {
		return nodes[u].getFlow(to);
	}

	public static FlowGraph parse(String graphInputFile) {
		FlowGraph graph = null;
		File file = new File(graphInputFile);
		if (file.exists()) {
			try (Scanner sc = new Scanner(file)) {
				int n = Integer.parseInt(sc.nextLine().trim());
				int t = n - 1;
				int s = 0;
				graph = new FlowGraph(n, s, t);
				while (sc.hasNext()) {
					String line = sc.nextLine().trim();
					String[] edgesLine = line.split(" ");
					int[] edges = Arrays.asList(edgesLine).stream().mapToInt(Integer::parseInt).toArray();
					graph.addAdjNode(edges[0], edges[1], edges[2]);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return graph;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < nodes.length; i++) {
			sb.append(nodes[i].toString());
		}
		return sb.toString();
	}

}
