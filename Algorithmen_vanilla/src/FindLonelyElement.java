import java.util.ArrayList;
import java.util.List;

public class FindLonelyElement {
    public static void main(String[] args) {
        List<Integer> inputs = List.of(1,2,3,1,3,1,3);

        for (int resNum : getLonelyElements(inputs)) {
            System.out.println(resNum);
        }
    }

    private static List<Integer> getLonelyElements(List<Integer> input) {
        List<Integer> result = new ArrayList<>();
        List<Integer> del = new ArrayList<>();

        for (int num : input) {
            if (result.contains(num)) {
                result.removeIf(next -> next == num && del.contains(next));
                if (!del.contains(num)) {
                    del.add(num);
                }
            } else {
                result.add(num);
            }
        }

        return result;
    }
}
