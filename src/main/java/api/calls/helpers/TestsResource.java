package api.calls.helpers;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;
import parsing.entities.*;
import parsing.entities.projections.LocalTestNameAndStatusWithUserId;
import parsing.relations.TestName;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/tests")
public class TestsResource {

    @Inject
    EntityManager em;

//    @GET
//    @Transactional
//    @Produces(MediaType.APPLICATION_JSON)
//    @Timed(name = "checksNewQuery", description = "A measure of how long it takes to perform the /tests request.", unit = MetricUnits.MILLISECONDS)
//    public List<AccumulatedResultsByTest> test(){
//
//        List<TestName> names = em.createQuery("select t from TestName t", TestName.class).getResultList();
//
//        List<AccumulatedResultsByTest> tests = new ArrayList<>();
//
//        Query query = em.createQuery("select count(tn.testId) from TestNameRelation tn inner join TestStatusRelation ts on tn.testId = ts.testId where tn.testNameId=:testNameId and ts.status=:status");
//
//        Query userQuery = em.createQuery("select u from TestNameRelation tn inner join TestStatusRelation ts on tn.testId = ts.testId " +
//                "inner join TestUserRelation tu on ts.testId = tu.testId inner join User u on tu.userId = u.id where tn.testNameId=:testNameId and ts.status=:status");
//
//        for (TestName name : names) {
//            AccumulatedResultsByTest accumulatedResultsByTest = AccumulatedResultsByTest.of(name.getName());
//
//            accumulatedResultsByTest.setNumberOfUsersThatPassed((long) query.setParameter("testNameId",name.id).setParameter("status", TestStatus.PASS).getSingleResult());
//            accumulatedResultsByTest.setNumberOfUsersThatPartiallyPassed((long) query.setParameter("testNameId",name.id).setParameter("status", TestStatus.PARTIAL).getSingleResult());
//            accumulatedResultsByTest.setNumberOfUsersThatFailed((long) query.setParameter("testNameId",name.id).setParameter("status", TestStatus.FAIL).getSingleResult());
//            accumulatedResultsByTest.setNumberOfUsersThatDidNotRunTest((long) query.setParameter("testNameId",name.id).setParameter("status", TestStatus.UNTESTED).getSingleResult());
//            accumulatedResultsByTest.setUsersThatPassed(userQuery.setParameter("testNameId",name.id).setParameter("status", TestStatus.PASS).setMaxResults(3).getResultList());
//            accumulatedResultsByTest.setUsersThatPartiallyPassed(userQuery.setParameter("testNameId",name.id).setParameter("status", TestStatus.PARTIAL).setMaxResults(3).getResultList());
//            accumulatedResultsByTest.setUsersThatFailed(userQuery.setParameter("testNameId",name.id).setParameter("status", TestStatus.FAIL).setMaxResults(3).getResultList());
//            accumulatedResultsByTest.setUsersThatDidNotRunTest(userQuery.setParameter("testNameId",name.id).setParameter("status", TestStatus.UNTESTED).setMaxResults(3).getResultList());
//
//            tests.add(accumulatedResultsByTest);
//        }
//
//        return tests;
//    }

    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Timed(name = "checkOldQuery", description = "A measure of how long it takes to perform the older request.", unit = MetricUnits.MILLISECONDS)
    public List<AccumulatedResultsByTest> getTests() {
        List<TestName> names = TestName.listAll();

        List<AccumulatedResultsByTest> tests = new ArrayList<>();

        List<LocalTestNameAndStatusWithUserId> passedTestNames = LocalTest.find("status", TestStatus.PASS).project(LocalTestNameAndStatusWithUserId.class).list();
        List<LocalTestNameAndStatusWithUserId> partiallyPassedTestNames = LocalTest.find("status", TestStatus.PARTIAL).project(LocalTestNameAndStatusWithUserId.class).list();
        List<LocalTestNameAndStatusWithUserId> failedTestNames = LocalTest.find("status", TestStatus.FAIL).project(LocalTestNameAndStatusWithUserId.class).list();
        List<LocalTestNameAndStatusWithUserId> untestedTestNames = LocalTest.find("status", TestStatus.UNTESTED).project(LocalTestNameAndStatusWithUserId.class).list();


        for (TestName name : names) {
            AccumulatedResultsByTest accumulatedResultsByTest = AccumulatedResultsByTest.of(name.getName());

            List<LocalTestNameAndStatusWithUserId> passedTestsWithRightName = passedTestNames.stream().distinct()
                    .filter(o -> o.getTestNameId() == name.id).collect(Collectors.toList());
            List<LocalTestNameAndStatusWithUserId> partiallyPassedTestWithRightName = partiallyPassedTestNames.stream().distinct().
                    filter(o -> o.getTestNameId() == name.id).collect(Collectors.toList());
            List<LocalTestNameAndStatusWithUserId> failedTestsWithRightName = failedTestNames.stream().distinct()
                    .filter(o -> o.getTestNameId() == name.id).collect(Collectors.toList());
            List<LocalTestNameAndStatusWithUserId> untestedTestsWithRightName = untestedTestNames.stream().distinct()
                    .filter(o -> o.getTestNameId() == name.id).collect(Collectors.toList());

            accumulatedResultsByTest.setNumberOfUsersThatPassed(passedTestsWithRightName.size());
            accumulatedResultsByTest.setNumberOfUsersThatPartiallyPassed(partiallyPassedTestWithRightName.size());
            accumulatedResultsByTest.setNumberOfUsersThatFailed(failedTestsWithRightName.size());
            accumulatedResultsByTest.setNumberOfUsersThatDidNotRunTest(untestedTestsWithRightName.size());
            accumulatedResultsByTest.setUsersThatPassed(User.findUsersfromList(passedTestsWithRightName));
            accumulatedResultsByTest.setUsersThatPartiallyPassed(User.findUsersfromList(partiallyPassedTestWithRightName));
            accumulatedResultsByTest.setUsersThatFailed(User.findUsersfromList(failedTestsWithRightName));
            accumulatedResultsByTest.setUsersThatDidNotRunTest(User.findUsersfromList(untestedTestsWithRightName));

            tests.add(accumulatedResultsByTest);
        }

        return tests;
    }

}