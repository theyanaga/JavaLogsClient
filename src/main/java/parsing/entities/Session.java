package parsing.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;

import javax.persistence.Entity;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Objects;

@Entity
public class Session extends PanacheEntity {

    private int sessionId;

    private long userId;

    public Session() {

    }

    public Session(int sessionId, long userId) {
        this.sessionId = sessionId;
        this.userId = userId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public static Session getSession(int sessionId, long userId) {
        Session session = Session.find("sessionId = :sessionId and userId = :userId", Parameters.with("sessionId", sessionId).and("userId",userId).map()).firstResult();
        return Objects.requireNonNullElseGet(session, () -> new Session(sessionId, userId));
    }
}
