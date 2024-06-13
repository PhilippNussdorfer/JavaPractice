package Bank.person;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class User {
    private List<Person> users;

    public void addUser(Person user) {
        users.add(user);
    }
}
