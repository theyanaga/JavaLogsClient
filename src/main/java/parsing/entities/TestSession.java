package parsing.entities;

import java.util.ArrayList;
import java.util.List;

public class TestSession {

    private int sessionId;

    private int timesRan;

    private float totalSessionPoints;

    private float sessionPoints;

    private List<String> allTestNames = new ArrayList<>();

    private List<TestRun> testRuns = new ArrayList<>();

    private TestSession(int sessionId) {
        this.sessionId = sessionId;
    }

    public static TestSession ofId(int sessionId) {
        return new TestSession(sessionId);
    }

    public void addTestRun(TestRun testRun) {
        if (!isRepeated(testRun)) {
            testRuns.add(testRun);
            this.sessionPoints = this.sessionPoints + testRun.getPoints();
            this.totalSessionPoints = this.totalSessionPoints + testRun.getTotalPoints();
        }
    }

    public boolean isRepeated(TestRun testRun) {
        for (TestRun run : this.testRuns) {
            if (run.getRunId() == testRun.getRunId() && run.getSessionId() == testRun.getSessionId()) {
                return true;
            }
        }
        return false;
    }

    public void setTimesRan(int timesRan) {
        if (timesRan > this.timesRan) {
            this.timesRan = timesRan;
        }
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public float getTotalSessionPoints() {
        return totalSessionPoints;
    }

    public void setTotalSessionPoints(float totalSessionPoints) {
        this.totalSessionPoints = totalSessionPoints;
    }

    public float getSessionPoints() {
        return sessionPoints;
    }

    public void setSessionPoints(float sessionPoints) {
        this.sessionPoints = sessionPoints;
    }

    public List<TestRun> getTestRuns() {
        return testRuns;
    }

    public void setTestRuns(List<TestRun> testRuns) {
        this.testRuns = testRuns;
    }

    public List<String> getAllTestNames() {
        return allTestNames;
    }

    public void setAllTestNames(List<String> allTestNames) {
        this.allTestNames = allTestNames;
    }

    @Override
    public String toString() {
        return "TestSession{" +
                "sessionId=" + sessionId +
                ", timesRan=" + timesRan +
                ", totalSessionPoints=" + totalSessionPoints +
                ", sessionPoints=" + sessionPoints +
                ", allTestNames=" + allTestNames +
                ", testRuns=" + testRuns +
                '}';
    }
}
