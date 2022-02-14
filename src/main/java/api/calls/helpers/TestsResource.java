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

            long numberOfUsersThatPassed = LocalTest.find("name = ?1 and status = ?2", testName, TestStatus.PASS).count();
            long numberOfUsersThatPartiallyPassed = LocalTest.find("name = ?1 and status = ?2", testName, TestStatus.PARTIAL).count();
            long numberOfUsersThatFailed = LocalTest.find("name = ?1 and status = ?2", testName, TestStatus.FAIL).count();
            long numberOfUsersThatDidNotRunTest = LocalTest.find("name = ?1 and status = ?2", testName, TestStatus.UNTESTED).count();

            accumulatedResultsByTest.setNumberOfUsersThatPassed( (int) numberOfUsersThatPassed);
            accumulatedResultsByTest.setNumberOfUsersThatPartiallyPassed((int) numberOfUsersThatPartiallyPassed);
            accumulatedResultsByTest.setNumberOfUsersThatFailed((int) numberOfUsersThatFailed);
            accumulatedResultsByTest.setNumberOfUsersThatDidNotRunTest((int) numberOfUsersThatDidNotRunTest);

//            List<LocalTest> passedTests = LocalTest.find("name = ?1 and status = ?2", testName, TestStatus.PASS).page(Page.ofSize(3)).list();
//            List<LocalTest> partiallyPassedTests = LocalTest.find("name = ?1 and status = ?2", testName, TestStatus.PARTIAL).page(Page.ofSize(3)).list();
//            List<LocalTest> failedTests = LocalTest.find("name = ?1 and status = ?2", testName, TestStatus.FAIL).page(Page.ofSize(3)).list();
//            List<LocalTest> untestedTests = LocalTest.find("name = ?1 and status = ?2", testName, TestStatus.UNTESTED).page(Page.ofSize(3)).list();
//            List<User> passedUsers = passedTests.stream().map(LocalTest::getUser_id).collect(Collectors.toList());
//            List<User> partialUsers = partiallyPassedTests.stream().map(LocalTest::getUser_id).collect(Collectors.toList());
//            List<User> failedUsers = failedTests.stream().map(LocalTest::getUser_id).collect(Collectors.toList());
//            List<User> untestedUsers = untestedTests.stream().map(LocalTest::getUser_id).collect(Collectors.toList());
//
//            accumulatedResultsByTest.setUsersThatPassed(passedUsers);
//            accumulatedResultsByTest.setUsersThatPartiallyPassed(partialUsers);
//            accumulatedResultsByTest.setUsersThatFailed(failedUsers);
//            accumulatedResultsByTest.setUsersThatDidNotRunTest(untestedUsers);

            tests.add(accumulatedResultsByTest);
        }

        return tests;
    }

}
