package parsing.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class TestScore extends PanacheEntity {

    private long score;

    private long maxAllowedScore;

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getMaxAllowedScore() {
        return maxAllowedScore;
    }

    public void setMaxAllowedScore(long maxAllowedScore) {
        this.maxAllowedScore = maxAllowedScore;
    }
}
