package parsing.relations;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class TestUserRelation extends PanacheEntity {

    private long testId;

    private long userId;

    public TestUserRelation() {

    }

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    private TestUserRelation(long testId, long userId) {
        this.testId = testId;
        this.userId = userId;
        this.persist();
    }

    public static TestUserRelation of(long testId, long userId) {
        return new TestUserRelation(testId, userId);
    }
}
