package parsing.relations;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class TestAssignmentRelation extends PanacheEntity {

    private long testId;

    private long assignmentId;

    public TestAssignmentRelation() {

    }

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    public long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(long assignmentId) {
        this.assignmentId = assignmentId;
    }

    private TestAssignmentRelation(long testId, long assignmentId) {
        this.testId = testId;
        this.assignmentId = assignmentId;
        this.persist();
    }

    public static TestAssignmentRelation of(long testId, long assignmentId) {
        return new TestAssignmentRelation(testId, assignmentId);
    }
}
