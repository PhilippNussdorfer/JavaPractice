package bbrez.at.NewSpringBootTryWithInjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class PresenceService {

    @Autowired
    private StudentNameRepo repo;

    public List<String> precedeAre(int max) {
        Random random = new Random();
        int amount = random.nextInt(max);
        while (amount <= 0) {
            amount = random.nextInt(max);
        }
        return repo.studentName("1B", amount);
    }
}
