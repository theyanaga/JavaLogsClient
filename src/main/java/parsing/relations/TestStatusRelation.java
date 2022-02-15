package parsing.relations;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import parsing.entities.TestStatus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class TestStatusRelation extends PanacheEntity {

    @Enumerated(EnumType.ORDINAL)
    private TestStatus status;

    private long testId;

    public TestStatusRelation() {

    }

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    public TestStatus getStatus() {
        return status;
    }

    public void setStatus(TestStatus status) {
        this.status = status;
    }

    private TestStatusRelation(long testId, TestStatus status) {
        this.status = status;
        this.testId = testId;
        this.persist();
    }

    public static TestStatusRelation of (long testId, TestStatus status) {
        return new TestStatusRelation(testId, status);
    }
}
