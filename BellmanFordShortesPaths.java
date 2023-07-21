// 1.	Bellman-Ford Shortes paths
// You will be given a graph from the console, your task is to find the shortest path and print it as a sequence from S source vertex to D destination vertex, 
// and then on the second line, the weight of that path, or if there is no such, in case of negative cycle print a message "Negative Cycle Detected".
// Input
// •	The input comes from the console. First is an integer, the number of nodes, then the number of edges, after that, 
// each edge on a new line in the following format "{source} {destination} {weight}". Then you will read two integers on a separate line,
// the source, and destination nodes.
// Output
// •	Print on a single line the path found separated by spaces and on the second line the weight of that path, or if there is no path,
// message "Negative Cycle Detected".

// Example input:
// 6
// 8
// 1 2 8
// 1 3 10
// 2 4 1
// 3 6 2
// 4 3 -4
// 4 6 -1
// 6 5 -2
// 5 3 1
// 1
// 6
// Example output:
// 1 2 4 3 6
// 7
// Example input1:
// 4
// 4
// 1 2 1
// 2 3 -1
// 3 4 -1
// 4 1 -1
// 1
// 4
// Example output1:
// Negative Cycle Detected

// read the input - use bufferedReader
// create a graph
// create a distance array
// create a prev array
// create a queue
// create a visited array
// create a negative cycle boolean
// create a source and destination
// run bellman ford algorithm - with Handling for Negative Cycles
// print the path - use bufferWriter
// print the distance - use bufferWriter


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BellmanFordShortestPaths {

    private static class Edge {
        private int source;
        private int destination;
        private int weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Read the number of nodes and edges.
        int nodes = Integer.parseInt(reader.readLine());
        int edges = Integer.parseInt(reader.readLine());

        // Create a list of edges
        List<Edge> graph = new ArrayList<>();

        for (int i = 0; i < edges; i++) {
            String[] inputs = reader.readLine().split(" ");
            int source = Integer.parseInt(inputs[0]);
            int destination = Integer.parseInt(inputs[1]);
            int weight = Integer.parseInt(inputs[2]);
            graph.add(new Edge(source, destination, weight));
        }

        // Read the source and destination nodes.
        int source = Integer.parseInt(reader.readLine());
        int destination = Integer.parseInt(reader.readLine());

        // Initialize the distance and prev arrays.
        int[] distances = new int[nodes+1];
        int[] prev = new int[nodes+1];
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);

        // Set the distance of the source node to 0.
        distances[source] = 0;

        // Perform the relaxation step V-1 times.
        for (int i = 0; i < nodes - 1; i++) {
            for (Edge edge : graph) {
                if (distances[edge.source] != Integer.MAX_VALUE && distances[edge.source] + edge.weight < distances[edge.destination]) {
                    distances[edge.destination] = distances[edge.source] + edge.weight;
                    prev[edge.destination] = edge.source;
                }
            }
        }

        // Check for a negative cycle
        boolean negativeCycle = false;
        for (Edge edge : graph) {
            if (distances[edge.source] != Integer.MAX_VALUE && distances[edge.source] + edge.weight < distances[edge.destination]) {
                negativeCycle = true;
                break;
            }
        }

        // Print the result.
        if (negativeCycle) {
            System.out.println("Negative Cycle Detected");
        } else if (distances[destination] == Integer.MAX_VALUE) {
            System.out.println("No path found from source to destination");
        } else {
            Deque<Integer> path = new ArrayDeque<>();
            for (int v = destination; v != -1; v = prev[v]) {
                path.push(v);
            }
            // Print the path without empty spaces.
            while (!path.isEmpty()) {
                int node = path.pop();
                System.out.print(node);
                if (!path.isEmpty()) {
                    System.out.print(" ");
                }
            }

            System.out.println();
            System.out.println(distances[destination]);
        }
    }
}
