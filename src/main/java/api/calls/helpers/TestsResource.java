package api.calls.helpers;

import parsing.entities.LocalChecksTest;
import parsing.entities.TestResultByUsers;
import parsing.entities.User;
import parsing.entities.UserWithTests;

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

    @Inject
    AResource aResource;

    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public List<TestResultByUsers> getTestResultsByUser() {
        List<UserWithTests> usersWithTests = aResource.getAllUsersAndTheirTests();

        Map<String, TestResultByUsers> testResultMap = new HashMap<>();

        for (UserWithTests userWithTests : usersWithTests) {
            for (LocalChecksTest test : userWithTests.getTests()) {
                String testName = test.getName();
                if (testResultMap.containsKey(testName)) {
                    testResultMap.get(testName).insertUserTestResult(test, userWithTests.getUser());
                }
                else {
                    TestResultByUsers aTestResult = TestResultByUsers.of(testName);
                    aTestResult.insertUserTestResult(test, userWithTests.getUser());
                    testResultMap.put(testName, aTestResult);
                }
            }
         }

        return testResultMap.values().stream().collect(Collectors.toList());
    }

}
