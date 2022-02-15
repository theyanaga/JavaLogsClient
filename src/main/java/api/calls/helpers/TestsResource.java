package api.calls.helpers;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import parsing.entities.*;
import parsing.entities.projections.NameOfTest;
import parsing.entities.projections.UserId;
import parsing.relations.TestName;

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

    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public List<AccumulatedResultsByTest> test(){
        List<NameOfTest> names = TestName.findAll().project(NameOfTest.class).list();

        List<AccumulatedResultsByTest> tests = new ArrayList<>();

        for (NameOfTest nameOfTest : names) {
            AccumulatedResultsByTest accumulatedResultsByTest = AccumulatedResultsByTest.of(nameOfTest.getName());

            TestName testName = TestName.find("name", nameOfTest.getName()).firstResult();

            PanacheQuery<LocalTest> passedTestQuery = LocalTest.find("test_name_id = ?1 and status = ?2", testName.id, TestStatus.PASS);
            PanacheQuery<LocalTest> partiallyPassedTestQuery  = LocalTest.find("test_name_id = ?1 and status = ?2", testName.id, TestStatus.PARTIAL);
            PanacheQuery<LocalTest> failedTestQuery  = LocalTest.find("test_name_id = ?1 and status = ?2", testName.id, TestStatus.FAIL);
            PanacheQuery<LocalTest> untestedTestQuery  = LocalTest.find("test_name_id = ?1 and status = ?2", testName.id, TestStatus.UNTESTED);

            accumulatedResultsByTest.setNumberOfUsersThatPassed( (int) passedTestQuery.count());
            accumulatedResultsByTest.setNumberOfUsersThatPartiallyPassed((int) partiallyPassedTestQuery.count());
            accumulatedResultsByTest.setNumberOfUsersThatFailed((int) failedTestQuery.count());
            accumulatedResultsByTest.setNumberOfUsersThatDidNotRunTest((int) untestedTestQuery.count());

            List<UserId> userPassedIds = passedTestQuery.page(Page.ofSize(3)).project(UserId.class).list();
            List<UserId> userPartiallyPassedIds = partiallyPassedTestQuery.page(Page.ofSize(3)).project(UserId.class).list();
            List<UserId> userFailedIds = failedTestQuery.page(Page.ofSize(3)).project(UserId.class).list();
            List<UserId> userUntestedIds = untestedTestQuery.page(Page.ofSize(3)).project(UserId.class).list();

            accumulatedResultsByTest.setUsersThatPassed(User.findUsersFromId(userPassedIds));
            accumulatedResultsByTest.setUsersThatPartiallyPassed(User.findUsersFromId(userPartiallyPassedIds));
            accumulatedResultsByTest.setUsersThatFailed(User.findUsersFromId(userFailedIds));
            accumulatedResultsByTest.setUsersThatDidNotRunTest(User.findUsersFromId(userUntestedIds));

            tests.add(accumulatedResultsByTest);
        }

        return tests;
    }

}
