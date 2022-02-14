package parsing.entities;

import java.util.List;

public class UserWithTests {

    private User user;

    private List<LocalTest> tests;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<LocalTest> getTests() {
        return tests;
    }

    public void setTests(List<LocalTest> tests) {
        this.tests = tests;
    }

    private UserWithTests(User user, List<LocalTest> testList) {
        this.tests = testList;
        this.user = user;
    }

    public static UserWithTests of(User user, List<LocalTest> tests) {
        return new UserWithTests(user, tests);
    }
}
