package parsing.entities;

import java.time.ZonedDateTime;
import java.util.List;

public class GraphNode {

    private List<LocalTest> tests;

    private int sessionId;

    private User user;

    private ZonedDateTime time;

    public GraphNode(List<LocalTest> localTests, int sessionId, User user, ZonedDateTime time) {
        this.tests = localTests;
        this.sessionId = sessionId;
        this.user = user;
        this.time = time;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

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

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
}
