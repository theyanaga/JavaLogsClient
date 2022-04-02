package api.calls.helpers;

import api.calls.entities.Holder;
import graph.entities.UserBarGraphEntity;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;
import parsing.entities.*;
import parsing.entities.projections.LocalTestNameAndStatus;
import parsing.entities.projections.LocalTestNameAndStatusWithUserId;
import parsing.relations.Assignment;
import parsing.relations.TestName;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;
import java.util.stream.Collectors;

@Path("/graphs")
public class GraphsResource {

    @Inject
    EntityManager em;

    @Path("/testsPassedBarGraphSortedByName")
    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Timed(name = "checkOldGraphQuery", description = "A measure of how long it takes to perform the older request.", unit = MetricUnits.MILLISECONDS)
    public List<UserBarGraphEntity> getUserBarChartGraph() {

        List<User> users = User.getEntityManager().createQuery("select distinct t.user from RowFromServer t where t.assignment =:assignment", User.class)
                .setParameter("assignment", Assignment.findById((long)53584)).getResultList();

        TypedQuery<Integer> highestSessionNumberQuery = Session.getEntityManager().createQuery("select max(s.sessionId) from LocalTest lt inner join TestAndSession ts on lt.id = ts.testId inner join Session s on ts.sessionId = s.id where lt.userId =: userId and lt.assignmentId = 53584", Integer.class);

        TypedQuery<Tuple> query  = LocalTest.getEntityManager().createQuery("select lt.testNameId, lt.status from LocalTest lt inner join TestAndSession ts on lt.id = ts.testId inner join Session s on ts.sessionId = s.id where lt.userId =: userId and lt.assignmentId = 53584 and s.sessionId =:sessionId", Tuple.class);

        Map<Long, UserBarGraphEntity> map = new HashMap<>();

        for (User user : users) {
            List<Tuple> tuples = query.setParameter("userId", user.id).setParameter("sessionId", highestSessionNumberQuery.setParameter("userId",user.id).getSingleResult()).getResultList();

            for (Tuple tuple : tuples) {
                long testNameId = (long) tuple.get(0);
                TestStatus status = (TestStatus) tuple.get(1);
                if (map.containsKey(testNameId)) {
                    map.get(testNameId).addTestResult(status);
                }
                else {
                    TestName testName = TestName.findById(testNameId);
                    map.put(testNameId, UserBarGraphEntity.of(testName.getName(), 0, 0 ,0 ,0));
                }
            }

        }

        return map.values().stream().sorted(Comparator.comparing(UserBarGraphEntity::getTestName)).collect(Collectors.toList());


    }

    @Path("/testsPassedBarGraphAscendingOrders")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserBarGraphEntity> getUserBarChartGraphInAscendingOrder() {
        List<UserBarGraphEntity> graphData = getUserBarChartGraph();
        graphData.sort(Comparator.comparing(UserBarGraphEntity::getCountOfPassed));
        return graphData;
    }

    @Path("/testsPassedBarGraphDescendingOrders")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserBarGraphEntity> getUserBarChartGraphInDescendingOrder() {
        List<UserBarGraphEntity> graphData = getUserBarChartGraphInAscendingOrder();
        Collections.reverse(graphData);
        return graphData;
    }
}
