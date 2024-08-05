package kruskal;

import lombok.*;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class KruskalNode {
    private final String name;
    @Setter
    private boolean marked = false;
}
