package api.calls.helpers;

import api.calls.entities.ServerInputWrapper;
import api.calls.entities.ServerOutput;
import api.calls.entities.ServerOutputWrapper;
import com.github.javafaker.Faker;
import gradingTools.logs.localChecksStatistics.collectors.Collector;
import gradingTools.logs.localChecksStatistics.collectors.StandardCollectors.AttemptsCollectorV2;
import gradingTools.logs.localChecksStatistics.collectors.StandardCollectors.FinalStatusCollector;
import gradingTools.logs.localChecksStatistics.collectors.StandardCollectors.TestScoreCollector;
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
import parsing.entities.projections.LocalTestNameAndStatus;
import parsing.helpers.AndrewOutputProcessor;
import parsing.relations.*;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Order;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

        public static final int SKIP = 1741; // stop at 1745

        @Inject
        @RestClient
        LogsService logsService;

        @Inject
        EntityManager em;

        private static final Logger LOG = Logger.getLogger(Main.class);

        @Override
        @Transactional(Transactional.TxType.REQUIRED)
        public int run(String[] args) throws Exception {

//            List<User> users = User.getEntityManager().createQuery("select distinct t.user from RowFromServer t where t.assignment =:assignment", User.class).setParameter("assignment", Assignment.findById((long)53584)).getResultList();
//
//            TypedQuery<Long> typedQuery = User.getEntityManager().createQuery("select distinct t.userId from LocalTest t where t.assignmentId = 53584", Long.class);
//
//            List<Long> usersAlreadyPersisted = typedQuery.getResultList();
//
//            Query query = RowFromServer.getEntityManager().createQuery("select distinct t.sessionNumber from RowFromServer t where t.user =:user and t.assignment =:assignmentId");
//
//            Query anotherQuery = RowFromServer.getEntityManager().createQuery("select t from RowFromServer t where t.user =:user and t.sessionNumber =:sessionNumber and t.assignment =:assignmentId");
//
//            for (User user : users) {
//
//                if (!(usersAlreadyPersisted.contains(user.id))) {
//                    List sessionNumbers = query.setParameter("user", user).setParameter("assignmentId", Assignment.findById((long)53584)).getResultList();
//                    for (Object o : sessionNumbers) {
//                        int sessionNumber = (int) o;
//
//                        Session session = Session.getSession((int) o,  user.id);
//
//                        if (!session.isPersistent()) {
//                            session.persist();
//                        }
//
//                        List rows = anotherQuery.setParameter("user", user).setParameter("sessionNumber", sessionNumber).setParameter("assignmentId", Assignment.findById((long)53584)).getResultList();
//
//                        List<String> csvRows = new ArrayList<>();
//
//                        for (Object row : rows) {
//                            RowFromServer rowFromServer = (RowFromServer) row;
//                            csvRows.add(rowFromServer.createCSVLineFromRow());
//                        }
//
//                        Collector[] collectors = {
//                                new AttemptsCollectorV2(), new FinalStatusCollector(), new TestScoreCollector()
//                        };
//
//                        CollectorManager cm = new CollectorManager(collectors);
//
//                        List<String> outputFromAndrew = LocalLogDataAnalyzer.runEvaluationFromDatabase(csvRows, cm);
//
//                        List<LocalTest> tests = AndrewOutputProcessor.processInput(outputFromAndrew, Assignment.findById((long)53584));
//
//                        for (LocalTest test : tests) {
//                            test.setUserId(((User) user).id);
//                            test.persistAndFlush();
//                            TestAndSession testAndSession = new TestAndSession();
//                            testAndSession.setTestId(test.id);
//                            testAndSession.setSessionId(session.id);
//                            testAndSession.persistAndFlush();
//                        }
//                    }
//                }
//
//            }

//            TypedQuery<Long> userIds = User.getEntityManager().createQuery("select u.id from User u where u.id = 53586", Long.class);
//
//            List<Long> users = userIds.getResultList();
//
//            for (Long aLong : users) {
//                User user = User.findById(aLong);
//                Faker faker = new Faker();
//
//                user.setFirstName(faker.name().firstName());
//                user.setLastName(faker.name().lastName());
//
//                User.getEntityManager().merge(user);
//
//                user.persistAndFlush();
//
//            }

//            System.out.println(tests);

            Quarkus.waitForExit();
            return 0;
        }

    }
}
