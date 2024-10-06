/*pathfinding Algorithm (A Algorithm)*
Use Case: Used in GPS navigation systems to find the shortest path between two locations.
Description: The A* algorithm is an informed search algorithm used for pathfinding and graph traversal.*/

import java.util.*;

class Node implements Comparable<Node> {
    public int x, y, gCost, hCost, fCost;
    public Node parent;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.fCost, other.fCost);
    }
}

public class AStar {
    public static final int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public static List<Node> aStarSearch(int[][] grid, Node start, Node goal) {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        HashSet<Node> closedList = new HashSet<>();

        start.gCost = 0;
        start.hCost = manhattanDistance(start, goal);
        start.fCost = start.gCost + start.hCost;

        openList.add(start);

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            if (current.x == goal.x && current.y == goal.y) {
                return constructPath(current);
            }

            closedList.add(current);

            for (int[] direction : directions) {
                int newX = current.x + direction[0];
                int newY = current.y + direction[1];

                if (isValid(grid, newX, newY) && !isInList(closedList, newX, newY)) {
                    Node neighbor = new Node(newX, newY);
                    int tentativeGCost = current.gCost + 1;

                    if (tentativeGCost < neighbor.gCost || !isInList(openList, newX, newY)) {
                        neighbor.gCost = tentativeGCost;
                        neighbor.hCost = manhattanDistance(neighbor, goal);
                        neighbor.fCost = neighbor.gCost + neighbor.hCost;
                        neighbor.parent = current;

                        if (!isInList(openList, newX, newY)) {
                            openList.add(neighbor);
                        }
                    }
                }
            }
        }
        return null; // No path found
    }

    private static int manhattanDistance(Node a, Node b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    private static boolean isValid(int[][] grid, int x, int y) {
        return x >= 0 && y >= 0 && x < grid.length && y < grid[0].length && grid[x][y] == 0;
    }

    private static boolean isInList(Collection<Node> list, int x, int y) {
        return list.stream().anyMatch(n -> n.x == x && n.y == y);
    }

    private static List<Node> constructPath(Node current) {
        List<Node> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        int[][] grid = {
            {0, 0, 0, 0},
            {0, 1, 1, 0},
            {0, 0, 0, 0},
            {0, 1, 0, 0}
        };

        Node start = new Node(0, 0);
        Node goal = new Node(3, 3);

        List<Node> path = aStarSearch(grid, start, goal);

        if (path != null) {
            for (Node node : path) {
                System.out.println("Path: (" + node.x + ", " + node.y + ")");
            }
        } else {
            System.out.println("No path found!");
        }
    }
}



The A* algorithm calculates the shortest path in a grid with obstacles using both the distance traveled (gCost) and the heuristic estimate (hCost).
