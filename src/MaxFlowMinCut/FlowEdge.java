package MaxFlowMinCut;

public class FlowEdge {
    private final int v, w;
    private final int capacity;
    private int flow;

    public FlowEdge(int v, int w, int c) {
        this.v = v;
        this.w = w;
        capacity = c;
    }

    public int to() {
        return this.w;
    }

    public int from() {
        return this.v;
    }

    public int flow() {
        return this.flow;
    }

    public int capacity() {
        return this.capacity;
    }

    public int other(int vertex) {
        if (v == vertex) return w;
        else if (w ==vertex) return v;
        else throw new IllegalArgumentException();
    }

    public int residualCapacityTo(int vertex) {
        if (vertex == v) return flow;
        else if(vertex == w) return capacity - flow;
        else throw new IllegalArgumentException();
    }

    public void addResidualCapacityTo(int vertex, int delta) {
        if (vertex == v) flow -= delta;
        else if (vertex == w) flow += delta;
        else throw new IllegalArgumentException();
    }

}
