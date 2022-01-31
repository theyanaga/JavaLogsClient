package parsing.entities;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum TestStatus {
    PASS("Pass"),
    FAIL("Fail"),
    PARTIAL("Partial"),
    UNTESTED("Untested");

    private static final Map<String, TestStatus> VALUES = Arrays.stream(values()).collect(
            Collectors.toUnmodifiableMap(o -> o.value, o -> o)
    );

    private final String value;

    private TestStatus(String val) {
        this.value = val;
    }

    public String getValue() {
        return value;
    }

    public static TestStatus fromString(String value) {
        return VALUES.get(value);
    }


}
