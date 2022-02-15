package parsing.entities.projections;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class UserId {
    private final long userId;

    public UserId(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }
}
