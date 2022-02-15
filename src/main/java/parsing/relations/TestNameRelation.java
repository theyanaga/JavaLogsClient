package parsing.relations;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.persistence.Entity;

@Entity
public class TestNameRelation extends PanacheEntity {

    private long testNameId;

    private long testId;

    public TestNameRelation() {

    }

    public long getTestNameId() {
        return testNameId;
    }

    public void setTestNameId(long testNameId) {
        this.testNameId = testNameId;
    }

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    private TestNameRelation(long testId, long testNameId) {
        this.testId = testId;
        this.testNameId = testNameId;
        this.persist();
    }

    public static TestNameRelation of (long testId, long testNameId) {
        return new TestNameRelation(testId, testNameId);
    }
}
