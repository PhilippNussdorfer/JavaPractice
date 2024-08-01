package kruskal;

import java.util.ArrayList;
import java.util.List;

public class Starter {
    private static final List<KruskalEdges> EDGES = new ArrayList<>();
    private static final List<KruskalNode> NODES = new ArrayList<>(List.of(
            new KruskalNode("0"),
            new KruskalNode("1"),
            new KruskalNode("2"),
            new KruskalNode("3"),
            new KruskalNode("4"),
            new KruskalNode("5"),
            new KruskalNode("6"),
            new KruskalNode("8"),
            new KruskalNode("9")
    ));

    public static void main(String[] args) {
        initKruskalEdges();

    }

    private static void initKruskalEdges() {
        EDGES.addAll(List.of(
                new KruskalEdges(1, NODES.get(7), NODES.get(6)),
                new KruskalEdges(8, NODES.get(7), NODES.get(0)),
                new KruskalEdges(4, NODES.get(0), NODES.get(1)),
                new KruskalEdges(11, NODES.get(1), NODES.get(7)),
                new KruskalEdges(8, NODES.get(1), NODES.get(2)),
                new KruskalEdges(7, NODES.get(7), NODES.get(8)),
                new KruskalEdges(2, NODES.get(6), NODES.get(5)),
                new KruskalEdges(6, NODES.get(8), NODES.get(6)),
                new KruskalEdges(2, NODES.get(8), NODES.get(2)),
                new KruskalEdges(4, NODES.get(2), NODES.get(5)),
                new KruskalEdges(7, NODES.get(2), NODES.get(3)),
                new KruskalEdges(9, NODES.get(3), NODES.get(4)),
                new KruskalEdges(14, NODES.get(5), NODES.get(3)),
                new KruskalEdges(10, NODES.get(4), NODES.get(5))
        ));
    }
}
