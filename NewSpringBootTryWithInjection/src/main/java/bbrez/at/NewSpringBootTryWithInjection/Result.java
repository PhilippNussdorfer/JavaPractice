package bbrez.at.NewSpringBootTryWithInjection;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
public class Result {
    private String studentName;
    private int result;

    @Override
    public String toString() {
        return studentName + ", result=" + result;
    }
}
