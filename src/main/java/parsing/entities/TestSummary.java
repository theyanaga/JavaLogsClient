package parsing.entities;

public class TestSummary {

    private String name;
    private float attempts;
    private TestStatus status;

    public TestSummary(String name, LocalTest test) {
        this.name = name;
        this.attempts = test.getAttempts();
        this.status = test.status;
    }

    public String getName() {
        return name;
    }

    public float getAttempts() {
        return attempts;
    }

    public TestStatus getStatus() {
        return status;
    }
}
