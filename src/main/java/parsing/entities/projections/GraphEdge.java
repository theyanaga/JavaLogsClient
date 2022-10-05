package parsing.entities.projections;

import parsing.entities.GraphNode;

import java.time.Duration;
import java.time.ZonedDateTime;

public class GraphEdge {

    private GraphNode src;

    private GraphNode dest;

    private Duration distance;

    public GraphEdge(GraphNode src, GraphNode dest) {
        this.src = src;
        this.dest = dest;
        this.distance = Duration.between(src.getTime(), dest.getTime());
    }

    public GraphNode getSrc() {
        return src;
    }

    public void setSrc(GraphNode src) {
        this.src = src;
    }

    public GraphNode getDest() {
        return dest;
    }

    public void setDest(GraphNode dest) {
        this.dest = dest;
    }

    public Duration getDistance() {
        return distance;
    }

    public void setDistance(Duration distance) {
        this.distance = distance;
    }
}
