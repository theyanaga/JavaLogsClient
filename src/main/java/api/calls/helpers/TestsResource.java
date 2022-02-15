package api.calls.helpers;

import com.arjuna.ats.internal.jdbc.drivers.modifiers.list;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import parsing.entities.*;
import parsing.entities.projections.*;
import parsing.relations.TestName;
import parsing.relations.TestNameRelation;
import parsing.relations.TestStatusRelation;
import parsing.relations.TestUserRelation;

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
//        List<NameOfTest> names = TestName.findAll().project(NameOfTest.class).list();
//
//        List<AccumulatedResultsByTest> tests = new ArrayList<>();
//
//        for (NameOfTest nameOfTest : names) {
//            AccumulatedResultsByTest accumulatedResultsByTest = AccumulatedResultsByTest.of(nameOfTest.getName());
//
//            TestName testName = TestName.find("name", nameOfTest.getName()).firstResult();
//
//            PanacheQuery<LocalTest> passedTestQuery = LocalTest.find("test_name_id = ?1 and status = ?2", testName.id, TestStatus.PASS);
//            PanacheQuery<LocalTest> partiallyPassedTestQuery  = LocalTest.find("test_name_id = ?1 and status = ?2", testName.id, TestStatus.PARTIAL);
//            PanacheQuery<LocalTest> failedTestQuery  = LocalTest.find("test_name_id = ?1 and status = ?2", testName.id, TestStatus.FAIL);
//            PanacheQuery<LocalTest> untestedTestQuery  = LocalTest.find("test_name_id = ?1 and status = ?2", testName.id, TestStatus.UNTESTED);
//
//            accumulatedResultsByTest.setNumberOfUsersThatPassed( (int) passedTestQuery.count());
//            accumulatedResultsByTest.setNumberOfUsersThatPartiallyPassed((int) partiallyPassedTestQuery.count());
//            accumulatedResultsByTest.setNumberOfUsersThatFailed((int) failedTestQuery.count());
//            accumulatedResultsByTest.setNumberOfUsersThatDidNotRunTest((int) untestedTestQuery.count());
//
//            List<UserId> userPassedIds = passedTestQuery.range(0, 2).project(UserId.class).list();
//            List<UserId> userPartiallyPassedIds = partiallyPassedTestQuery.range(0, 2).project(UserId.class).list();
//            List<UserId> userFailedIds = failedTestQuery.range(0, 2).project(UserId.class).list();
//            List<UserId> userUntestedIds = untestedTestQuery.range(0, 2).project(UserId.class).list();
//
//            accumulatedResultsByTest.setUsersThatPassed(User.findUsersFromId(userPassedIds));
//            accumulatedResultsByTest.setUsersThatPartiallyPassed(User.findUsersFromId(userPartiallyPassedIds));
//            accumulatedResultsByTest.setUsersThatFailed(User.findUsersFromId(userFailedIds));
//            accumulatedResultsByTest.setUsersThatDidNotRunTest(User.findUsersFromId(userUntestedIds));
//
//            tests.add(accumulatedResultsByTest);
//        }
//
//        return tests;

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
