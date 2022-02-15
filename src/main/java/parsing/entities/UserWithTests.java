package parsing.entities;

import api.calls.entities.Holder;

import java.util.List;

public class UserWithTests {

    private User user;

    private List<Holder> tests;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Holder> getTests() {
        return tests;
    }

    public void setTests(List<Holder> tests) {
        this.tests = tests;
    }

    private UserWithTests(User user, List<Holder> testList) {
        this.tests = testList;
        this.user = user;
    }

    public static UserWithTests of(User user, List<Holder> tests) {
        return new UserWithTests(user, tests);
    }
}
