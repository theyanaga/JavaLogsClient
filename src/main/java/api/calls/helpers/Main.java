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
import javax.persistence.EntityManager;
import javax.persistence.Query;
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

        @Inject
        EntityManager em;

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
//            Query userQuery = em.createQuery("select u from TestNameRelation tn inner join TestStatusRelation ts on tn.testId = ts.testId " +
//                    "inner join TestUserRelation tu on ts.testId = tu.testId inner join User u on tu.userId = u.id where tn.testNameId=:testNameId and ts.status=:status")
//                    .setParameter("testNameId", (long)3901).setParameter("status", TestStatus.PASS)
//                    .setMaxResults(3);
//            System.out.println(userQuery.getResultList());


            Quarkus.waitForExit();
            return 0;
        }

    }
}
