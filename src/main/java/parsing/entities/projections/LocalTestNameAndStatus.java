package parsing.entities.projections;

import io.quarkus.runtime.annotations.RegisterForReflection;
import parsing.entities.TestStatus;

@RegisterForReflection
public class LocalTestNameAndStatus {
    private final long testNameId;
    private final TestStatus status;

    public LocalTestNameAndStatus(long testNameId, TestStatus status) {
        this.testNameId = testNameId;
        this.status = status;
    }

    public long getTestNameId() {
        return testNameId;
    }

    public TestStatus getStatus() {
        return status;
    }
}
