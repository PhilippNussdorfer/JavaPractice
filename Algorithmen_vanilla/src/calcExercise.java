import java.util.ArrayList;
import java.util.List;

public class calcExercise {
    public static void main(String[] args) {
        List<List<Integer>> arr = new ArrayList<>();

        int x = 0, y = 0;

        arr.add(new ArrayList<>(List.of(1,2,3)));
        arr.add(new ArrayList<>(List.of(4,5,6)));
        arr.add(new ArrayList<>(List.of(9,8,9)));

        int first = 0;
        int sec = 0;

        for (List<Integer> row : arr) {
            first += row.get(x);
            x ++;
        }

        for (List<Integer> row : arr) {
            x --;
            sec += row.get(x);
        }

        System.out.println(Math.abs(first - sec));
    }
}
