package com.vendor.graph;

public class GraphEdge {
    private GraphDot firstDot;

    public GraphDot getFirstDot() {
        return firstDot;
    }

    public void setFirstDot(GraphDot firstDot) {
        this.firstDot = firstDot;
    }

    public GraphDot getSecondDot() {
        return secondDot;
    }

    public void setSecondDot(GraphDot secondDot) {
        this.secondDot = secondDot;
    }

    private GraphDot secondDot;

}
