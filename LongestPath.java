// Longest Path
// You will be given a graph from the console, your task is to find the longest path to the D destination vertex and then print the weight of that path.
// Input
// •	The input comes from the console. 
// First is an integer the number of nodes, 
// then the number of edges, after that, each edge on a new line in the following format "{source} {destination} {weight}". 
// Then you will read two integers on a separate line, the source, and destination nodes.
// Output
// •	Print on a single line the weight of that path.
// input:
// 6
// 11
// 1 2 5
// 1 3 3
// 2 4 6
// 2 3 2
// 3 5 4
// 3 6 2
// 3 4 7
// 4 6 1
// 4 5 3
// 5 6 4
// 6 2 1
// 1
// 6
// output: 21
// input2:
// 4
// 4
// 1 2 5
// 1 3 3
// 3 4 6
// 4 2 4
// 1
// 2
// output2: 13

import java.io.*;
import java.util.*;

public class LongestPath {
    public static class Edge {
        int destination;
        int weight;

        public Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    public static void topologicalSort(int v, boolean visited[], Stack<Integer> stack, List<List<Edge>> graph) {
        visited[v] = true;

        for (Edge edge : graph.get(v)) {
            if (!visited[edge.destination]) {
                topologicalSort(edge.destination, visited, stack, graph);
            }
        }

        stack.push(v);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int nodes = Integer.parseInt(br.readLine().trim());
        int edges = Integer.parseInt(br.readLine().trim());

        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i <= nodes; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges; i++) {
            String[] line = br.readLine().split(" ");
            int source = Integer.parseInt(line[0]);
            int destination = Integer.parseInt(line[1]);
            int weight = Integer.parseInt(line[2]);

            graph.get(source).add(new Edge(destination, weight));
        }

        int source = Integer.parseInt(br.readLine().trim());
        int destination = Integer.parseInt(br.readLine().trim());

        Stack<Integer> stack = new Stack<Integer>();
        boolean[] visited = new boolean[nodes + 1];
        for (int i = 1; i <= nodes; i++) {
            if (!visited[i]) {
                topologicalSort(i, visited, stack, graph);
            }
        }

        int[] distances = new int[nodes + 1];
        Arrays.fill(distances, Integer.MIN_VALUE);
        distances[source] = 0;

        while (!stack.isEmpty()) {
            int u = stack.pop();

            if (distances[u] != Integer.MIN_VALUE) {
                for (Edge edge : graph.get(u)) {
                    if (distances[edge.destination] < distances[u] + edge.weight) {
                        distances[edge.destination] = distances[u] + edge.weight;
                    }
                }
            }
        }

        bw.write(String.valueOf(distances[destination]));
        bw.newLine();

        bw.flush();
        bw.close();
    }
}
