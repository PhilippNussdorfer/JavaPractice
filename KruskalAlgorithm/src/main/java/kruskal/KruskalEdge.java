package kruskal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KruskalEdge {
    private final int weight;
    private final KruskalNode nodeA;
    private final KruskalNode nodeB;

    @Override
    public String toString() {
        return "node " + nodeA.getName() + " - " + weight + " - node " + nodeB.getName();
    }

    public boolean hasNode(KruskalNode node) {
        return nodeA.getName().equals(node.getName()) || nodeB.getName().equals(node.getName());
    }
}
