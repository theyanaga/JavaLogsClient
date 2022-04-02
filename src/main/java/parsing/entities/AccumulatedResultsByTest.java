package parsing.entities;

import java.util.ArrayList;
import java.util.List;

public class AccumulatedResultsByTest {

    private AccumulatedResultsByTest(String name) {
        this.name = name;
    }

    public static AccumulatedResultsByTest of(String name) {
        return new AccumulatedResultsByTest(name);
    }

    private String name;

    private List<User> usersThatPassed = new ArrayList<>();

    private long numberOfUsersThatPassed;

    private List<User> usersThatFailed = new ArrayList<>();

    private long numberOfUsersThatFailed;

    private List<User> usersThatPartiallyPassed = new ArrayList<>();

    private long numberOfUsersThatPartiallyPassed;

    public void setUsersThatPassed(List<User> usersThatPassed) {
        this.usersThatPassed = usersThatPassed;
    }

    public void setUsersThatFailed(List<User> usersThatFailed) {
        this.usersThatFailed = usersThatFailed;
    }

    public void setUsersThatPartiallyPassed(List<User> usersThatPartiallyPassed) {
        this.usersThatPartiallyPassed = usersThatPartiallyPassed;
    }

    public void setUsersThatDidNotRunTest(List<User> usersThatDidNotRunTest) {
        this.usersThatDidNotRunTest = usersThatDidNotRunTest;
    }

    private List<User> usersThatDidNotRunTest = new ArrayList<>();

    private long numberOfUsersThatDidNotRunTest;

    public void insertUserTestResult(TestStatus status, User user) {
        switch (status) {
            case PASS:
                this.usersThatPassed.add(user);
                this.numberOfUsersThatPassed++;
                break;
            case PARTIAL:
                this.usersThatPartiallyPassed.add(user);
                this.numberOfUsersThatPartiallyPassed++;
                break;
            case FAIL:
                this.usersThatFailed.add(user);
                this.numberOfUsersThatFailed++;
                break;
            case UNTESTED:
                this.usersThatDidNotRunTest.add(user);
                this.numberOfUsersThatDidNotRunTest++;
                break;
        }
    }

    public long getNumberOfUsersThatPassed() {
        return numberOfUsersThatPassed;
    }

    public long getNumberOfUsersThatFailed() {
        return numberOfUsersThatFailed;
    }

    public long getNumberOfUsersThatPartiallyPassed() {
        return numberOfUsersThatPartiallyPassed;
    }

    public long getNumberOfUsersThatDidNotRunTest() {
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

    public void setNumberOfUsersThatPassed(long numberOfUsersThatPassed) {
        this.numberOfUsersThatPassed = numberOfUsersThatPassed;
    }

    public void setNumberOfUsersThatFailed(long numberOfUsersThatFailed) {
        this.numberOfUsersThatFailed = numberOfUsersThatFailed;
    }

    public void setNumberOfUsersThatPartiallyPassed(long numberOfUsersThatPartiallyPassed) {
        this.numberOfUsersThatPartiallyPassed = numberOfUsersThatPartiallyPassed;
    }

    public void setNumberOfUsersThatDidNotRunTest(long numberOfUsersThatDidNotRunTest) {
        this.numberOfUsersThatDidNotRunTest = numberOfUsersThatDidNotRunTest;
    }
}
