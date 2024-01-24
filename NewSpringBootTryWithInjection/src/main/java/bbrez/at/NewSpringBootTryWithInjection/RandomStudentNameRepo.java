package bbrez.at.NewSpringBootTryWithInjection;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@Service
public class RandomStudentNameRepo implements StudentNameRepo{

    private final List<String> firstnames = List.of("Max", "Patrick", "Philipp", "Rene", "Anna", "Svenja", "Jennifer", "Pascal", "Andreas", "Michael", "Stefan", "Herbert");
    private final List<String> secondNames = List.of("Nussdorfer", "Nebenfuhr", "Fessler", "Leitner", "Hasenhütl", "Fasching", "Brieger", "Pichler", "Beham");
    @Override
    public List<String> studentName(String clas, int amount) {
        List<String> names = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < amount; i++) {
            String nameCombo = firstnames.get(rand.nextInt(firstnames.size())) + " - " + secondNames.get(rand.nextInt(secondNames.size()));
            names.add(nameCombo);
        }
        return names;
    }
}
