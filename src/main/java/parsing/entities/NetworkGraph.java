package parsing.entities;

import parsing.entities.projections.GraphEdge;

import java.util.List;

public class NetworkGraph {

    private List<GraphNode> nodes;

    private List<GraphEdge> edges;

    public NetworkGraph(List<GraphNode> nodes, List<GraphEdge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public List<GraphNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<GraphNode> nodes) {
        this.nodes = nodes;
    }

    public List<GraphEdge> getEdges() {
        return edges;
    }

    public void setEdges(List<GraphEdge> edges) {
        this.edges = edges;
    }
}
