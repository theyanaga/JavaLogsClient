package org.acme.logs.parsing.entities;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TestRun {

    private final AssignmentTestManager manager;

    //Make a builder out of this
    private TestRun(int sessionId, int runId, boolean isSuite, String time, String passedTestsString,
                    String failedTestsString, String partialTestsString, String untestedTests, String testScores,
                    AssignmentTestManager aManager) {
        this.sessionId = sessionId;
        this.isSuite = isSuite;
        this.manager = aManager;
        this.runId = runId;

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy");
        this.time = ZonedDateTime.parse(time, dateTimeFormatter);

        this.passedTests = parseTestString(passedTestsString);
        this.failedTests = parseTestString(failedTestsString);
        this.partialTests = parseTestString(partialTestsString);
        this.untestedTests = parseTestString(untestedTests);
        this.testScores = parseTestScoreString(testScores);

    }

    public static TestRun ofLine(int sessionId, int runId, boolean isSuite, String time, String passedTestsString,
                                 String failedTestsString, String partialTestsString, String untestedTests,
                                 String testScores, AssignmentTestManager manager) {
        return new TestRun(sessionId, runId, isSuite, time,
                passedTestsString, failedTestsString,
                partialTestsString, untestedTests,
                testScores, manager);
    }

    private int sessionId;

    private int runId;

    private float points;

    private float totalPoints;

    private boolean isSuite;

    // Should this be the time of the first test, or the time of the last?
    private ZonedDateTime time;

    // For scores, just sum the passed tests
    private List<LocalChecksTest> passedTests;

    private List<LocalChecksTest> failedTests;

    private List<LocalChecksTest> partialTests;

    private List<LocalChecksTest> untestedTests;

    private List<LocalChecksTest> testScores;

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isSuite() {
        return isSuite;
    }

    public void setSuite(boolean suite) {
        isSuite = suite;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public List<LocalChecksTest> getPassedTests() {
        return passedTests;
    }

    public void setPassedTests(List<LocalChecksTest> passedTests) {
        this.passedTests = passedTests;
    }

    public List<LocalChecksTest> getFailedTests() {
        return failedTests;
    }

    public void setFailedTests(List<LocalChecksTest> failedTests) {
        this.failedTests = failedTests;
    }

    public List<LocalChecksTest> getPartialTests() {
        return partialTests;
    }

    public void setPartialTests(List<LocalChecksTest> partialTests) {
        this.partialTests = partialTests;
    }

    public List<LocalChecksTest> getUntestedTests() {
        return untestedTests;
    }

    public void setUntestedTests(List<LocalChecksTest> untestedTests) {
        this.untestedTests = untestedTests;
    }

    public float getPoints() {
        return this.points;
    }

    public float getTotalPoints() {
        return this.totalPoints;
    }

    public int getRunId() {
        return this.runId;
    }

    private List<LocalChecksTest> parseTestString(String testString) {
        String[] splittedTests = testString.split(" ");
        List<LocalChecksTest> tests = new ArrayList<>();
        for (String test : splittedTests) {
            tests.add(LocalChecksTest.ofName(test));
            addTestName(test);
        }

        return tests;
    }

    private List<LocalChecksTest> parseTestScoreString(String testScoresString) {
        String[] splittedTests = testScoresString.split(" ");
        List<LocalChecksTest> tests = new ArrayList<>();
        for (String test : splittedTests) {
            String[] testAndScore = test.split("-");
            String[] testScores = testAndScore[1].replaceAll("\\(", "")
                    .replaceAll("\\)","").split("/");
            float testPoints = Float.parseFloat(testScores[0]);
            float testTotalPoints = Float.parseFloat(testScores[1]);
            this.points = this.points + testPoints;
            this.totalPoints = this.totalPoints + testTotalPoints;
            putScoreInTest(testAndScore[0], testPoints, testTotalPoints);
        }

        return tests;
    }

    private void addTestName(String name) {
        manager.addNewTestName(name, this.sessionId);
    }

    private void putScoreInTest(String testName, float points, float totalPoints) {
        putScoreInTest(testName, points, totalPoints, this.passedTests);
        putScoreInTest(testName, points, totalPoints, this.failedTests);
        putScoreInTest(testName, points, totalPoints, this.partialTests);
    }

    private void putScoreInTest(String testName, float points, float totalPoints, List<LocalChecksTest> tests) {
        for (LocalChecksTest test : tests) {
            if (test.getName().equalsIgnoreCase(testName)) {
                test.setPoints(points);
                test.setTotalPoints(totalPoints);
            }
        }
    }

    @Override
    public String toString() {
        return "TestRun{" +
                "manager=" + manager +
                ", sessionId=" + sessionId +
                ", runId=" + runId +
                ", points=" + points +
                ", totalPoints=" + totalPoints +
                ", isSuite=" + isSuite +
                ", time=" + time +
                ", passedTests=" + passedTests +
                ", failedTests=" + failedTests +
                ", partialTests=" + partialTests +
                ", untestedTests=" + untestedTests +
                ", testScores=" + testScores +
                '}';
    }
}

