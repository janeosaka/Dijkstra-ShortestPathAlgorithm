import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Graph {

    private LinkedList<EdgeNode>[] adjList;
    private int nVertices;
    private int nEdges;

    private int[] visited; // Used in Dijkstra's algorithm

    public static void main(String[] args) {

        int nVertices = 5; // number of vertices numbered 0,1,2,3

        int[][] graphData = { // list of the edges
                {0, 1, 20}, {2, 0, 30}, {1, 2, 20}, {2, 3, 10}, {0, 3, 40}, {1, 3, 60}, {3, 0, 15}, {4, 3, 12}};

        // For example {0,1,20} means there is a an edge from 0 to 1 with weight = 20

        Graph graph = new Graph(graphData, nVertices);
        graph.printGraph();

        System.out.println();
        graph.dfsTraversal(4);

        System.out.println("\n");
        graph.dijkstraShortestPaths(1);
    }

    @SuppressWarnings("unchecked")
    // constructor
    public Graph(int[][] graphData, int nVertices) {

        this.nVertices = nVertices;
        this.adjList = new LinkedList[nVertices];
        this.nEdges = graphData.length;

        // Populate 'adjList' from 'graphData'
        for (int i = 0; i < graphData.length; i++) {

            int originVertex = graphData[i][0];
            int destinationVertex = graphData[i][1];
            int weight = graphData[i][2];

            EdgeNode edgeNode = new EdgeNode(destinationVertex, weight);

            LinkedList<EdgeNode> originLinkedList = adjList[originVertex];
            if (originLinkedList == null) {
                originLinkedList = new LinkedList<EdgeNode>();
                adjList[originVertex] = originLinkedList;
            }
            originLinkedList.add(edgeNode);
        }
    }

    public void printGraph() {
        System.out.println("Graph: nVertices = " + this.nVertices + " nEdges = " + this.nEdges);
        System.out.println("Adjacency Lists");

        for (int i = 0; i < adjList.length; i++) {

            System.out.print("v= " + i + " [");

            LinkedList<EdgeNode> vertexLinkedList = adjList[i];

            for (EdgeNode edgeNode : vertexLinkedList) {
                System.out.print("[" + edgeNode.destVertex + "," + edgeNode.weight + "]");
            }

            System.out.println("]");
        }
    }

    public void dfsTraversal(int startVertex) {
        System.out.println("DFS traversal starting with index: " + startVertex);

        this.visited = new int[nVertices];

        dfs(startVertex);
    }

    private void dfs(int startVertex) {

        if (visited[startVertex] == 1) { // This node has already been visited so no need to visit it again
            return;
        }

        System.out.print(startVertex + " ");
        visited[startVertex] = 1;

        LinkedList<EdgeNode> linkedList = this.adjList[startVertex];
        for (EdgeNode edgeNode : linkedList) {
            dfs(edgeNode.destVertex);
        }

    }

    public void dijkstraShortestPaths(int startVertex) {

        PriorityQueue<DistNode> pq = new PriorityQueue<DistNode>(this.nVertices);

        int distance[] = new int[this.nVertices];
        int parent[] = new int[this.nVertices];
        this.visited = new int[this.nVertices];

        for (int i = 0; i < this.nVertices; i++) {
            distance[i] = Integer.MAX_VALUE; // Initialize all distances as INFINITE
            parent[i] = -1;
        }

        // set distance from source to always be 0
        distance[startVertex] = 0;


        pq.add(new DistNode(startVertex, distance[startVertex]));
        // Find shortest path for all vertices
        while (!pq.isEmpty()) {

            DistNode u = pq.poll();

            for (EdgeNode neighbour : this.adjList[u.vertex]) {

                int newDist = distance[u.vertex] + neighbour.weight;

                if (distance[neighbour.destVertex] > newDist) {

                    pq.remove(neighbour.destVertex);
                    distance[neighbour.destVertex] = newDist;

                    parent[neighbour.destVertex] = u.vertex;

                    // Reenter the node with new distance.
                    pq.add(new DistNode(neighbour.destVertex, distance[neighbour.destVertex]));
                }
            }
        }

        printShortestPaths(startVertex, distance, parent);

        return;
    }

    private void printShortestPaths(int start, int[] distance, int[] parent) {
        System.out.println("Shortest Paths from vertex " + start + " to vertex");

        for (int i = 0; i < distance.length; i++) {

            System.out.print(i + ": ");

            if (distance[i] == Integer.MAX_VALUE) {
                System.out.println("There is no such path");
                continue;
            }

            System.out.print("[");

            List<Integer> path = calculatePath(start, i, distance, parent);

            for (int j = 0; j < path.size(); j++) {

                System.out.print(path.get(j));

                if (j != path.size() - 1)
                    System.out.print(", ");
            }

            System.out.print("]");
            System.out.println(" Path weight = " + distance[i]);
        }
    }

    private List<Integer> calculatePath(int start, int dest, int[] distance, int[] parent) {
        List<Integer> path = new ArrayList<Integer>();
        path.add(dest);

        if (start != dest) {
            int previousVertex = parent[dest];
            while (previousVertex != -1 && previousVertex != start) {
                path.add(previousVertex);
                previousVertex = parent[previousVertex];
            }

            path.add(start);

            Collections.reverse(path);
        }

        return path;
    }

}




