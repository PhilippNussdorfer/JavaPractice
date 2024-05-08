import java.util.ArrayList;
import java.util.List;

public class EuklidischerAlgorithmus {

    public static void main(String[] args) {
        System.out.println(ggt(224, 376));
        System.out.println(kgv(224, 376));
        System.out.println(kgv(200, 360));
    }

    private static int ggt(int firstNum, int secNum) {
        int biggest = Math.max(firstNum, secNum);
        int smallest = Math.min(firstNum, secNum);

        List<Integer> rest = new ArrayList<>();
        rest.add(biggest);
        rest.add(smallest);

        int count = 1;
        for (int i = 0; ; i++) {
            int first = rest.get(i);
            int sec = rest.get(i + 1);

            while (first - (sec * count) > 0) {
                if (first - (sec * (count + 1)) == 0)
                    return sec;
                if (first - (sec * (count + 1)) < 0) {
                    rest.add(first - (sec * count));
                    count = 1;
                    break;
                } else {
                    count ++;
                }
            }
        }
    }

    private static int kgv(int firstNum, int secNum) {
        int res = ggt(firstNum, secNum);
        return (firstNum * secNum) / res;
    }
}
