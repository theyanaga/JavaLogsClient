package parsing.helpers;

import gradingTools.logs.LocalChecksLogData;
import gradingTools.logs.localChecksStatistics.collectors.Collector;
import gradingTools.logs.localChecksStatistics.collectors.StandardCollectors.AttemptsCollectorV2;
import gradingTools.logs.localChecksStatistics.collectors.StandardCollectors.FinalStatusCollector;
import gradingTools.logs.localChecksStatistics.collectors.StandardCollectors.TotalAttemptsCollector;
import parsing.entities.AssignmentTestManager;
import parsing.entities.TestRun;
import parsing.entities.LocalChecksTest;
import parsing.entities.TestSession;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class FileHelper {



    public static void main(String[] args) throws IOException {
        Reader in = new FileReader("/Users/felipeyanaga/Downloads/comp533s22_assignment1_S22Assignment1SuiteFineGrained.csv");
        CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT.builder().setHeader("#, Time, %Passes, Change, Test, Pass, Partial, Fail, Untested, SessionNumber, SessionRunNumber, IsSuite, SuiteTests, PrerequisiteTests, ExtraCreditTests, TestScores, FailFromPreReq, Blank").setSkipHeaderRecord(true).build());
        AssignmentTestManager manager = AssignmentTestManager.ofName("Assignment2");
        for (CSVRecord record : parser.getRecords()) {
            TestRun testRun = TestRun.ofLine(Integer.parseInt(record.get(9)), Integer.parseInt(record.get(10)), Boolean.parseBoolean(record.get(11)), record.get(1),
                    record.get(5), record.get(7), record.get(6), record.get(8), record.get(15), manager);
            manager.addNewTestRun(testRun);
        }

        Collector[] collectors = {
                new AttemptsCollectorV2(),
                new FinalStatusCollector(),
                new TotalAttemptsCollector()
//			new ContextBasedWorkTimeIRCollector(),
//			new FixedWorkTimeIRCollector(),
//			new EditsIRCollector(),
//			new RunsIRCollector(),
//			new TestFocusedContextBasedWorkTimeIRCollector(),
//			new TestFocusedFixedWorkTimeIRCollector(),

        };
        File project = new File("src/main/resources/LogFolder");

        List<String> data = LocalChecksLogData.getData(project,"1",collectors);

        System.out.println(data);

        for (TestSession session : manager.getAllTestSessions()) {
            System.out.println(session);
        }
    }

}
