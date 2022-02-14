package api.calls.helpers;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import parsing.entities.*;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/tests")
public class TestsResource {

    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public List<AccumulatedResultsByTest> test(){
        List<TestName> names = TestName.listAll();

        List<AccumulatedResultsByTest> tests = new ArrayList<>();

        for (TestName testName : names) {
            AccumulatedResultsByTest accumulatedResultsByTest = AccumulatedResultsByTest.of(testName.getName());

            PanacheQuery<LocalTest> passedTestQuery = LocalTest.find("test_name_id = ?1 and status = ?2", testName.id, TestStatus.PASS);
            PanacheQuery<LocalTest> partiallyPassedTestQuery  = LocalTest.find("test_name_id = ?1 and status = ?2", testName.id, TestStatus.PARTIAL);
            PanacheQuery<LocalTest> failedTestQuery  = LocalTest.find("test_name_id = ?1 and status = ?2", testName.id, TestStatus.FAIL);
            PanacheQuery<LocalTest> untestedTestQuery  = LocalTest.find("test_name_id = ?1 and status = ?2", testName.id, TestStatus.UNTESTED);

            accumulatedResultsByTest.setNumberOfUsersThatPassed( (int) passedTestQuery.count());
            accumulatedResultsByTest.setNumberOfUsersThatPartiallyPassed((int) partiallyPassedTestQuery.count());
            accumulatedResultsByTest.setNumberOfUsersThatFailed((int) failedTestQuery.count());
            accumulatedResultsByTest.setNumberOfUsersThatDidNotRunTest((int) untestedTestQuery.count());

            List<Long> userPassedIds = passedTestQuery.page(Page.ofSize(3)).stream().map(LocalTest::getUserId).collect(Collectors.toList());
            List<Long> userPartiallyPassedIds = partiallyPassedTestQuery.page(Page.ofSize(3)).stream().map(LocalTest::getUserId).collect(Collectors.toList());
            List<Long> userFailedIds = failedTestQuery.page(Page.ofSize(3)).stream().map(LocalTest::getUserId).collect(Collectors.toList());
            List<Long> userUntestedIds = untestedTestQuery.page(Page.ofSize(3)).stream().map(LocalTest::getUserId).collect(Collectors.toList());

            accumulatedResultsByTest.setUsersThatPassed(User.findUsersFromId(userPassedIds));
            accumulatedResultsByTest.setUsersThatPartiallyPassed(User.findUsersFromId(userPartiallyPassedIds));
            accumulatedResultsByTest.setUsersThatFailed(User.findUsersFromId(userFailedIds));
            accumulatedResultsByTest.setUsersThatDidNotRunTest(User.findUsersFromId(userUntestedIds));

            tests.add(accumulatedResultsByTest);
        }

        return tests;
    }

}
