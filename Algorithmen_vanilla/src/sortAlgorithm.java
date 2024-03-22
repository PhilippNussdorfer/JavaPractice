import java.util.ArrayList;
import java.util.List;

public class sortAlgorithm {
    public static void main(String[] args) {
        sortAlgorithm sort = new sortAlgorithm();

        /*var out = main.getSumFromList(List.of(2, 45, 9, 7), 11);

        for (int i : out) {
            System.out.println(i);
        }*/

        List<Integer> integerList = new ArrayList<>(List.of(5, 9, 1, 2, 4, 0, 3, 6, 8, 7));

        for (int num : integerList) {
            System.out.print(num);
        }
        System.out.println('\n');

        var list = sort.quickSort(integerList);

        for (int num : list) {
            System.out.print(num);
        }
    }

    public List<Integer> getSumFromList(List<Integer> list, int searched) {

        for (int j = 0; j < list.size(); j++) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == searched) {
                    return List.of(i);
                }

                if (list.get(j) == searched) {
                    return List.of(j);
                }

                if (list.get(j) + list.get(i) == searched) {
                    return List.of(j, i);
                }
            }
        }

        return null;
    }

    public List<Integer> quickSort(List<Integer> sortList) {
        var pointer = sortList.get(0);
        sortList.remove(0);

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        fillLeftAndRight(sortList, pointer, left, right);

        if (left.size() > 1) {
            left = quickSort(left);
        }
        if (right.size() > 1) {
            right = quickSort(right);
        }

        left.add(pointer);
        left.addAll(right);

        return left;
    }

    private static void fillLeftAndRight(List<Integer> sortList, Integer pointer, List<Integer> left, List<Integer> right) {
        for (int num : sortList) {
            if (num <= pointer) {
                left.add(num);
            } else {
                right.add(num);
            }
        }
    }
}