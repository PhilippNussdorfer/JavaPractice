package kruskal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KruskalEdge {
    private final int weight;
    private final KruskalNode nodeA;
    private final KruskalNode nodeB;

    @Override
    public String toString() {
        return "node " + nodeA.getName() + " - " + weight + " - node " + nodeB.getName();
    }

    public boolean hasNode(KruskalEdge edge) {
        if (nodeA.getName().equals(edge.getNodeA().getName()) || nodeA.getName().equals(edge.getNodeB().getName())) {
            markNode(nodeA);
            return true;
        }

        if (nodeB.getName().equals(edge.getNodeA().getName()) || nodeB.getName().equals(edge.getNodeB().getName())) {
            markNode(nodeB);
            return true;
        }

        return false;
    }

    private void markNode(KruskalNode node) {
        node.setMarked(true);
    }

    public boolean nodeOfEdgeIsMarked(KruskalEdge edge) {
        if ((nodeA.getName().equals(edge.getNodeA().getName()) || nodeA.getName().equals(edge.getNodeB().getName())) && nodeA.isMarked())
            return true;

        return (nodeB.getName().equals(edge.getNodeA().getName()) || nodeB.getName().equals(edge.getNodeB().getName())) && nodeB.isMarked();
    }

    public void resetMarkedNodes() {
        nodeA.setMarked(false);
        nodeB.setMarked(false);
    }
}
