package parsing.helpers;

import gradingTools.logs.LocalChecksLogData;
import gradingTools.logs.localChecksStatistics.collectors.Collector;
import gradingTools.logs.localChecksStatistics.collectors.StandardCollectors.AttemptsCollectorV2;
import gradingTools.logs.localChecksStatistics.collectors.StandardCollectors.FinalStatusCollector;
import gradingTools.logs.localChecksStatistics.collectors.StandardCollectors.TestScoreCollector;
import gradingTools.logs.localChecksStatistics.collectors.StandardCollectors.TotalAttemptsCollector;
import gradingTools.logs.localChecksStatistics.compiledLogGenerator.CollectorManager;
import parsing.entities.AssignmentTestManager;
import parsing.entities.TestRun;
import parsing.entities.TestSession;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static gradingTools.logs.localChecksStatistics.compiledLogGenerator.LocalLogDataAnalyzer.runEvaluationFromDatabase;

public class FileHelper {

    public static void processFiles() throws IOException {
        File dir = new File("/Users/felipeyanaga/Downloads/dewan-research/src/main/resources/student-logs");
        if (dir.isDirectory()) {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                Collector [] collectors = {
                        new AttemptsCollectorV2(),
                        new FinalStatusCollector(),
                        new TotalAttemptsCollector(),
                        new TestScoreCollector(),
                };
                List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);

                List<String> results = runEvaluationFromDatabase(lines, new CollectorManager(collectors)).stream().filter(s -> {
                    String[] splitString = s.split(" ");
                    return !splitString[0].equalsIgnoreCase("\"");
                }).map(s -> s.replaceAll("\"","")).collect(Collectors.toList());

                System.out.println(results);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        processFiles();
    }

}
