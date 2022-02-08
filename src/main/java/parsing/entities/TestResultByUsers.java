package parsing.entities;

import java.util.ArrayList;
import java.util.List;

public class TestResultByUsers {

    private TestResultByUsers(String name) {
        this.name = name;
    }

    public static TestResultByUsers of(String name) {
        return new TestResultByUsers(name);
    }

    private String name;

    private final List<User> usersThatPassed = new ArrayList<>();

    private int numberOfUsersThatPassed;

    private final List<User> usersThatFailed = new ArrayList<>();

    private int numberOfUsersThatFailed;

    private final List<User> usersThatPartiallyPassed = new ArrayList<>();

    private int numberOfUsersThatPartiallyPassed;

    private final List<User> usersThatDidNotRunTest = new ArrayList<>();

    private int numberOfUsersThatDidNotRunTest;

    public void insertUserTestResult(LocalChecksTest test, User user) {
        switch (test.getStatus()) {
            case PASS:
                this.usersThatPassed.add(user);
                this.numberOfUsersThatPassed++;
            case PARTIAL:
                this.usersThatPartiallyPassed.add(user);
                this.numberOfUsersThatPartiallyPassed++;
            case FAIL:
                this.usersThatFailed.add(user);
                this.numberOfUsersThatFailed++;
            case UNTESTED:
                this.usersThatDidNotRunTest.add(user);
                this.numberOfUsersThatDidNotRunTest++;
        }
    }

    public int getNumberOfUsersThatPassed() {
        return numberOfUsersThatPassed;
    }

    public int getNumberOfUsersThatFailed() {
        return numberOfUsersThatFailed;
    }

    public int getNumberOfUsersThatPartiallyPassed() {
        return numberOfUsersThatPartiallyPassed;
    }

    public int getNumberOfUsersThatDidNotRunTest() {
        return numberOfUsersThatDidNotRunTest;
    }

    public List<User> getUsersThatPassed() {
        return usersThatPassed;
    }

    public List<User> getUsersThatFailed() {
        return usersThatFailed;
    }

    public List<User> getUsersThatPartiallyPassed() {
        return usersThatPartiallyPassed;
    }

    public List<User> getUsersThatDidNotRunTest() {
        return usersThatDidNotRunTest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
