package api.calls.helpers;

import graph.entities.UserBarGraphEntity;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;
import parsing.entities.AccumulatedResultsByTest;
import parsing.entities.LocalTest;
import parsing.entities.TestStatus;
import parsing.entities.User;
import parsing.entities.projections.LocalTestNameAndStatus;
import parsing.entities.projections.LocalTestNameAndStatusWithUserId;
import parsing.relations.TestName;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
        List<TestName> names = TestName.listAll();

        List<UserBarGraphEntity> tests = new ArrayList<>();

        List<LocalTestNameAndStatus> passedTestNames = LocalTest.find("status", TestStatus.PASS).project(LocalTestNameAndStatus.class).list();
        List<LocalTestNameAndStatus> partiallyPassedTestNames = LocalTest.find("status", TestStatus.PARTIAL).project(LocalTestNameAndStatus.class).list();
        List<LocalTestNameAndStatus> failedTestNames = LocalTest.find("status", TestStatus.FAIL).project(LocalTestNameAndStatus.class).list();
        List<LocalTestNameAndStatus> untestedTestNames = LocalTest.find("status", TestStatus.UNTESTED).project(LocalTestNameAndStatus.class).list();

        for (TestName name : names) {
            long countOfPassed = passedTestNames.stream().distinct()
                    .filter(o -> o.getTestNameId() == name.id).count();
            long countOfPartiallyPassed = partiallyPassedTestNames.stream().distinct().
                    filter(o -> o.getTestNameId() == name.id).count();
            long countOfFailed = failedTestNames.stream().distinct()
                    .filter(o -> o.getTestNameId() == name.id).count();
            long countOfUntested = untestedTestNames.stream().distinct()
                    .filter(o -> o.getTestNameId() == name.id).count();

            tests.add(UserBarGraphEntity.of(name.getName(), countOfPassed, countOfPartiallyPassed, countOfFailed, countOfUntested));
        }

        return tests;
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
