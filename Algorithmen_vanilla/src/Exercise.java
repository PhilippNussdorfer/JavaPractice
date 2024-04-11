import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Exercise {

    public static void main(String[] args) {
        List<Integer> in = new ArrayList<>(List.of(7,69,2,221,8974));
        System.out.println(calc(in));
    }

    private static List<Integer> calc(List<Integer> input) {
        var num1 = 0;
        var num2 = 0;

        Collections.sort(input);

        for (int i = 0; i < input.size() - 1; i++) {
            num1 += input.get(i);
        }

        for (int i = 1; i < input.size(); i++) {
            num2 += input.get(i);
        }

        return List.of(num1, num2);
    }
}
