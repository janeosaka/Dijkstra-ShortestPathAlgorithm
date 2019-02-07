class EdgeNode {

    int destVertex;
    int weight;

    public EdgeNode(int v, int w) {
        this.destVertex = v;
        this.weight = w;
    }

    @Override
    public String toString() {
        return ("( " + destVertex + "," + weight + " )");
    }
}