package api.calls.helpers;

import graph.entities.UserBarGraphEntity;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;
import parsing.entities.*;
import parsing.entities.projections.LocalTestNameAndStatusWithUserId;
import parsing.relations.Assignment;
import parsing.relations.TestName;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;
import java.util.stream.Collectors;

@Path("/tests")
public class TestsResource {

    @Inject
    EntityManager em;

    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Timed(name = "checkOldQuery", description = "A measure of how long it takes to perform the older request.", unit = MetricUnits.MILLISECONDS)
    public List<AccumulatedResultsByTest> getTests() {
        List<User> users = User.getEntityManager().createQuery("select distinct t.user from RowFromServer t where t.assignment =:assignment", User.class)
                .setParameter("assignment", Assignment.findById((long)53584)).getResultList();

        TypedQuery<Integer> highestSessionNumberQuery = Session.getEntityManager().createQuery("select max(s.sessionId) from LocalTest lt inner join TestAndSession ts on lt.id = ts.testId inner join Session s on ts.sessionId = s.id where lt.userId =: userId and lt.assignmentId = 53584", Integer.class);

        TypedQuery<Tuple> query  = LocalTest.getEntityManager().createQuery("select lt.testNameId, lt.status from LocalTest lt inner join TestAndSession ts on lt.id = ts.testId inner join Session s on ts.sessionId = s.id where lt.userId =: userId and lt.assignmentId = 53584 and s.sessionId =:sessionId", Tuple.class);

        Map<Long, AccumulatedResultsByTest> map = new HashMap<>();

        for (User user : users) {
            List<Tuple> tuples = query.setParameter("userId", user.id).setParameter("sessionId", highestSessionNumberQuery.setParameter("userId",user.id).getSingleResult()).getResultList();

            for (Tuple tuple : tuples) {
                long testNameId = (long) tuple.get(0);
                TestStatus status = (TestStatus) tuple.get(1);
                if (map.containsKey(testNameId)) {
                    map.get(testNameId).insertUserTestResult(status, user);
                }
                else {
                    TestName testName = TestName.findById(testNameId);
                    map.put(testNameId, AccumulatedResultsByTest.of(testName.getName()));
                }
            }

        }

        return map.values().stream().sorted(Comparator.comparing(AccumulatedResultsByTest::getName)).collect(Collectors.toList());
    }

}