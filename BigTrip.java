// Big Trip
// You will be given a trip description from the console. 
// Your task is to find the longest trip possible and print how much time it will cost. After that, print the path itself on a single line separated by spaces.
// Input
// •	The input comes from the console. First is an integer, the number of nodes, then the number of edges. 
// After that each edge on a new line in the following format "{source} {destination} {weight}". 
// Then you will read two integers on a separate line, the source, and destination nodes.
// Output
// •	Print on the first line the weight of that path.
// •	On the second line the path.
// Use BufferedReader, InputStreamReader, and System.out.println() to write the output.
// find the longest path in a weighted graph
// Input1
// 6
// 10
// 1 2 3
// 1 5 5
// 2 4 4
// 2 3 7
// 2 6 2
// 3 4 -1
// 3 6 2
// 5 3 6
// 5 2 2
// 4 6 -2
// 1
// 3
// Output1
// 14
// 1 5 2 3
// Input2
// 6
// 10
// 1 2 3
// 1 5 5
// 2 4 4
// 2 3 -7
// 2 6 2
// 3 4 -1
// 3 6 2
// 5 3 6
// 5 2 2
// 4 6 -2
// 1
// 3
// Output2
// 11
// 1 5 3

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BigTrip {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int numNodes = Integer.parseInt(reader.readLine());
        int numEdges = Integer.parseInt(reader.readLine());

        int[][] edges = new int[numEdges][3];
        int[] distance = new int[numNodes + 1];
        int[] parent = new int[numNodes + 1];

        // Initialize distance array
        Arrays.fill(distance, Integer.MIN_VALUE);

        for (int i = 0; i < numEdges; i++) {
            String[] edgeArgs = reader.readLine().split(" ");
            int source = Integer.parseInt(edgeArgs[0]);
            int destination = Integer.parseInt(edgeArgs[1]);
            int weight = Integer.parseInt(edgeArgs[2]);

            edges[i] = new int[]{source, destination, weight};
        }

        int source = Integer.parseInt(reader.readLine());
        int destination = Integer.parseInt(reader.readLine());

        reader.close();

        distance[source] = 0;
        parent[source] = -1;

        for (int i = 1; i <= numNodes; i++) {
            for (int[] edge : edges) {
                int u = edge[0];
                int v = edge[1];
                int w = edge[2];

                if (distance[u] != Integer.MIN_VALUE && distance[u] + w > distance[v]) {
                    distance[v] = distance[u] + w;
                    parent[v] = u;
                }
            }
        }

        if (distance[destination] == Integer.MIN_VALUE) {
            System.out.println("No path exists");
        } else {
            System.out.println(distance[destination]);
            printPath(parent, destination);
        }
    }

    public static void printPath(int[] parent, int destination) {
        if (parent[destination] == -1) {
            System.out.print(destination + " ");
            return;
        }
        printPath(parent, parent[destination]);
        System.out.print(destination + " ");
    }
}
