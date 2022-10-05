package api.calls.helpers;

import api.calls.entities.Holder;
import parsing.entities.User;
import parsing.entities.UserWithTests;
import parsing.entities.*;
import parsing.entities.projections.LocalTestNameAndStatus;
import parsing.relations.Assignment;
import parsing.relations.Course;
import parsing.relations.TestName;

import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/potato")
public class AResource {

    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserWithTests> test() {
//        List<RowFromServer> rows = RowFromServer.find("user_id", 1).list();
//
//        Collector[] collectors = {
//                new AttemptsCollectorV2(),
//                new FinalStatusCollector(),
//        };
//
//        CollectorManager cm = new CollectorManager(collectors);
//
//        List<String> lines = new ArrayList<>();
//
//        for (RowFromServer row : rows)  {
//            lines.add(row.createCSVLineFromRow());
//        }
//
//        List<UserWithTests> users = new ArrayList<>();
//
//
//        users.add(UserWithTests.of(User.findById( (long) 1), AndrewOutputProcessor.processInput(LocalLogDataAnalyzer.runEvaluationFromDatabase(lines, cm),
//                Assignment.findById( (long) 3))));

        return null;
    }

    @Path("/salad")
    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getTestNames() {
        TypedQuery<String> testNames = TestName.getEntityManager().createQuery("select tn.name from TestName tn where tn.id > 163822 ", String.class);

        return testNames.getResultList();
    }

    @Path("/allUsers")
    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserWithTests> getAllUsersAndTheirTests() {

        List<User> users = User.getEntityManager().createQuery("select distinct t.user from RowFromServer t where t.assignment =:assignment", User.class)
                .setParameter("assignment", Assignment.findById((long)163824)).getResultList();

        TypedQuery<Integer> highestSessionNumberQuery = Session.getEntityManager().createQuery("select max(s.sessionId) from LocalTest lt inner join TestAndSession ts on lt.id = ts.testId inner join Session s on ts.sessionId = s.id where lt.userId =: userId and lt.assignmentId = 163824", Integer.class);

//        TypedQuery<LocalTest> mostRecentTestResult = LocalTest.getEntityManager().createQuery("select lt from LocalTest lt inner join TestAndSession ts on lt.id = ts.testId inner join Session s on ts.sessionId = s.id where lt.userId = 119 and lt.assignmentId = 53584 and s.sessionId = 96", LocalTest.class);
        TypedQuery<Tuple> query  = LocalTest.getEntityManager().createQuery("select lt.testNameId, lt.status from LocalTest lt inner join TestAndSession ts on lt.id = ts.testId inner join Session s on ts.sessionId = s.id where lt.userId =: userId and lt.assignmentId = 163824 and s.sessionId =:sessionId", Tuple.class);

        List<UserWithTests> userWithTests = new ArrayList<>();

        for (User user : users) {
//            List<LocalTestNameAndStatus> tests = LocalTest.find("user_id", user.id).project(LocalTestNameAndStatus.class).list();
            List<Tuple> tests = query.setParameter("userId", user.id).setParameter("sessionId", highestSessionNumberQuery.setParameter("userId",user.id).getSingleResult()).getResultList();
            List<Holder> elements = new ArrayList<>();
            for (Tuple tuple : tests) {
                TestName testName = TestName.findById(tuple.get(0));
                Holder holder = new Holder(testName.getName(), tuple.get(1).toString());
                if (elements.stream().noneMatch(o -> o.name.equalsIgnoreCase(testName.getName()))) {
                    elements.add(holder);
                }
            }

            userWithTests.add(
                    UserWithTests.of(user, elements)
            );
        }

        return userWithTests;


    }

    @Path("/summary")
    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserSummary> getSummary() {

        List<User> users = User.getEntityManager().createQuery("select distinct t.user from RowFromServer t where t.assignment =:assignment", User.class)
                .setParameter("assignment", Assignment.findById((long)163824)).getResultList();

        TypedQuery<Integer> highestSessionNumberQuery = Session.getEntityManager().createQuery("select max(s.sessionId) from LocalTest lt inner join TestAndSession ts on lt.id = ts.testId inner join Session s on ts.sessionId = s.id where lt.userId =: userId and lt.assignmentId = 163824", Integer.class);

//        TypedQuery<LocalTest> mostRecentTestResult = LocalTest.getEntityManager().createQuery("select lt from LocalTest lt inner join TestAndSession ts on lt.id = ts.testId inner join Session s on ts.sessionId = s.id where lt.userId = 119 and lt.assignmentId = 53584 and s.sessionId = 96", LocalTest.class);
        TypedQuery<LocalTest> query  = LocalTest.getEntityManager().createQuery("select lt from LocalTest lt inner join TestAndSession ts on lt.id = ts.testId inner join Session s on ts.sessionId = s.id where lt.userId =: userId and lt.assignmentId = 163824 and s.sessionId =:sessionId", LocalTest.class);

        List<UserSummary> userWithTests = new ArrayList<>();

        for (User user : users) {
//            List<LocalTestNameAndStatus> tests = LocalTest.find("user_id", user.id).project(LocalTestNameAndStatus.class).list();
            List<LocalTest> tests = query.setParameter("userId", user.id).setParameter("sessionId", highestSessionNumberQuery.setParameter("userId",user.id).getSingleResult()).getResultList();
            List<LocalTest> elements = new ArrayList<>();

            List<ZonedDateTime> times = RowFromServer.getEntityManager().createQuery("select r.time from RowFromServer r where r.assignment=:assignmentId and r.user=:user order by r.time", ZonedDateTime.class)
                    .setParameter("assignmentId", Assignment.findById((long)163824))
                    .setParameter("user", user)
                    .getResultList();

            TimeSummary timeSummary = new TimeSummary(times.get(0), times.get(times.size() - 1));

            for (LocalTest test: tests) {
                TestName testName = TestName.findById(test.testNameId);
                if (elements.stream().noneMatch(o -> o.testNameId == testName.id)) {
                    elements.add(test);
                }
            }

            userWithTests.add(
                    new UserSummary(user, elements, timeSummary)
            );
        }

        return userWithTests;


    }
}
