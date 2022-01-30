package api.calls.entities;

public enum CSVIndexes {
    ROW(0),
    TIME(1),
    PERCENT_PASSES(2),
    CHANGE(3),
    TEST_NAME(4),
    PASSED_TESTS(5),
    PARTIAL_TESTS(6),
    FAILED_TESTS(7),
    UNTESTED_TESTS(8),
    SESSION_NUMBER(9),
    SESSION_RUN_NUMBER(10),
    IS_SUITE(11),
    SUITE_TESTS(12),
    PREREQUISITE_TESTS(13),
    EXTRA_CREDIT_TESTS(14),
    TEST_SCORES(15),
    FAIL_FROM_PREREQUISITE(16);

    private CSVIndexes(int anIndex) {
        this.index = anIndex;
    }

    public int getIndex() {
        return this.index;
    }

    private int index;
}
