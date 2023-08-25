// Most Reliable Path
// We have a set of towns, and some of them are connected by direct paths. Each
// path has a coefficient of reliability (in percentage):
// the chance to pass without incidents. Your goal is to compute the most
// reliable path between two given nodes.
// Assume all percentages will be integer numbers and round the result to the
// second digit after the decimal separator.
// For example, let's consider the graph below:
// The most reliable path between 0 and 6 is shown on the right: 0 → 4 → 5 → 3 →
// 1 → 6. Its cost = 88% * 99% * 98% * 95% * 100% = 81.11%.
// The table below shows the optimal reliability coefficients for all paths
// starting from node 0:
// v 0 1 2 3 4 5 6
// reliability[s → d] 100% 81.11% 77.05% 85.38% 88% 87.12% 81.11%
// input
// Nodes: 7
// Path: 0 – 6
// Edges: 10
// 0 3 85
// 0 4 88
// 3 1 95
// 3 5 98
// 4 5 99
// 4 2 14
// 5 1 5
// 5 6 90
// 1 6 100
// 2 6 95
// output
// Most reliable path reliability: 81.11%
// 0 -> 4 -> 5 -> 3 -> 1 -> 6
// input2
// Nodes: 4
// Path 0 – 1
// Edges: 4
// 0 1 94
// 0 2 97
// 2 3 99
// 1 3 98
// output2
// Most reliable path reliability: 94.11%
// 0 -> 2 -> 3 -> 1


import java.util.*;

public class MostReliablePath {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Nodes: 4
        // Path 0 – 1
        // Edges: 4
        int nodes = Integer.parseInt(scanner.nextLine().split(": ")[1]);
        String[] path = scanner.nextLine().split(" ");
        int start = Integer.parseInt(path[1]);
        int end = Integer.parseInt(path[3]);
        int edges = Integer.parseInt(scanner.nextLine().split(": ")[1]);

        int[][] graph = new int[nodes][nodes];
        for (int i = 0; i < edges; i++) {
            String[] edge = scanner.nextLine().split(" ");
            int from = Integer.parseInt(edge[0]);
            int to = Integer.parseInt(edge[1]);
            int reliability = Integer.parseInt(edge[2]);
            graph[from][to] = reliability;
            graph[to][from] = reliability;
        }
        double[] reliability = new double[nodes];
        int[] prev = new int[nodes];
        boolean[] visited = new boolean[nodes];
        Arrays.fill(reliability, 0);
        Arrays.fill(prev, -1);
        Arrays.fill(visited, false);
        reliability[start] = 100;
        for (int i = 0; i < nodes; i++) {
            int maxReliability = -1;
            int maxNode = -1;
            for (int j = 0; j < nodes; j++) {
                if (!visited[j] && reliability[j] > maxReliability) {
                    maxReliability = (int) reliability[j];
                    maxNode = j;
                }
            }
            if (maxNode == -1) {
                break;
            }
            visited[maxNode] = true;
            for (int j = 0; j < nodes; j++) {
                if (graph[maxNode][j] > 0) {
                    double newReliability = reliability[maxNode] * graph[maxNode][j] / 100;
                    if (newReliability > reliability[j]) {
                        reliability[j] = newReliability;
                        prev[j] = maxNode;
                    }
                }
            }
        }
        System.out.printf("Most reliable path reliability: %.2f%%%n", reliability[end]);
        List<Integer> pathList = new ArrayList<>();
        int node = end;
        while (node != -1) {
            pathList.add(node);
            node = prev[node];
        }
        Collections.reverse(pathList);
        System.out.println(String.join(" -> ", pathList.stream().map(String::valueOf).toArray(String[]::new)));
    }
}
