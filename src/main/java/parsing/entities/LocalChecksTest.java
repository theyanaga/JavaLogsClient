package parsing.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import gradingTools.logs.LocalChecksLogData;

import java.util.Objects;

public class LocalChecksTest {

    private LocalChecksTest(String name) {
        this.name = name;
        this.hasPoints = false;
    }

    private LocalChecksTest(String name, float points, float totalPoints) {
        this.name = name;
        this.points = points;
        this.totalPoints = totalPoints;
        this.hasPoints = true;
    }

    public static LocalChecksTest ofName(String name) {
        return new LocalChecksTest(name);
    }

    public static LocalChecksTest of(String name, float point, float totalPoints) {
        return new LocalChecksTest(name);
    }

    public boolean isSameTest(String input) {
        return input.contains(this.name);
    }

    private float attempts;

    private boolean hasPoints;

    private String name;

    private float points;

    private float totalPoints;

    private TestStatus status;

    public TestStatus getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = TestStatus.fromString(status);
    }

    public float getAttempts() {
        return attempts;
    }

    public void setAttempts(float attempts) {
        this.attempts = attempts;
    }

    public boolean isHasPoints() {
        return hasPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = points;
    }

    public float getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(float totalPoints) {
        this.totalPoints = totalPoints;
    }

    @Override
    public String toString() {
        return "LocalChecksTest{" +
                "attempts=" + attempts +
                ", hasPoints=" + hasPoints +
                ", name='" + name + '\'' +
                ", points=" + points +
                ", totalPoints=" + totalPoints +
                '}';
    }

}
