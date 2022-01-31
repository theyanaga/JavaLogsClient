package parsing.entities;

import api.calls.entities.CSVIndexes;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class RowFromServer extends PanacheEntity {

    private RowFromServer(Users users, Assignment assignment, String row, String time, String percentPassed, String change, String testName, String passedTests, String partialTests, String failedTests, String untestedTests, String sessionNumber, String sessionRunNumber, String isSuite, String suiteTests, String preRequisiteTests, String extraCreditTests, String testScores, String failFromPreReq) {
        this.users = users;
        this.assignment = assignment;
        this.row = row;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy");
        this.time = ZonedDateTime.parse(time, dateTimeFormatter);
        this.percentPassed = Integer.parseInt(percentPassed);
        this.change = Integer.parseInt(change);
        this.testName = testName;
        this.passedTests = passedTests;
        this.partialTests = partialTests;
        this.failedTests = failedTests;
        this.untestedTests = untestedTests;
        this.sessionNumber = Integer.parseInt(sessionNumber);
        this.sessionRunNumber = Integer.parseInt(sessionRunNumber);
        this.isSuite = Boolean.parseBoolean(isSuite);
        this.suiteTests = suiteTests;
        this.preRequisiteTests = preRequisiteTests;
        this.extraCreditTests = extraCreditTests;
        this.testScores = testScores;
        this.failFromPreReq = failFromPreReq;
    }

    @Transactional
    public static RowFromServer of(Users users, Assignment assignment, String[] csvLine) {
        if (!users.isPersistent()) {
            users.persist();
        }
        if (!assignment.isPersistent()) {
            assignment.persist();
        }
        return new RowFromServer(users, assignment, csvLine[CSVIndexes.ROW.getIndex()], csvLine[CSVIndexes.TIME.getIndex()],
                csvLine[CSVIndexes.PERCENT_PASSES.getIndex()],
                csvLine[CSVIndexes.CHANGE.getIndex()],
                csvLine[CSVIndexes.PERCENT_PASSES.getIndex()],
                csvLine[CSVIndexes.PASSED_TESTS.getIndex()],
                csvLine[CSVIndexes.PARTIAL_TESTS.getIndex()],
                csvLine[CSVIndexes.FAILED_TESTS.getIndex()],
                csvLine[CSVIndexes.UNTESTED_TESTS.getIndex()],
                csvLine[CSVIndexes.SESSION_NUMBER.getIndex()],
                csvLine[CSVIndexes.SESSION_RUN_NUMBER.getIndex()],
                csvLine[CSVIndexes.IS_SUITE.getIndex()],
                csvLine[CSVIndexes.SUITE_TESTS.getIndex()],
                csvLine[CSVIndexes.PREREQUISITE_TESTS.getIndex()],
                csvLine[CSVIndexes.EXTRA_CREDIT_TESTS.getIndex()],
                csvLine[CSVIndexes.TEST_SCORES.getIndex()],
                csvLine[CSVIndexes.FAIL_FROM_PREREQUISITE.getIndex()]);
    }


    @ManyToOne
    @JoinColumn(name="user_id")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    private String row;

    private ZonedDateTime time;

    private int percentPassed;

    private int change;

    private String testName;

    @Lob
    private String passedTests;

    @Lob
    private String partialTests;

    @Lob
    private String failedTests;

    @Lob
    private String untestedTests;

    private int sessionNumber;

    private int sessionRunNumber;

    private boolean isSuite;

    @Lob
    private String suiteTests;

    @Lob
    private String preRequisiteTests;

    @Lob
    private String extraCreditTests;

    @Lob
    private String testScores;

    @Lob
    private String failFromPreReq;

    public RowFromServer() {}

    public String getRow() {
        return row;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public int getPercentPassed() {
        return percentPassed;
    }

    public int getChange() {
        return change;
    }

    public boolean isSuite() {
        return isSuite;
    }

    public String getSuiteTests() {
        return suiteTests;
    }

    public String getPreRequisiteTests() {
        return preRequisiteTests;
    }

    public String getExtraCreditTests() {
        return extraCreditTests;
    }

    public String getTestScores() {
        return testScores;
    }

    public String getFailFromPreReq() {
        return failFromPreReq;
    }

    public String getTestName() {
        return testName;
    }

    public String getPassedTests() {
        return passedTests;
    }

    public String getPartialTests() {
        return partialTests;
    }

    public String getFailedTests() {
        return failedTests;
    }

    public String getUntestedTests() {
        return untestedTests;
    }

    public int getSessionNumber() {
        return sessionNumber;
    }

    public int getSessionRunNumber() {
        return sessionRunNumber;
    }

    public String createCSVLineFromRow() {
        String[] line = new String[17];

        line[CSVIndexes.ROW.getIndex()] = this.getRow();
        line[CSVIndexes.TIME.getIndex()] = this.getTime().toString();
        line[CSVIndexes.PERCENT_PASSES.getIndex()] = String.valueOf(this.getPercentPassed());
        line[CSVIndexes.CHANGE.getIndex()] = String.valueOf(this.getChange());
        line[CSVIndexes.TEST_NAME.getIndex()] = this.getTestName();
        line[CSVIndexes.PASSED_TESTS.getIndex()] = this.getPassedTests();
        line[CSVIndexes.PARTIAL_TESTS.getIndex()] = this.getPartialTests();
        line[CSVIndexes.FAILED_TESTS.getIndex()] = this.getFailedTests();
        line[CSVIndexes.UNTESTED_TESTS.getIndex()] = this.getUntestedTests();
        line[CSVIndexes.SESSION_NUMBER.getIndex()] = String.valueOf(this.getSessionNumber());
        line[CSVIndexes.SESSION_RUN_NUMBER.getIndex()] = String.valueOf(this.getSessionRunNumber());
        line[CSVIndexes.IS_SUITE.getIndex()] = String.valueOf(this.isSuite());
        line[CSVIndexes.SUITE_TESTS.getIndex()] = this.getSuiteTests();
        line[CSVIndexes.PREREQUISITE_TESTS.getIndex()] = this.getPreRequisiteTests();
        line[CSVIndexes.EXTRA_CREDIT_TESTS.getIndex()] = this.getExtraCreditTests();
        line[CSVIndexes.TEST_SCORES.getIndex()] = this.getTestScores();
        line[CSVIndexes.FAIL_FROM_PREREQUISITE.getIndex()] = this.getFailFromPreReq();

        String input = "";

        for (int i = 0; i < line.length; i++) {
            input = input + line[i] + ",";
            if (i == line.length - 1) {
                input = input + line[i];
            }
        }

        return input;
    }
}
