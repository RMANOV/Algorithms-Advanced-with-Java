// Modified Kruskal Algorithm
// Implement Kruskal's algorithm by keeping the disjoint sets in a forest where each node holds a parent + children. 
// Thus, when two sets need to be merged, the result can be easily optimized to have two levels only: root and leaves. When two trees are merged, 
// all nodes from the second (its root + root's children) should be attached to the first tree's root node:
// Example1
// Nodes: 4
// Edges: 5
// 0 1 9
// 0 3 4
// 3 1 6
// 3 2 11
// 1 2 5
// Output: Minimum spanning forest weight: 15
// (0 3) -> 4
// (1 2) -> 5
// (1 3) -> 6
// Example2
// Nodes: 9
// Edges: 15
// 1 4 8
// 4 0 6
// 1 7 7
// 4 7 10
// 4 8 3
// 7 8 4
// 0 8 5
// 8 6 9
// 8 3 20
// 0 5 4
// 0 3 9
// 6 3 8
// 6 2 12
// 5 3 2
// 3 2 14
// Output: Minimum spanning forest weight: 45
// (3 5) -> 2
// (4 8) -> 3
// (0 5) -> 4
// (8 7) -> 4
// (0 8) -> 5
// (1 7) -> 7
// (3 6) -> 8
// (6 2) -> 12
// Example3
// Nodes: 8
// Edges: 16
// 0 1 4
// 0 2 5
// 0 3 1
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
// Output: Minimum spanning forest weight: 37
// (0 3) -> 1
// (1 3) -> 2
// (2 3) -> 3
// (5 6) -> 6
// (3 4) -> 7
// (2 5) -> 9
// (4 7) -> 9

import java.util.Arrays;
import java.util.Scanner;

public class Kruskal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String nodesLine = sc.nextLine(); // Read the "Nodes:" line
        int nodes = Integer.parseInt(nodesLine.split("\\s+")[1]);
        String edgesLine = sc.nextLine(); // Read the "Edges:" line
        int edges = Integer.parseInt(edgesLine.split("\\s+")[1]);
        int[][] graph = new int[edges][3];
        for (int i = 0; i < edges; i++) {
            graph[i][0] = sc.nextInt();
            graph[i][1] = sc.nextInt();
            graph[i][2] = sc.nextInt();
        }
        sc.close();
        int[][] result = kruskal(nodes, edges, graph);
        int weight = 0;
        for (int i = 0; i < result.length; i++) {
            weight += result[i][2];
        }
        System.out.println("Minimum spanning forest weight: " + weight);
        // for (int i = 0; i < result.length; i++) {
        // System.out.println("(" + result[i][0] + " " + result[i][1] + ") -> " +
        // result[i][2]);
        // }
    }

    public static int[][] kruskal(int nodes, int edges, int[][] graph) {
        int[][] result = new int[nodes - 1][3];
        int[] parent = new int[nodes];
        for (int i = 0; i < nodes; i++) {
            parent[i] = i;
        }
        Arrays.sort(graph, (a, b) -> a[2] - b[2]);
        int index = 0;
        for (int i = 0; i < edges; i++) {
            int[] edge = graph[i];
            int x = find(parent, edge[0]);
            int y = find(parent, edge[1]);
            if (x != y) {
                result[index++] = edge;
                union(parent, x, y);
            }
        }
        return result;
    }

    public static int find(int[] parent, int node) {
        if (parent[node] == node) {
            return node;
        }
        return find(parent, parent[node]);
    }

    public static void union(int[] parent, int x, int y) {
        int xParent = find(parent, x);
        int yParent = find(parent, y);
        parent[yParent] = xParent;
    }
}
