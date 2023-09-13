// Undefined
// You will be given a graph from the console.
// Your task is to find the shortest path and print it as a sequence from S
// source vertex to D destination vertex and then on the second line,
// the weight of that path, be careful there might be negative cycles if there
// are printed "Undefined".
// Input
// • The input comes from the console. First is an integer, the number of nodes,
// then the number of edges.
// After that each edge on a new line in the following format "{source}
// {destination} {weight}".
// Then you will read two integers on a separate line, the source, and
// destination nodes.
// Output
// Print on a single line the path found separated by spaces and on the second
// line the weight of that path, or if there is no path, message "Negative Cycle
// Detected".
// Input1
// 5
// 8
// 1 2 -1
// 1 3 4
// 2 3 3
// 2 4 2
// 2 5 2
// 4 2 1
// 4 3 5
// 5 4 -3
// 1
// 4
// Output1
// 1 2 5 4
// -2
// Input2
// 5
// 8
// 1 2 -1
// 1 3 4
// 2 3 3
// 2 4 2
// 2 5 2
// 4 2 -1
// 4 3 5
// 5 4 -3
// 1
// 4
// Output2
// Undefined

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Undefined {

    public static class Edge {
        int from, to, weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer;

        int nodes = Integer.parseInt(reader.readLine());
        int edges = Integer.parseInt(reader.readLine());

        List<Edge> graph = new ArrayList<>();
        int[] dist = new int[nodes];
        int[] prev = new int[nodes];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);

        for (int i = 0; i < edges; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int from = Integer.parseInt(tokenizer.nextToken()) - 1;
            int to = Integer.parseInt(tokenizer.nextToken()) - 1;
            int weight = Integer.parseInt(tokenizer.nextToken());

            graph.add(new Edge(from, to, weight));
        }

        tokenizer = new StringTokenizer(reader.readLine());
        int source = Integer.parseInt(tokenizer.nextToken()) - 1;

        tokenizer = new StringTokenizer(reader.readLine());
        int destination = Integer.parseInt(tokenizer.nextToken()) - 1;

        dist[source] = 0;
        for (int i = 1; i <= nodes - 1; ++i) {
            for (Edge edge : graph) {
                if (dist[edge.from] != Integer.MAX_VALUE && dist[edge.from] + edge.weight < dist[edge.to]) {
                    dist[edge.to] = dist[edge.from] + edge.weight;
                    prev[edge.to] = edge.from;
                }
            }
        }

        for (Edge edge : graph) {
            if (dist[edge.from] != Integer.MAX_VALUE && dist[edge.from] + edge.weight < dist[edge.to]) {
                System.out.println("Undefined");
                return;
            }
        }

        if (dist[destination] == Integer.MAX_VALUE) {
            System.out.println("Undefined");
            return;
        }

        List<Integer> path = new ArrayList<>();
        for (int at = destination; at != -1; at = prev[at]) {
            path.add(at + 1);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = path.size() - 1; i >= 0; i--) {
            sb.append(path.get(i)).append(" ");
        }
        System.out.println(sb.toString().trim());
        System.out.println(dist[destination]);
    }
}
