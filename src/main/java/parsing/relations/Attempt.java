package parsing.relations;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Attempt extends PanacheEntity {

    private float attempts;

    public Attempt() {

    }

    public float getAttempts() {
        return attempts;
    }

    public void setAttempts(float attempts) {
        this.attempts = attempts;
    }

    private Attempt(float attempt) {
        this.attempts = attempt;
    }

    public static Attempt of(float attempt) {
        Attempt anAttempt = Attempt.find("attempts", attempt).firstResult();
        if (anAttempt == null) {
            Attempt aNewAttempt = new Attempt(attempt);
            aNewAttempt.persist();
            return aNewAttempt;
        }
        return anAttempt;
    }
}
