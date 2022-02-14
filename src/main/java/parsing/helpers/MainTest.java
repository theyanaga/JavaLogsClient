package parsing.helpers;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import parsing.entities.User;

import javax.transaction.Transactional;
import java.util.List;

public class MainTest {

    @Transactional
    public static void main(String[] args) {
        List<User> users = User.listAll();

        for (User user : users) {
            Faker faker = new Faker();
            Name name = faker.name();

            if ((User.find("firstName", name.firstName()) == null) ||
                    (User.find("lastName", name.lastName()) == null)) {
                user.setFirstName(name.firstName());
                user.setLastName(name.lastName());
            }
            else {
                while (!((User.find("firstName", name.firstName()) == null) ||
                        (User.find("lastName", name.lastName()) == null))) {
                    Faker anotherFaker = new Faker();
                    name = anotherFaker.name();
                    user.setFirstName(name.firstName());
                    user.setLastName(name.lastName());
                }
            }

            user.persist();
        }
    }
}
