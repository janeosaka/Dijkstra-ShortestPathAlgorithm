class DistNode implements Comparable<DistNode> {

    public int vertex;
    public int distance;

    public DistNode(int v, int d) {
        vertex = v;
        distance = d;
    }

    public int compareTo(DistNode node) {
        return Integer.compare(distance, node.distance);
    }
}