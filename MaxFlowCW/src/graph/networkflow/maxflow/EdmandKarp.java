/* *****************************************************************************
 *  Name:    Mohomed Rashad
 *  UOW ID: 1930176   
 *  IIT ID: 2018470
 *
 *  Description: An implementation of Edmand Karp algorithm to find
 *  max flow.
 *
 *  Written:       27/03/2021
 *  Last updated:  29/03/2021
 *
 **************************************************************************** */

package graph.networkflow.maxflow;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

public class EdmandKarp {
	FlowGraph graph;
	long maxFlow;

	public EdmandKarp(FlowGraph graph) {
		this.graph = graph;
		this.maxFlow = -1;
	}

	public long getMaxFlow() {
		return getMaxFlow(false);
	}

	public long getMaxFlow(boolean printAugPath) {
		if (maxFlow != -1) {
			return maxFlow;
		}
		maxFlow = 0;
		long minResidualCap;
		int j = 1;
		if (printAugPath) {
			System.out.println("C - Capacity, F - Current Flow, R - Residual Capacity");
			System.out.println("Edge - <from> F/C---->R <to>\n");
		}
		do {
			int[] paths = bfs();
			int t = graph.getSink();
			if (paths[t] == -1)
				return maxFlow;
			minResidualCap = Long.MAX_VALUE;
			int i = t;
			while (paths[i] != -1) {
				int parent = paths[i];
				minResidualCap = Math.min(minResidualCap, graph.getResidualCapacity(parent, i));
				i = parent;
			}
			if (printAugPath) {
				System.out.println("Augmenting Path - " + j + " | Min Residual Capacity - " + minResidualCap);
			}
			i = t;
			while (paths[i] != -1) {
				int parent = paths[i];
				graph.addFlow(parent, i, minResidualCap);
				long residualCap = graph.getResidualCapacity(parent, i);
				long capacity = graph.getCapacity(parent, i);
				long currentFlow = graph.getFlow(parent, i);
				if (printAugPath) {
					String augPathInfo = getAugPathInfoString(parent, i, capacity, currentFlow, residualCap);
					System.out.println(augPathInfo);
				}
				i = parent;
			}
			if (printAugPath) {
				System.out.println("");
			}
			maxFlow += minResidualCap;
			j += 1;
		} while (minResidualCap != 0);
		return maxFlow;
	}

	private static String getAugPathInfoString(int u, int v, long cap, long flow, long residualCap) {
		StringBuilder sb = new StringBuilder();
		sb.append(u + "  ");
		sb.append(flow + "/" + cap);
		sb.append("---->" + residualCap + "  ");
		sb.append(v);
		return sb.toString();
	}

	public int[] bfs() {
		int n = graph.size();
		int s = graph.getSource();
		int t = graph.getSink();
		Queue<Integer> bfsQ = new ArrayDeque<>(n);
		int[] paths = new int[n];
		Arrays.fill(paths, -1);
		boolean[] isVisited = new boolean[n];
		Arrays.fill(isVisited, false);
		bfsQ.offer(s);
		isVisited[s] = true;
		// paths[s] = -1;
		while (!bfsQ.isEmpty()) {
			int node = bfsQ.poll();
			if (node == t) {
				break;
			}
			ArrayList<Integer> adjNodes = graph.getAdjList(node);
			for (int edge : adjNodes) {
				if (!isVisited[edge] && graph.getResidualCapacity(node, edge) > 0) {
					isVisited[edge] = true;
					paths[edge] = node;
					bfsQ.offer(edge);
				}
			}
		}
		return paths;
	}
}
