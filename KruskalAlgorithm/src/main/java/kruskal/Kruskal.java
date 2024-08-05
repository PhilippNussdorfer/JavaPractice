package kruskal;

import lombok.Getter;

import java.util.*;

@Getter
public class Kruskal {
    private final List<KruskalEdge> finishedTree = new ArrayList<>();
    private final int maxEdges;

    public Kruskal(List<KruskalEdge> sortedEdges, int maxEdges) {
        this.maxEdges = maxEdges;

        calcMinTree(sortedEdges);
    }

    private void calcMinTree(List<KruskalEdge> sortedEdges) {
        for (KruskalEdge edge : sortedEdges) {

            if (finishedTree.size() < maxEdges && checkForLoop(edge)) {
                finishedTree.add(edge);
            }

            if (finishedTree.size() < maxEdges)
                for (KruskalEdge resettingEdge : sortedEdges)
                    resettingEdge.resetMarkedNodes();
        }
    }

    private boolean checkForLoop(KruskalEdge selectedEdge) {
        List<KruskalEdge> currentEdges = new ArrayList<>();
        List<KruskalEdge> nextEdges = new ArrayList<>();
        int count = 0;

        startingSetup(selectedEdge, currentEdges);

        while (!nextEdges.isEmpty() || count == 0) {

            nextEdges.clear();
            addNextEdgesToList(currentEdges, nextEdges);
            currentEdges.addAll(nextEdges);

            if (createsLoop(currentEdges))
                return false;

            count ++;
        }

        return true;
    }

    private void addNextEdgesToList(List<KruskalEdge> currentEdges, List<KruskalEdge> nextEdges) {
        for (KruskalEdge currentEdge : currentEdges) {
            for (KruskalEdge nextPossibleEdge : finishedTree) {
                if (addNextEdge(currentEdge, nextPossibleEdge, nextEdges)) {
                    break;
                }
            }
        }
    }

    private boolean createsLoop(List<KruskalEdge> foundEdges) {
        Set<KruskalEdge> kruskalEdgeSet = new HashSet<>(foundEdges);

        return kruskalEdgeSet.size() < foundEdges.size();
    }

    private boolean addNextEdge(KruskalEdge currentEdge, KruskalEdge nextPossibleEdge, List<KruskalEdge> nextEdges) {
        if (!currentEdge.nodeOfEdgeIsMarked(nextPossibleEdge) && currentEdge.hasNode(nextPossibleEdge)) {
            nextEdges.add(nextPossibleEdge);
            return true;
        }
        return false;
    }

    private void startingSetup(KruskalEdge selectedEdge, List<KruskalEdge> currentEdges) {
        for (KruskalEdge edge : finishedTree) {
            if (edge.hasNode(selectedEdge))
                currentEdges.add(edge);
        }
    }
}
