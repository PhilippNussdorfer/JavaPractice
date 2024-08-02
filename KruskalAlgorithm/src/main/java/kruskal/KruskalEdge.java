package kruskal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class KruskalEdge {
    private final int weight;
    private final KruskalNode nodeA;
    private final KruskalNode nodeB;
    @Setter
    private boolean marked = false;

    @Override
    public String toString() {
        return "node " + nodeA.getName() + " - " + weight + " - node " + nodeB.getName();
    }

    public boolean hasNode(KruskalNode node) {
        return nodeA.getName().equals(node.getName()) || nodeB.getName().equals(node.getName());
    }
}
