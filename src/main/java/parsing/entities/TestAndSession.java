package parsing.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import parsing.entities.projections.LocalTestNameAndStatus;

import javax.persistence.*;

@Entity
public class TestAndSession extends PanacheEntity {

    private long sessionId;

    private long testId;

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }
}
