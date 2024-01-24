package bbrez.at.NewSpringBootTryWithInjection;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GiveGrades {

    public Result res(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cant be null");
        } if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cant be null");
        }
        Random rand = new Random();
        return Result.builder().studentName(name).result(rand.nextInt(5) + 1).build();
    }
}
