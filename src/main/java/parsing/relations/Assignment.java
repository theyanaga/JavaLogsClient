package parsing.relations;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Assignment extends PanacheEntity {

    private int number;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    @Column(name="max_score")
    private float maxScore;



    public Assignment(int aNumber, Course aCourse) {
        this.number = aNumber;
        setCourse(aCourse);
        this.persist();
    }

    public static Assignment of (int aNumber, Course aCourse) {
        Assignment assignment = Assignment.find("number = ?1 and course_id = ?2", aNumber, aCourse.id).firstResult();
        return Objects.requireNonNullElseGet(assignment, () -> new Assignment(aNumber, aCourse));
    }

    public void setCourse(Course course) {
        if (!course.isPersistent()) {
            course.persist();
        }
        this.course = course;
    }

    public int getNumber() {
        return number;
    }

    public Course getCourse() {
        return course;
    }

    public float getMaxScore() {
        return maxScore;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Assignment() {

    }
}
