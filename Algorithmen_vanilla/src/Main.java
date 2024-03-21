import java.util.List;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();

        var out = main.getSumFromList(List.of(2, 45, 9, 7), 11);

        for (int i : out) {
            System.out.println(i);
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
}