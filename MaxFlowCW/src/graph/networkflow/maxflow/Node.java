/* *****************************************************************************
 *  Name:    Mohomed Rashad
 *  UOW ID: 1930176   
 *  IIT ID: 2018470
 *
 *  Description: Node class represents a Vertex. It contains the adjNodes the
 *  vertext contains and the capacity details. This utilizes a adjecancy list 
 *  to store adjNodes(edges) and capacity details
 *
 *  Written:       27/03/2021
 *  Last updated:  29/03/2021
 *
 **************************************************************************** */

package graph.networkflow.maxflow;

import java.util.ArrayList;
import java.util.HashMap;

public class Node {

	protected int from;
	protected ArrayList<Integer> adjNodes;
	protected HashMap<Integer, long[]> adjNodeState;

	public Node(int from) {
		this.from = from;
		this.adjNodes = new ArrayList<>();
		this.adjNodeState = new HashMap<>();
	}

	public int edgeCount() {
		return adjNodes.size();
	}

	public long getCapacity(int to) {
		return adjNodeState.get(to)[0];
	}

	public long getFlow(int to) {
		return adjNodeState.get(to)[1];
	}

	public long getResidualCapacity(int to) {
		long cap = getCapacity(to);
		long flow = getFlow(to);
		return cap - flow;
	}

	public void addFlow(int to, long flow) {
		adjNodeState.get(to)[1] += flow;
	}

	public void addAdjNode(int a, long cap) {
		adjNodes.add(a);
		long[] adjNodeState = new long[] { cap, 0 };
		this.adjNodeState.put(a, adjNodeState);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int adjNode : adjNodes) {
			sb.append(from + "  ");
			sb.append(getFlow(adjNode) + "/" + getCapacity(adjNode));
			sb.append("---->" + getResidualCapacity(adjNode) + "  ");
			sb.append(adjNode + "\n");
		}
		return sb.toString();
	}
}
