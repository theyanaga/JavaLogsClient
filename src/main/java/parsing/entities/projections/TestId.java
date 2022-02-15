package parsing.entities.projections;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.Objects;

@RegisterForReflection
public class TestId {
    private final Long testId;

    public TestId(Long testId) {
        this.testId = testId;
    }

    public Long getTestId() {
        return testId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestId testId1 = (TestId) o;
        return testId.equals(testId1.testId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testId);
    }
}
