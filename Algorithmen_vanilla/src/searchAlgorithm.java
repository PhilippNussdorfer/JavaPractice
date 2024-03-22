import java.util.ArrayList;
import java.util.List;

public class searchAlgorithm {
    public static void main(String[] args) {
        searchAlgorithm search = new searchAlgorithm();

        System.out.println(search.getNumOfSmallerThanTarget(List.of(-1, 1, 2, 3, 1), 2));
        System.out.println(search.getNumOfSmallerThanTarget(List.of(1, 3, 5, 7, 9), 10));
    }

    public int getNumOfSmallerThanTarget(List<Integer> list, int target) {
        int result = 0;
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i) + list.get(j) < target) {
                    result++;
                }
            }
        }
        return result;
    }
}
