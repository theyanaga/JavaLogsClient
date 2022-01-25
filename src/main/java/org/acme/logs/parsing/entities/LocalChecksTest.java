package org.acme.logs.parsing.entities;

import java.time.LocalDate;

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

    private boolean hasPoints;

    private String name;

    private float points;

    private float totalPoints;

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
}
