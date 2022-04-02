package parsing.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;
import parsing.relations.TestName;

import javax.persistence.*;

@Entity
@Cacheable
public class LocalTest extends PanacheEntity {

    private LocalTest(User user, TestName testName, float attempts) {
        this.userId= user.id;
        this.testNameId = testName.id;
        this.attempts = attempts;
    }

    private LocalTest(String name) {
        if (null == TestName.find("name", name).firstResult()) {
            TestName testName = new TestName();
            testName.setName(name);
            testName.persist();
            this.testNameId = testName.id;
        }
        else {
            TestName aName = TestName.find("name", name).firstResult();
            this.testNameId = aName.id;
        }
    }

    public static LocalTest localTestOf(User user, TestName testName, float attempts) {
        return new LocalTest(user, testName, attempts);
    }

    public static LocalTest ofName(String name) {
       return new LocalTest(name);
    }

    @Column(name = "user_id")
    public long userId;

    @Column(name = "test_name_id")
    public long testNameId;

    @Column(name = "assignment_id")
    public long assignmentId;

    @Enumerated(EnumType.STRING)
    public TestStatus status;

    private float pointsGained;

    private float pointsTotal;

    private float attempts;

    public LocalTest() {

    }

    public void setName(TestName name) {
        if (TestName.find("name", name.getName()) == null) {
            name.persist();
        }
        this.assignmentId = name.id;
    }

    public float getPointsGained() {
        return pointsGained;
    }

    public void setPointsGained(float pointsGained) {
        this.pointsGained = pointsGained;
    }

    public float getPointsTotal() {
        return pointsTotal;
    }

    public void setPointsTotal(float pointsTotal) {
        this.pointsTotal = pointsTotal;
    }

    public float getAttempts() {
        return attempts;
    }

    public void setAttempts(float attempts) {
        this.attempts = attempts;
    }

    public TestStatus getStatus() {
        return status;
    }

    public void setStatus(String value) {
        this.status = TestStatus.fromString(value);
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setTestNameId(long testNameId) {
        this.testNameId = testNameId;
    }

    public void setAssignmentId(long assignmentId) {
        this.assignmentId = assignmentId;
        TestNameWithAssignment testNameWithAssignment = TestNameWithAssignment.find("testNameId = :testNameId and assignmentId = : assignmentId",
                Parameters.with("testNameId", this.testNameId).and("assignmentId", assignmentId).map()).firstResult();
        if (!(testNameWithAssignment == null)) {
            testNameWithAssignment.persist();
        }
        else {
            TestNameWithAssignment aTestNameWithAssignment = new TestNameWithAssignment();
            aTestNameWithAssignment.setTestNameId(this.testNameId);
            aTestNameWithAssignment.setAssignmentId(this.assignmentId);
            aTestNameWithAssignment.persistAndFlush();
        }
    }

    public void setStatus(TestStatus status) {
        this.status = status;
    }

    public long getUserId() {
        return userId;
    }

    public long getTestNameId() {
        return testNameId;
    }

    public String getName() {
        TestName testName = TestName.findById(this.testNameId);
        return testName.getName();
    }

    public long getAssignmentId() {
        return assignmentId;
    }

    @Override
    public String toString() {
        return "LocalTest{" +
                "userId=" + userId +
                ", testNameId=" + testNameId +
                ", assignmentId=" + assignmentId +
                ", status=" + status +
                ", pointsGained=" + pointsGained +
                ", pointsTotal=" + pointsTotal +
                ", attempts=" + attempts +
                ", id=" + id +
                '}';
    }
}
