package kruskal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KruskalEdges {
    private final int weight;
    private final KruskalNode nodeA;
    private final KruskalNode nodeB;
}
