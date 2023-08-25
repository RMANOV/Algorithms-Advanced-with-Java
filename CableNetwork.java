// Cable Network
// A cable networking company plans to extend its existing cable network by
// connecting as many customers as possible within a fixed budget limit.
// The company has calculated the cost of building some prospective connections.
// You are given the existing cable network (a set of customers and connections
// between them) along with the estimated connection costs between some pairs of
// customers and prospective customers.
// A customer can only be connected to the network via a direct connection with
// an already connected customer. Example:
// Note that each edge, at the time of its addition to the network, connects a
// new customer with an existing one.
// Hints - Modify Prims's algorithm. Until the budget is spent, connect the
// smallest possible edge from the connected node to the non-connected node.
// Example:
// Budget: 20
// Nodes: 9
// Edges: 15
// 1 4 8
// 4 0 6 connected
// 1 7 7
// 4 7 10
// 4 8 3
// 7 8 4
// 0 8 5 connected
// 8 6 9
// 8 3 20 connected
// 0 5 4
// 0 3 9 connected
// 6 3 8
// 6 2 12
// 5 3 2
// 3 2 14 connected
// Output: Budget used: 13
// Example2:
// Budget: 7
// Nodes: 4
// Edges: 5
// 0 1 9
// 0 3 4 connected
// 3 1 6
// 3 2 11 connected
// 1 2 5
// Output: Budget used: 5
// Example3:
// Budget: 20
// Nodes: 8
// Edges: 16
// 0 1 4
// 0 2 5
// 0 3 1 connected
// 1 2 8
// 1 3 2
// 2 3 3
// 2 4 16
// 2 5 9
// 3 4 7
// 3 5 14
// 4 5 12
// 4 6 22
// 4 7 9
// 5 6 6
// 5 7 18
// 6 7 15
// Output: Budget used: 12

// Read the Input:
// First lines contain text and should be parsed to extract Budget, Nodes, and
// Edges.
// Read the connections and check for the 'connected' keyword to recognize
// pre-existing connections.
// Initialize Data Structures:
// Create an adjacency matrix to store the edges between nodes, and other data
// structures like parent array, keys array, and MST set to apply a modified
// Prim's algorithm.
// Initialize Existing Connections:
// If there is a connection marked 'connected', we must set the cost to 0 and
// initialize the starting point of the algorithm to be one of the nodes that is
// already connected.
// Modified Prim's Algorithm:
// Run a loop to add edges to the Minimum Spanning Tree. Use a priority queue or
// minKey function to find the node with the smallest key value.
// Check if adding the new edge will exceed the budget, and if so, stop the
// algorithm.
// Update the key values of adjacent nodes.
// Calculate and Output the Budget Used:
// Add up the values of the selected edges and print the result.

import java.util.*;

public class CableNetwork {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int budget = Integer.parseInt(sc.nextLine().split(": ")[1]);
        int nodes = Integer.parseInt(sc.nextLine().split(": ")[1]);
        int edges = Integer.parseInt(sc.nextLine().split(": ")[1]);
        
        int[] parent = new int[nodes];
        for (int i = 0; i < nodes; i++) {
            parent[i] = i;
        }

        List<Edge> remainingEdges = new ArrayList<>();

        for (int i = 0; i < edges; i++) {
            String line = sc.nextLine();
            String[] arr = line.split(" ");
            int u = Integer.parseInt(arr[0]);
            int v = Integer.parseInt(arr[1]);
            int w = Integer.parseInt(arr[2]);
            if (line.contains("connected")) {
                union(parent, u, v);
            } else {
                remainingEdges.add(new Edge(u, v, w));
            }
        }

        Collections.sort(remainingEdges);

        int budgetUsed = 0;
        for (Edge e : remainingEdges) {
            if (find(parent, e.u) != find(parent, e.v)) {
                if (budgetUsed + e.w <= budget) {
                    budgetUsed += e.w;
                    union(parent, e.u, e.v);
                } else {
                    break;
                }
            }
        }

        System.out.println("Budget used: " + budgetUsed);
    }

    static int find(int[] parent, int x) {
        if (parent[x] != x) {
            parent[x] = find(parent, parent[x]);
        }
        return parent[x];
    }

    static void union(int[] parent, int x, int y) {
        int rootX = find(parent, x);
        int rootY = find(parent, y);
        parent[rootX] = rootY;
    }

    static class Edge implements Comparable<Edge> {
        int u, v, w;

        Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }

        public int compareTo(Edge other) {
            return this.w - other.w;
        }
    }
}
