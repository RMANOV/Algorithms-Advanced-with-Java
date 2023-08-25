// Cheap Town Tour
// You now live in a new country, and you want to start a tour and visit every town in the country, 
// and since you are new in the country, your finances are minimalized, so you want your trip to be as cheap as possible. 
// You will be given the amount of the cities on the first line, then the amount of the roads (n), and on the next n lines, 
// you will receive which tows the road connects and the cost to use it. 
// Assume you can start from any town, and your target is to visit every one of them at the minimum cost.
// Input
// •	On the first line, you will be given the number of the towns
// •	On the second line, you will be given the amount of streets (n)
// •	On the next n lines you will be given a connection in the format: "{first} -> {second} -> {cost}"
// Output
// •	Print the total cost of the road you have chosen in the format: "Total cost: {totalCost}"
// Examples
// Input
// 4
// 5
// 0 - 1 - 10
// 0 - 2 - 6
// 0 - 3 - 5
// 1 - 3 - 15
// 2 - 3 - 4
// Output
// Total cost: 19

import java.util.*;

public class CheapTownTour {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int towns = Integer.parseInt(scanner.nextLine()); // read the number of towns
        int streets = Integer.parseInt(scanner.nextLine()); // read the number of streets
        int[] parent = new int[towns]; // create an array to store the parent of each node
        for (int i = 0; i < towns; i++) { // initialize the parent array
            parent[i] = i;
        }
        List<Edge> edges = new ArrayList<>(); // create a list to store the edges
        for (int i = 0; i < streets; i++) { // read the edges and add them to the list
            String[] tokens = scanner.nextLine().split(" - ");
            int from = Integer.parseInt(tokens[0]);
            int to = Integer.parseInt(tokens[1]);
            int cost = Integer.parseInt(tokens[2]);
            edges.add(new Edge(from, to, cost));
        }
        Collections.sort(edges); // sort the edges by cost
        int totalCost = 0; // initialize the total cost
        for (Edge edge : edges) { // iterate over the edges
            int rootA = findRoot(edge.from, parent); // find the root of the first node
            int rootB = findRoot(edge.to, parent); // find the root of the second node
            if (rootA != rootB) { // if the roots are different, add the cost to the total and update the parent array
                totalCost += edge.cost;
                parent[rootB] = rootA;
            }
        }
        System.out.println("Total cost: " + totalCost); // print the total cost
    }

    private static int findRoot(int node, int[] parent) { // find the root of a node
        if (node != parent[node]) { // if the node is not the root, update its parent
            parent[node] = findRoot(parent[node], parent);
        }
        return parent[node]; // return the root
    }

    static class Edge implements Comparable<Edge> { // define the Edge class
        int from, to, cost;

        Edge(int from, int to, int cost) { // constructor
            this.from = from;
            this.to = to;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge other) { // compare edges by cost
            return Integer.compare(this.cost, other.cost);
        }
    }
}
