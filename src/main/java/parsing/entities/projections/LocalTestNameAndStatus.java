package parsing.entities.projections;

import io.quarkus.runtime.annotations.RegisterForReflection;
import parsing.entities.TestStatus;

import javax.persistence.*;

@RegisterForReflection
public class LocalTestNameAndStatus {
    private  long testNameId;
    private  TestStatus status;

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

    public void setTestNameId(long testNameId) {
        this.testNameId = testNameId;
    }

    public void setStatus(TestStatus status) {
        this.status = status;
    }
}
