package parsing.entities.projections;

import io.quarkus.runtime.annotations.RegisterForReflection;
import parsing.entities.TestStatus;

@RegisterForReflection
public class LocalTestNameAndStatusWithUserId {

    private final long userId;

    private final long testNameId;

    private final TestStatus status;

    public LocalTestNameAndStatusWithUserId(long userId, long testNameId, TestStatus status) {
        this.userId = userId;
        this.testNameId = testNameId;
        this.status = status;
    }

    public long getUserId() {
        return userId;
    }

    public long getTestNameId() {
        return testNameId;
    }

    public TestStatus getStatus() {
        return status;
    }
}
