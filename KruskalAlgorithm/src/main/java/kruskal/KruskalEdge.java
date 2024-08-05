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

    @Override
    public String toString() {
        return "node " + nodeA.getName() + " - " + weight + " - node " + nodeB.getName();
    }

    public boolean hasNode(KruskalEdge edge) {
        return     (nodeA.getName().equals(edge.getNodeA().getName()) || nodeA.getName().equals(edge.getNodeB().getName()))
                || (nodeB.getName().equals(edge.getNodeA().getName()) || nodeB.getName().equals(edge.getNodeB().getName()));
    }
}
