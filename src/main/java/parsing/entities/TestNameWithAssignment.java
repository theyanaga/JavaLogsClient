package parsing.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class TestNameWithAssignment extends PanacheEntity {

    private long testNameId;

    private long assignmentId;

    public long getTestNameId() {
        return testNameId;
    }

    public void setTestNameId(long testNameId) {
        this.testNameId = testNameId;
    }

    public long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(long assignmentId) {
        this.assignmentId = assignmentId;
    }

}
