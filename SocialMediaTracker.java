// Social Media Tracker
// You are given a social network represented as a collection of users, each with a unique identifier. 
// Each user can have one or more friends, and each friend relationship has a non-negative integer value that represents the strength of the friendship between the users. The higher the integer value, the stronger the friendship between the users.
// Your task is to develop an algorithm that will take two user IDs as input and output a path between them with the highest total strength. 
// In other words, the algorithm should find the path with the best sum of friendship strengths between the two users.
// However, there is a catch: if there are multiple paths with the same total strength value, you should select the one with the least number of hops. When we say "friend hops", we are referring to the number of friendships that need to be traversed to reach from one user to the other. 
// Input
// 	The first line of the input contains an integer r (1 ≤ r ≤ 1000) - the number of friend relationships.
// 	The next r lines each contain three space-separated values: "{userA} {userB} {influence}", where userA and userB are unique user identifiers and influence is a non-negative integer (0 ≤ influence ≤ 10^6). 
// o	These values represent a friendship between userA and userB, with the strength of the friendship being represented by the influence value.
// o	The influence is in the userA -> userB direction. 
// 	On the following line, you will receive the startUser.
// 	On the last line, you will receive the destinationUser.
// Output
// 	Print the best sum of friendship strengths and the hops in the following format: "({bestSum}, {hops})".
// Constraints
// 	There will always be a path from the startUser to the destinationUser.
// 	There will be no case with multiple paths with equal bestSum and hops.
// Example Input
// 5
// P G 5
// G I 10
// P A 3
// A N 15
// N G 3
// P
// I
// Example Output
// (31, 4)
// Example Input2
// 5
// P G 21
// G I 10
// P A 3
// A N 15
// N G 3
// P
// I
// Example Output2
// (31, 2)


import java.util.*;

public class Main {

    // Define a class Edge to represent the friendship between two users
    static class Edge {
        String target; // the user identifier of the friend
        int influence; // the strength of the friendship

        Edge(String target, int influence) {
            this.target = target;
            this.influence = influence;
        }
    }

    // Define a graph to represent the social network
    static Map<String, List<Edge>> graph = new HashMap<>();

    // Define variables to store the maximum influence and minimum hops
    static int maxInfluence = 0;
    static int minHops = Integer.MAX_VALUE;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read the number of friend relationships
        int r = Integer.parseInt(scanner.nextLine());

        // Read the friend relationships and add them to the graph
        for (int i = 0; i < r; i++) {
            String[] tokens = scanner.nextLine().split("\\s+");
            String startUser = tokens[0];
            String endUser = tokens[1];
            int influence = Integer.parseInt(tokens[2]);

            graph.putIfAbsent(startUser, new ArrayList<>());
            graph.get(startUser).add(new Edge(endUser, influence));
        }

        // Read the start user and destination user
        String startUser = scanner.nextLine();
        String destinationUser = scanner.nextLine();

        // Find the path with the highest total strength and the least number of hops
        dfs(startUser, destinationUser, 0, 0, new HashSet<>());

        // Print the result
        System.out.printf("(%d, %d)\n", maxInfluence, minHops);
    }

    // Depth-first search to find the path with the highest total strength and the least number of hops
    public static void dfs(String current, String destination, int currentInfluence, int currentHops, Set<String> visited) {
        // If the current user is the destination user, update the maximum influence and minimum hops if necessary
        if (current.equals(destination)) {
            if (currentInfluence > maxInfluence || (currentInfluence == maxInfluence && currentHops < minHops)) {
                maxInfluence = currentInfluence;
                minHops = currentHops;
            }
            return;
        }

        // Mark the current user as visited
        visited.add(current);

        // Traverse the friends of the current user recursively
        for (Edge edge : graph.getOrDefault(current, new ArrayList<>())) {
            if (!visited.contains(edge.target)) {
                dfs(edge.target, destination, currentInfluence + edge.influence, currentHops + 1, new HashSet<>(visited));
            }
        }
    }
}
