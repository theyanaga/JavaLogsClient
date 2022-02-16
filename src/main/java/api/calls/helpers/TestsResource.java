package api.calls.helpers;

import parsing.entities.*;
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

@Path("/tests")
public class TestsResource {

    @Inject
    EntityManager em;

    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public List<AccumulatedResultsByTest> test(){

        List<TestName> names = em.createQuery("select t from TestName t", TestName.class).getResultList();

        List<AccumulatedResultsByTest> tests = new ArrayList<>();

        Query query = em.createQuery("select count(tn.testId) from TestNameRelation tn inner join TestStatusRelation ts on tn.testId = ts.testId where tn.testNameId=:testNameId and ts.status=:status");

        Query userQuery = em.createQuery("select u from TestNameRelation tn inner join TestStatusRelation ts on tn.testId = ts.testId " +
                "inner join TestUserRelation tu on ts.testId = tu.testId inner join User u on tu.userId = u.id where tn.testNameId=:testNameId and ts.status=:status");

        for (TestName name : names) {
            AccumulatedResultsByTest accumulatedResultsByTest = AccumulatedResultsByTest.of(name.getName());

            accumulatedResultsByTest.setNumberOfUsersThatPassed((long) query.setParameter("testNameId",name.id).setParameter("status", TestStatus.PASS).getSingleResult());
            accumulatedResultsByTest.setNumberOfUsersThatPartiallyPassed((long) query.setParameter("testNameId",name.id).setParameter("status", TestStatus.PARTIAL).getSingleResult());
            accumulatedResultsByTest.setNumberOfUsersThatFailed((long) query.setParameter("testNameId",name.id).setParameter("status", TestStatus.FAIL).getSingleResult());
            accumulatedResultsByTest.setNumberOfUsersThatDidNotRunTest((long) query.setParameter("testNameId",name.id).setParameter("status", TestStatus.UNTESTED).getSingleResult());
            accumulatedResultsByTest.setUsersThatPassed(userQuery.setParameter("testNameId",name.id).setParameter("status", TestStatus.PASS).setMaxResults(3).getResultList());
            accumulatedResultsByTest.setUsersThatPartiallyPassed(userQuery.setParameter("testNameId",name.id).setParameter("status", TestStatus.PARTIAL).setMaxResults(3).getResultList());
            accumulatedResultsByTest.setUsersThatFailed(userQuery.setParameter("testNameId",name.id).setParameter("status", TestStatus.FAIL).setMaxResults(3).getResultList());
            accumulatedResultsByTest.setUsersThatDidNotRunTest(userQuery.setParameter("testNameId",name.id).setParameter("status", TestStatus.UNTESTED).setMaxResults(3).getResultList());

            tests.add(accumulatedResultsByTest);
        }

        return tests;
    }

}