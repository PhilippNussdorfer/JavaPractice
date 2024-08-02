package kruskal.QuickSort;

import kruskal.KruskalEdge;

import java.util.ArrayList;
import java.util.List;

public class QuickSort {
    public List<KruskalEdge> sortEdges(List<KruskalEdge> edges) {
        List<KruskalEdge> smaller = new ArrayList<>();
        List<KruskalEdge> bigger = new ArrayList<>();

        int index = (edges.size() / 2) - 1;

        if (index < 0)
            index = 0;

        var pointer = edges.get(index);
        edges.remove(index);

        divide(edges, pointer, smaller, bigger);

        if (smaller.size() > 1) {
            smaller = sortEdges(smaller);
        }
        if (bigger.size() > 1) {
            bigger = sortEdges(bigger);
        }

        smaller.add(pointer);
        smaller.addAll(bigger);

        return smaller;
    }

    private void divide(List<KruskalEdge> edges, KruskalEdge pointer, List<KruskalEdge> smaller, List<KruskalEdge> bigger) {
        for (KruskalEdge edge : edges) {
            if (edge.getWeight() <= pointer.getWeight())
                smaller.add(edge);
            else
                bigger.add(edge);
        }
    }
}
