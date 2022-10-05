package parsing.entities;

import java.util.ArrayList;
import java.util.List;

public class UserSummary {

    private SimpleUser user;

    private List<TestSummary> tests = new ArrayList<>();

    private TimeSummary timeSummary;

    private float userScore;


    public float getUserScore() {
        return userScore;
    }

    public UserSummary(User user, List<LocalTest> tests, TimeSummary timeSummary) {
        this.timeSummary = timeSummary;
        this.user = new SimpleUser(user);
        for (LocalTest test : tests) {
            userScore = userScore + test.getPointsGained();
            TestSummary testSummary = new TestSummary(test.getName(), test);
            this.tests.add(testSummary);
        }
    }

    public SimpleUser getUser() {
        return user;
    }

    public void setUser(SimpleUser user) {
        this.user = user;
    }

    public List<TestSummary> getTests() {
        return tests;
    }

    public TimeSummary getTimeSummary() {
        return timeSummary;
    }

    public void setTests(List<TestSummary> tests) {
        this.tests = tests;
    }
}
