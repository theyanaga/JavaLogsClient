package org.acme.logs.parsing.helpers;

import org.acme.logs.parsing.entities.AssignmentTestManager;
import org.acme.logs.parsing.entities.TestRun;
import org.acme.logs.parsing.entities.TestSession;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class FileHelper {



    public static void main(String[] args) throws IOException {
        Reader in = new FileReader("/Users/felipeyanaga/Downloads/comp533s22_assignment1_S22Assignment1SuiteFineGrained.csv");
        CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT.builder().setHeader("#, Time, %Passes, Change, Test, Pass, Partial, Fail, Untested, SessionNumber, SessionRunNumber, IsSuite, SuiteTests, PrerequisiteTests, ExtraCreditTests, TestScores, FailFromPreReq, Blank").setSkipHeaderRecord(true).build());
        AssignmentTestManager manager = AssignmentTestManager.ofName("Assignment2");
        for (CSVRecord record : parser.getRecords()) {
            int sessionId = Integer.parseInt(record.get(9));
            if (!manager.containsSession(sessionId)) {
                manager.getAllTestSessions().add(TestSession.ofId(sessionId));
            }
            TestRun testRun = TestRun.ofLine(Integer.parseInt(record.get(9)), Integer.parseInt(record.get(10)), Boolean.parseBoolean(record.get(11)), record.get(1),
                    record.get(5), record.get(7), record.get(6), record.get(8), record.get(15), manager);
            manager.addNewTestRun(testRun);
        }

        for (TestSession session : manager.getAllTestSessions()) {
            System.out.println(session);
        }
    }

}
