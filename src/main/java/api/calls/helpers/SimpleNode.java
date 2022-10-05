package api.calls.helpers;

import parsing.entities.LocalTest;
import parsing.entities.projections.UserId;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class SimpleNode {

    private List<Long> testIds = new ArrayList<>();

    private List<Long> users = new ArrayList<>();

    private int collisions = 0;

    private int sessionId;

    private ZonedDateTime time;

    public void addTestId(LocalTest test) {
        testIds.add(test.getTestNameId());
    }

    public SimpleNode(int sessionId, ZonedDateTime time) {
        this.sessionId = sessionId;
        this.time = time;
    }

    public List<Long> getTestIds() {
        return testIds;
    }

    public void setTestIds(List<Long> testIds) {
        this.testIds = testIds;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void addCollision(Long userId) {
        if (!users.contains(userId)) {
            this.users.add(userId);
            collisions++;
        }
    }

    public int getCollisions() {
        return collisions;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }
}
