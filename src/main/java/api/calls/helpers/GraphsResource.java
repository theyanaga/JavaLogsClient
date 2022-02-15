package api.calls.helpers;

import graph.entities.UserBarGraphEntity;
import parsing.entities.AccumulatedResultsByTest;
import parsing.entities.LocalTest;
import parsing.entities.TestStatus;
import parsing.entities.User;
import parsing.entities.projections.LocalTestNameAndStatus;
import parsing.entities.projections.LocalTestNameAndStatusWithUserId;
import parsing.relations.TestName;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/graphs")
public class GraphsResource {

    @Path("/testsPassedBarGraph")
    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
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

}
