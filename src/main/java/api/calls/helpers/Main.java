package api.calls.helpers;

import api.calls.entities.ServerInputWrapper;
import api.calls.entities.ServerOutputWrapper;
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
import org.hibernate.engine.spi.Managed;
import org.jboss.logging.Logger;
import parsing.entities.*;
import parsing.helpers.AndrewOutputProcessor;

import javax.enterprise.inject.New;
import javax.inject.Inject;
import javax.transaction.TransactionScoped;
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

//            long skip = RowFromServer.count();
//
//            for(int i = 0; i < 100; i++) {
//                ServerOutputWrapper wrapper = logsService.getLogs(ServerInputWrapper.createServerRequest(
//                        (int) (skip + i)
//                ));
//
//                String[] csvLine = wrapper.getLogs().get(0).getLog().getJson().split(",");
//
//                User user = User.of(wrapper.getLogs().get(0).getMachineId());
//
//                Course course = Course.of(Course.COMP_524, Season.FALL, 2021);
//
//                Assignment assignment = Assignment.of(2, course);
//
//                RowFromServer serverRow = RowFromServer.of(user, assignment, csvLine);
//                serverRow.persistAndFlush();
//            }
//
//            List<User> users = User.listAll();
//
//            for (User user : users) {
//                List<RowFromServer> rows = RowFromServer.find("user_id", user.id).list();
//
//                Collector[] collectors = {
//                        new AttemptsCollectorV2(),
//                        new FinalStatusCollector(),
//                };
//
//                CollectorManager cm = new CollectorManager(collectors);
//
//                List<String> lines = new ArrayList<>();
//
//                for (RowFromServer row : rows)  {
//                    lines.add(row.createCSVLineFromRow());
//                }
//
//                if (!lines.isEmpty()) {
//                    List<LocalTest> tests = AndrewOutputProcessor.processInput(LocalLogDataAnalyzer.runEvaluationFromDatabase(lines, cm),
//                            Assignment.findById((long) 3));
//
//                    for (LocalTest test : tests) {
//                        test.setUser_id(user);
//                        test.persist();
//                    }
//                }
//            }

            Quarkus.waitForExit();
            return 0;
        }

    }
}
