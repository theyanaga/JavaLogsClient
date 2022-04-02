package graph.entities;

import parsing.entities.TestStatus;

public class UserBarGraphEntity {

    private String testName;

    private long countOfPassed;

    private long countOfPartiallyPassed;

    private long countOfFailed;

    private long countOfUntested;

    private UserBarGraphEntity(String testName, long countOfPassed, long countOfPartiallyPassed, long countOfFailed, long countOfUntested) {
        this.testName = testName;
        this.countOfPassed = countOfPassed;
        this.countOfPartiallyPassed = countOfPartiallyPassed;
        this.countOfFailed = countOfFailed;
        this.countOfUntested = countOfUntested;
    }

    public static UserBarGraphEntity of(String testName, long countOfPassed, long countOfPartiallyPassed, long countOfFailed, long countOfUntested) {
        return new UserBarGraphEntity(testName, countOfPassed, countOfPartiallyPassed, countOfFailed, countOfUntested);
    }

    public String getTestName() {
        return testName;
    }

    private void addPassed() {
        this.countOfPassed++;
    }

    private void addFailed() {
        this.countOfFailed++;
    }

    private void addPartial() {
        this.countOfPartiallyPassed++;
    }

    private void addUntested() {
        this.countOfUntested++;
    }

    public void addTestResult(TestStatus status) {
        if (status == TestStatus.PASS) {
            this.addPassed();
        }
        else if (status == TestStatus.PARTIAL) {
            this.addPartial();
        }
        else if (status == TestStatus.FAIL) {
            this.addFailed();
        }
        else {
            this.addUntested();
        }
    }

    public long getCountOfPassed() {
        return countOfPassed;
    }

    public long getCountOfPartiallyPassed() {
        return countOfPartiallyPassed;
    }

    public long getCountOfFailed() {
        return countOfFailed;
    }

    public long getCountOfUntested() {
        return countOfUntested;
    }
}
