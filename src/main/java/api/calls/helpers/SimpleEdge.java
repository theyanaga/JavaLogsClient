package api.calls.helpers;

import parsing.entities.GraphNode;

import java.time.Duration;

public class SimpleEdge {

    private Duration distance;

    private int srcId;

    private int destId;

    public SimpleEdge(SimpleNode src, SimpleNode dest) {
        this.srcId = src.getSessionId();
        this.destId = dest.getSessionId();
        this.distance = Duration.between(src.getTime(), dest.getTime());
    }

    public int getSrcId() {
        return srcId;
    }

    public void setSrcId(int srcId) {
        this.srcId = srcId;
    }

    public int getDestId() {
        return destId;
    }

    public void setDestId(int destId) {
        this.destId = destId;
    }

    public Duration getDistance() {
        return distance;
    }

    public void setDistance(Duration distance) {
        this.distance = distance;
    }
}
