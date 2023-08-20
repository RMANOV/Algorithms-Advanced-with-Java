// 1.	Eco-Friendly Highway Construction
// Suppose you are the manager of a construction company that has been hired to build a new highway network connecting several towns in a rural area. 
// However, you have been informed that some of the towns are located in areas with difficult terrain or environmental restrictions, 
// making it more expensive to build highways in those areas.
// Your goal is to connect all the towns minimizing the total cost of building the highways.
// Input
// 	The first line will contain a positive integer n (1 <= n <= 10^4), representing the number of connections.
// 	On the following n lines, you will receive details about each connection in the following format: "{town1} {town2} {cost} {environmentCost}".
// o	cost (1 <= cost <= 10^9), representing the cost of building a highway between the towns town1 and town2. 
// o	There will also be an optional integer argument -  environmentCost (0 <= environmentCost <= 10^9) for each highway, representing the additional environmental restriction cost of building the highway.
// Output
// 	The output should consist of a single integer, representing the minimum cost of building highways to connect all towns, taking into account the environmental restriction cost and it should be printed in the following format: "Total cost of building highways: {totalCost}".
// Example Input
// 3
// A B 2 1
// B C 3
// C D 4 1
// Example Output
// Total cost of building highways: 11
// Example Input2
// 5
// A B 2 3
// B C 3
// C D 4
// A C 2
// B D 1
// Example Output2
// Total cost of building highways: 6



import java.util.*;

// Define a class Edge that implements the Comparable interface
class Edge implements Comparable<Edge> {
    String start, end;
    int totalCost;

    // Constructor for Edge class
    Edge(String start, String end, int cost, int environmentCost) {
        this.start = start;
        this.end = end;
        this.totalCost = cost + environmentCost;
    }

    // Override the compareTo method of the Comparable interface
    @Override
    public int compareTo(Edge o) {
        return Integer.compare(this.totalCost, o.totalCost);
    }
}

// Define the Main class
public class Main {

    // Define a HashMap to store the parent of each town
    static Map<String, String> parent = new HashMap<>();

    // Define the find method to find the parent of a town
    public static String find(String v) {
        if (v.equals(parent.get(v))) return v;
        return parent.put(v, find(parent.get(v)));  // Path compression
    }

    // Define the union method to join two towns
    public static void union(String a, String b) {
        parent.put(find(a), find(b));
    }

    // Define the main method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read the number of connections
        int numberOfConnections = Integer.parseInt(scanner.nextLine());

        // Create a list to store all the edges
        List<Edge> edges = new ArrayList<>();

        // Read the details of each connection and add it to the edges list
        for (int i = 0; i < numberOfConnections; i++) {
            String[] tokens = scanner.nextLine().split("\\s+");
            String firstTown = tokens[0];
            String secondTown = tokens[1];
            int cost = Integer.parseInt(tokens[2]);
            int environmentCost = (tokens.length == 4) ? Integer.parseInt(tokens[3]) : 0;

            edges.add(new Edge(firstTown, secondTown, cost, environmentCost));
            parent.putIfAbsent(firstTown, firstTown);
            parent.putIfAbsent(secondTown, secondTown);
        }

        // Sort the edges by total cost
        edges.sort(Comparator.naturalOrder());

        // Initialize the total cost and the number of edges added
        int totalCost = 0;
        int edgesAdded = 0;

        // Iterate over the edges and add the necessary edges to the minimum spanning tree
        for (Edge edge : edges) {
            if (!find(edge.start).equals(find(edge.end))) {
                totalCost += edge.totalCost;
                union(edge.start, edge.end);
                edgesAdded++;
            }
            
            // If we have added all necessary edges for MST, break the loop
            if(edgesAdded == parent.size() - 1) {
                break;
            }
        }

        // Print the total cost of building highways
        System.out.println("Total cost of building highways: " + totalCost);
    }
}
