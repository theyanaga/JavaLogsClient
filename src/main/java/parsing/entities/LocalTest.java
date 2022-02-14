package parsing.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;

@Entity
@Cacheable
public class LocalTest extends PanacheEntity {

    private LocalTest(User user, TestName testName, float pointsGained, float pointsTotal, float attempts) {
        this.user_id = user;
        this.name = testName;
        this.pointsGained = pointsGained;
        this.pointsTotal = pointsTotal;
        this.attempts = attempts;
    }

    private LocalTest(String name) {
        if (null == TestName.find("name", name).firstResult()) {
            TestName testName = new TestName();
            testName.setName(name);
            testName.persist();
            this.name = testName;
        }
        else {
            this.name = TestName.find("name", name).firstResult();
        }
    }

    public static LocalTest localTestOf(User user, TestName testName, float pointsGained, float pointsTotal, float attempts) {
        return new LocalTest(user, testName, pointsGained, pointsTotal, attempts);
    }

    public static LocalTest ofName(String name) {
       return new LocalTest(name);
    }



    @ManyToOne
    @JoinColumn(name="user_id")
    public User user_id;

    @ManyToOne
    @JoinColumn(name="test_name_id")
    public TestName name;

    @ManyToOne
    @JoinColumn(name="assignment_id")
    public Assignment assignment;

    @Enumerated(EnumType.STRING)
    public TestStatus status;

    private float pointsGained;

    private float pointsTotal;

    private float attempts;

    public LocalTest() {

    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name.getName();
    }

    public void setName(TestName name) {
        if (TestName.find("name", name.getName()) == null) {
            name.persist();
        }
        this.name = name;
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

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public TestStatus getStatus() {
        return status;
    }

    public void setStatus(String value) {
        this.status = TestStatus.fromString(value);
    }
}
