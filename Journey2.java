import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Journey2 {
    public static void main(String args[]) {
        //Create JFrame
        JFrame frame = new JFrame("Dijkstra's Shortest Path");
        // Create JPanel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 4, 4));

        //JLabels Create
        JLabel sourceLabel = new JLabel("Source:");
        JLabel destinationLabel = new JLabel("Destination:");
        JLabel resultLabel = new JLabel("Shortest Path:");

        //JTextFields Create
        JTextField sourceField = new JTextField(2);
        JTextField destinationField = new JTextField(2);

        //Create JText Area
        JTextArea resultArea = new JTextArea(20, 20);
        resultArea.setEditable(false);

        //JButton Create
        JButton calculateButton = new JButton("Calculate");

        //Adding Components to JPanel
        panel.add(sourceLabel);
        panel.add(sourceField);
        panel.add(destinationLabel);
        panel.add(destinationField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(calculateButton);
        panel.add(resultLabel);
        panel.add(resultArea);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String source = sourceField.getText();
                String destination = destinationField.getText();

                //Call Dijkastra's algorithm
                Map<String, Integer> shortestPathResult = calculateShortestPath(source, destination);
                resultArea.setText(shortestPathResult.toString());
            }
        });

        //Add Frame to JPanel
        frame.add(panel);
        //Configure JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static Map<String, Integer> calculateShortestPath(String source, String destination) {
        //Create a graph representation using an adjacency list
        Map<String, Map<String, Integer>> graph = new HashMap<>();

        // Adding edges & weights to the graph
        Map<String, Integer> edgesFromA = new HashMap<>();
        edgesFromA.put("B", 5);
        edgesFromA.put("C", 10);
        graph.put("A", edgesFromA);

        Map<String, Integer> edgesFromB = new HashMap<>();
        edgesFromB.put("A", 5);
        edgesFromB.put("C", 3);
        graph.put("B", edgesFromB);

        Map<String, Integer> edgesFromC = new HashMap<>();
        edgesFromC.put("A", 10);
        edgesFromC.put("B", 3);
        graph.put("C", edgesFromC);

        //Initialize distance & visited nodes
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previousNodes = new HashMap<>();
        Set<String> visitedNodes = new HashSet<>();

        for (String node : graph.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(source, 0);

        //Dijkstra's algorithm
        while (!visitedNodes.contains(destination)) {
            String currentNode = getClosestNode(distances, visitedNodes);
            visitedNodes.add(currentNode);

            Map<String, Integer> neighbors = graph.get(currentNode);
            for (String neighbor : neighbors.keySet()) {
                int distance = distances.get(currentNode) + neighbors.get(neighbor);
                if (distance < distances.get(neighbor)) {
                    distances.put(neighbor, distance);
                    previousNodes.put(neighbor, currentNode);
                }
            }
        }

        // Build the shortest path
        List<String> path = new ArrayList<>();
        String current = destination;
        while (previousNodes.containsKey(current)) {
            path.add(0, current);
            current = previousNodes.get(current);
        }
        path.add(0, source);

        // Build the result map
        Map<String, Integer> shortestPaths = new HashMap<>();
        for (String node : path) {
            shortestPaths.put(node, distances.get(node));
        }
        return shortestPaths;
    }

    private static String getClosestNode(Map<String, Integer> distances, Set<String> visitedNodes) {
        int minDistance = Integer.MAX_VALUE;
        String closestNode = null;
        for (String node : distances.keySet()) {
            int distance = distances.get(node);
            if (distance < minDistance && !visitedNodes.contains(node)) {
                minDistance = distance;
                closestNode = node;
            }
        }
        return closestNode;
    }
}
