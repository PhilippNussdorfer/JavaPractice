package kruskal;

import kruskal.QuickSort.QuickSort;

import java.util.ArrayList;
import java.util.List;

public class Starter {
    private static final List<KruskalEdge> EDGES = new ArrayList<>();
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
        QuickSort quickSort = new QuickSort();
        Kruskal kruskal = new Kruskal(quickSort.sortEdges(EDGES), NODES.size() - 1);

        for (KruskalEdge edge : kruskal.getFinishedTree()) {
            System.out.println(edge.toString());
        }
    }

    private static void initKruskalEdges() {
        EDGES.addAll(List.of(
                new KruskalEdge(1, NODES.get(7), NODES.get(6)),
                new KruskalEdge(8, NODES.get(7), NODES.get(0)),
                new KruskalEdge(4, NODES.get(0), NODES.get(1)),
                new KruskalEdge(11, NODES.get(1), NODES.get(7)),
                new KruskalEdge(8, NODES.get(1), NODES.get(2)),
                new KruskalEdge(7, NODES.get(7), NODES.get(8)),
                new KruskalEdge(2, NODES.get(6), NODES.get(5)),
                new KruskalEdge(6, NODES.get(8), NODES.get(6)),
                new KruskalEdge(2, NODES.get(8), NODES.get(2)),
                new KruskalEdge(4, NODES.get(2), NODES.get(5)),
                new KruskalEdge(7, NODES.get(2), NODES.get(3)),
                new KruskalEdge(9, NODES.get(3), NODES.get(4)),
                new KruskalEdge(14, NODES.get(5), NODES.get(3)),
                new KruskalEdge(10, NODES.get(4), NODES.get(5))
        ));
    }
}
