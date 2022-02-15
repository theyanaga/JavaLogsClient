package graph.entities;

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
