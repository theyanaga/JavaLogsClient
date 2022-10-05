package parsing.entities;

public class SimpleUser {

    private String firstName;
    private String lastName;

    public SimpleUser(User user) {
        this.firstName = user.firstName;
        this.lastName = user.lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
