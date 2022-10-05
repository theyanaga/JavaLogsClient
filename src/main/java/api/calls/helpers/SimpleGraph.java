package api.calls.helpers;

import java.util.List;

public class SimpleGraph {

    List<SimpleNode> nodes;

    List<SimpleEdge> edges;

    public SimpleGraph(List<SimpleNode> nodes, List<SimpleEdge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public List<SimpleNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<SimpleNode> nodes) {
        this.nodes = nodes;
    }

    public List<SimpleEdge> getEdges() {
        return edges;
    }

    public void setEdges(List<SimpleEdge> edges) {
        this.edges = edges;
    }
}
