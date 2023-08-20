// E-Shop
// You are the owner of a small online store that sells a variety of products.
// You want to optimize your store's profits by selecting which products to stock based on their popularity and profit margin. 
// Each product has a weight and value (representing its profit margin), and you have limited storage space.
// There is a catch: some of the products have a relationship with each other, and if you decide to include one product, 
// you must also include another. However, this rule also is applied recursively, meaning that if you have Item1-Item2 and Item2-Item3 pairs, 
// you should take Item3 if you pick Item1. 
// You must select the optimal products to include in your store to maximize profits while staying within your storage space.
// Input
// 	The first line of the input contains a positive integer n (1 <= n <= 10^4), representing the number of items.
// 	The next n lines contain information about the items. Each item is described in the format "{itemName} {itemWeight} {itemValue}".
// o	itemName is a string representing the name of the item and it will be unique.
// o	itemWeight is an integer representing the weight of the item (1 <= itemWeight <= 100)
// o	itemValue is an integer representing the value of the item (1 <= itemValue <= 10^3).
// 	On the next line, you will receive a positive integer p (1 <= p <= 10^4), representing the number of pairs.
// 	The next p lines contain information about the item pairs. Each item pair is described in the format "{itemName1} {itemName2}", 
// where itemName1 and itemName2 are the names of two items that are related to each other.
// o	Note that the relationship between items is bidirectional, meaning that if item A is related to item B, then item B is also related to item A.
// 	On the last line, you will receive an integer s (1 <= s <= 10^4), representing the maximum storage capacity of the store.
// Output
// 	Print the names of all selected items, ordered alphabetically, one item per line.
// o	There will be always only one valid combination that satisfies all conditions.
// Example Input
// 3
// Item1 3 3
// Item2 2 2
// Item3 1 4
// 1
// Item1 Item2
// 4
// Example Output
// Item3
// Example Input2
// 5
// Item1 2 3
// Item2 2 3
// Item3 2 2
// Item4 2 2
// Item5 2 20
// 2
// Item1 Item3
// Item1 Item4
// 6
// Example Output2
// Item2
// Item5

import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        // Create a map to store the items with their weight and value
        Map<String, int[]> items = new HashMap<>();

        // Read the input and store the items in the map
        for (int i = 0; i < n; i++) {
            String name = sc.next();
            int weight = sc.nextInt();
            int value = sc.nextInt();
            items.put(name, new int[]{weight, value});
        }

        int p = sc.nextInt();
        // Create a graph to store the relationships between the items
        Map<String, Set<String>> graph = buildGraph(p, sc, items.keySet());

        int maxCapacity = sc.nextInt();
        // Get the connected components of the graph
        List<Set<String>> components = getConnectedComponents(graph);

        // Find the items that maximize the value within the given capacity
        List<String> result = maximizeValue(components, items, maxCapacity);

        // Print the selected items in alphabetical order
        for (String item : result) {
            System.out.println(item);
        }
    }

    // Build a graph to store the relationships between the items
    static Map<String, Set<String>> buildGraph(int p, Scanner sc, Set<String> items) {
        Map<String, Set<String>> graph = new HashMap<>();
        for (String item : items) {
            graph.put(item, new HashSet<>());
        }
        for (int i = 0; i < p; i++) {
            String item1 = sc.next();
            String item2 = sc.next();
            graph.get(item1).add(item2);
            graph.get(item2).add(item1);
        }
        return graph;
    }

    // Get the connected components of the graph
    static List<Set<String>> getConnectedComponents(Map<String, Set<String>> graph) {
        Set<String> seen = new HashSet<>();
        List<Set<String>> components = new ArrayList<>();

        for (String item : graph.keySet()) {
            if (!seen.contains(item)) {
                Set<String> component = new HashSet<>();
                Queue<String> queue = new LinkedList<>();
                queue.add(item);
                seen.add(item);
                while (!queue.isEmpty()) {
                    String node = queue.poll();
                    component.add(node);
                    for (String neighbor : graph.get(node)) {
                        if (!seen.contains(neighbor)) {
                            seen.add(neighbor);
                            queue.add(neighbor);
                        }
                    }
                }
                components.add(component);
            }
        }
        return components;
    }

    // Find the items that maximize the value within the given capacity
    static List<String> maximizeValue(List<Set<String>> components, Map<String, int[]> items, int maxCapacity) {
        int n = components.size();
        // Create a 2D array to store the maximum value for each component and capacity
        List<int[]> dp = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            dp.add(new int[maxCapacity + 1]);
        }

        // Fill in the 2D array with the maximum value for each component and capacity
        for (int i = 1; i <= n; i++) {
            Set<String> comp = components.get(i - 1);
            int compWeight = getWeight(comp, items);
            int compValue = getValue(comp, items);

            for (int w = 0; w <= maxCapacity; w++) {
                if (compWeight <= w) {
                    dp.get(i)[w] = Math.max(dp.get(i - 1)[w], dp.get(i - 1)[w - compWeight] + compValue);
                } else {
                    dp.get(i)[w] = dp.get(i - 1)[w];
                }
            }
        }

        // Find the selected items that maximize the value within the given capacity
        List<String> selectedItems = new ArrayList<>();
        int remainingCapacity = maxCapacity;

        for (int i = n; i >= 1; i--) {
            if (dp.get(i)[remainingCapacity] != dp.get(i - 1)[remainingCapacity]) {
                selectedItems.addAll(components.get(i - 1));
                remainingCapacity -= getWeight(components.get(i - 1), items);
            }
        }

        // Sort the selected items in alphabetical order
        Collections.sort(selectedItems);
        return selectedItems;
    }

    // Get the total weight of a component
    static int getWeight(Set<String> component, Map<String, int[]> items) {
        return component.stream().mapToInt(item -> items.get(item)[0]).sum();
    }

    // Get the total value of a component
    static int getValue(Set<String> component, Map<String, int[]> items) {
        return component.stream().mapToInt(item -> items.get(item)[1]).sum();
    }
}
