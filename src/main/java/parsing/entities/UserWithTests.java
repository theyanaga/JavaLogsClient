package parsing.entities;

import java.util.List;

public class UserWithTests {

    private User user;

    private List<LocalChecksTest> tests;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<LocalChecksTest> getTests() {
        return tests;
    }

    public void setTests(List<LocalChecksTest> tests) {
        this.tests = tests;
    }

    private UserWithTests(User user, List<LocalChecksTest> testList) {
        this.tests = testList;
        this.user = user;
    }

    public static UserWithTests of(User user, List<LocalChecksTest> tests) {
        return new UserWithTests(user, tests);
    }
}
