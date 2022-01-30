package parsing.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class RowFromServer extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name="user_id")
    private Users users;

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

    public RowFromServer() {
    }

    public static class Builder {
        private Users users;
        private String row;
        private ZonedDateTime time;
        private int percentPassed;
        private int change;
        private String testName;
        private String passedTests;
        private String partialTests;
        private String failedTests;
        private String untestedTests;
        private int sessionNumber;
        private int sessionRunNumber;
        private boolean isSuite;
        private String suiteTests;
        private String preRequisiteTests;
        private String extraCreditTests;
        private String testScores;
        private String failFromPreReq;

        public Builder user(Users aUsers){
            users = aUsers;
            return this;
        }

        public Builder row(String aRow) {
            row = aRow;
            return this;
        }

        public Builder time(String aTimeString){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy");
            time = ZonedDateTime.parse(aTimeString, dateTimeFormatter);
            return this;
        }

        public Builder percentPassed(String aPercentage) {
            percentPassed = Integer.parseInt(aPercentage);
            return this;
        }

        public Builder change(String aChange) {
            change = Integer.parseInt(aChange);
            return this;
        }

        public Builder testName(String aTestName) {
            testName = aTestName;
            return this;
        }

        public Builder passedTests(String val) {
            passedTests = val;
            return this;
        }

        public Builder partialTests(String val) {
            partialTests = val;
            return this;
        }

        public Builder failedTests(String val) {
            failedTests = val;
            return this;
        }

        public Builder untestedTests(String val) {
            untestedTests = val;
            return this;
        }

        public Builder sessionNumber(String val) {
            sessionNumber = Integer.parseInt(val);
            return this;
        }

        public Builder sessionRunNumber(String val) {
            sessionRunNumber = Integer.parseInt(val);
            return this;
        }

        public Builder isSuite(String val) {
            isSuite = Boolean.parseBoolean(val);
            return this;
        }

        public Builder suiteTests(String val) {
            suiteTests = val;
            return this;
        }

        public Builder prerequisiteTests(String val) {
            preRequisiteTests = val;
            return this;
        }

        public Builder extraCreditTests(String val) {
            extraCreditTests = val;
            return this;
        }

        public Builder testScores(String val) {
            testScores = val;
            return this;
        }

        public Builder failFromPrerequisite(String val) {
            failFromPreReq = val;
            return this;
        }

        public RowFromServer build() {
            return new RowFromServer(this);
        }

    }

    private RowFromServer(Builder builder) {
        users = builder.users;
        row = builder.row;
        time = builder.time;
        percentPassed = builder.percentPassed;
        change = builder.change;
        testName = builder.testName;
        passedTests = builder.passedTests;
        partialTests = builder.partialTests;
        failedTests = builder.failedTests;
        untestedTests = builder.untestedTests;
        sessionNumber = builder.sessionNumber;
        sessionRunNumber = builder.sessionRunNumber;
        isSuite = builder.isSuite;
        suiteTests = builder.suiteTests;
        preRequisiteTests = builder.preRequisiteTests;
        extraCreditTests = builder.extraCreditTests;
        testScores = builder.testScores;
        failFromPreReq = builder.failFromPreReq;
    }

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
}
