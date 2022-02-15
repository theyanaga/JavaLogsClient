package parsing.relations;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class TestAttemptsRelation extends PanacheEntity {

    private long testId;

    private float attemptId;

    public TestAttemptsRelation() {

    }

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    public float getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(float attempt) {
        this.attemptId = attempt;
    }

    private TestAttemptsRelation(long testId, long attemptId) {
        this.testId = testId;
        this.attemptId = attemptId;
        this.persist();
    }

    public static TestAttemptsRelation of(long testId, long attemptId) {
        return new TestAttemptsRelation(testId, attemptId);
    }


}
