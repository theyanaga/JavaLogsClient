package api.calls.helpers;

import gradingTools.logs.localChecksStatistics.collectors.Collector;
import gradingTools.logs.localChecksStatistics.collectors.StandardCollectors.AttemptsCollectorV2;
import gradingTools.logs.localChecksStatistics.collectors.StandardCollectors.FinalStatusCollector;
import gradingTools.logs.localChecksStatistics.compiledLogGenerator.CollectorManager;
import gradingTools.logs.localChecksStatistics.compiledLogGenerator.LocalLogDataAnalyzer;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.context.ThreadContext;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import parsing.entities.*;
import parsing.helpers.AndrewOutputProcessor;
import parsing.relations.*;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@QuarkusMain
public class Main {

    // Pick a single log and extract things from it.

    public static void main(String[] args) throws IOException {
        Quarkus.run(MyApp.class,args);
    }

    public static class MyApp implements QuarkusApplication {

        @Inject
        ThreadContext threadContext;
        @Inject
        ManagedExecutor managedExecutor;

        public static final String MY_PATH = "/Users/felipeyanaga/UNC/Research/LocalCheckLogs/";

        public static final int SKIP = 250;

        @Inject
        @RestClient
        LogsService logsService;

        private static final Logger LOG = Logger.getLogger(Main.class);

        @Override
        @Transactional(Transactional.TxType.REQUIRED)
        public int run(String[] args) throws Exception {

////            List<User> users = User.listAll();
////
////            for (User user : users) {
////                List<RowFromServer> rowsForUser = RowFromServer.find("user_id", user.id).list();
////
////                Collector[] collectors = {
////                        new AttemptsCollectorV2(),
////                        new FinalStatusCollector(),
////                };
////
////                CollectorManager cm = new CollectorManager(collectors);
////
////                List<String> lines = new ArrayList<>();
////
////                for (RowFromServer row : rowsForUser)  {
////                    lines.add(row.createCSVLineFromRow());
////                }
////
////                List<LocalTest> tests = AndrewOutputProcessor.processInput(LocalLogDataAnalyzer.runEvaluationFromDatabase(lines, cm),
////                        Assignment.findById((long)3));
////
////                for (LocalTest test : tests) {
////                    IndividualTest individualTest = new IndividualTest();
////                    individualTest.persist();
////
////                    TestNameRelation testNameRelation = TestNameRelation.of(individualTest.id, test.getTestNameId());
////                    Attempt attempt = Attempt.of(test.getAttempts());
////                    TestAttemptsRelation testAttemptsRelation = TestAttemptsRelation.of(individualTest.id, attempt.id);
////                    TestAssignmentRelation testAssignmentRelation = TestAssignmentRelation.of(individualTest.id, (long) 3);
////                    TestStatusRelation testStatusRelation = TestStatusRelation.of(individualTest.id, test.getStatus());
////                    TestUserRelation testUserRelation = TestUserRelation.of(individualTest.id, user.id);
////
////                }
//
//            }


            Quarkus.waitForExit();
            return 0;
        }

    }
}
