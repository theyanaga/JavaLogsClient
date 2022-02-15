package parsing.entities.projections;

import io.quarkus.runtime.annotations.RegisterForReflection;
import parsing.entities.TestStatus;

@RegisterForReflection
public class LocalTestNameAndStatus {
    public final long testNameId;
    public final TestStatus status;

    public LocalTestNameAndStatus(long testNameId, TestStatus status) {
        this.testNameId = testNameId;
        this.status = status;
    }
}
