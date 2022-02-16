package api.calls.helpers;

import api.calls.entities.Holder;
import parsing.entities.User;
import parsing.entities.UserWithTests;
import parsing.entities.*;
import parsing.entities.projections.LocalTestNameAndStatus;
import parsing.relations.TestName;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/potato")
public class AResource {

    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserWithTests> test() {
//        List<RowFromServer> rows = RowFromServer.find("user_id", 1).list();
//
//        Collector[] collectors = {
//                new AttemptsCollectorV2(),
//                new FinalStatusCollector(),
//        };
//
//        CollectorManager cm = new CollectorManager(collectors);
//
//        List<String> lines = new ArrayList<>();
//
//        for (RowFromServer row : rows)  {
//            lines.add(row.createCSVLineFromRow());
//        }
//
//        List<UserWithTests> users = new ArrayList<>();
//
//
//        users.add(UserWithTests.of(User.findById( (long) 1), AndrewOutputProcessor.processInput(LocalLogDataAnalyzer.runEvaluationFromDatabase(lines, cm),
//                Assignment.findById( (long) 3))));

        return null;
    }

    @Path("/salad")
    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getTestNames() {
        List<TestName> testNames = TestName.listAll();

        return testNames.stream().map(TestName::getName).collect(Collectors.toList());
    }

    @Path("/allUsers")
    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserWithTests> getAllUsersAndTheirTests() {

        List<User> users = User.listAll();

        List<UserWithTests> userWithTests = new ArrayList<>();

        for (User user : users) {
            List<LocalTestNameAndStatus> tests = LocalTest.find("user_id", user.id).project(LocalTestNameAndStatus.class).list();
            List<Holder> elements = new ArrayList<>();
            for (LocalTestNameAndStatus a : tests) {
                TestName testName = TestName.findById(a.getTestNameId());
                Holder holder = new Holder(testName.getName(), a.getStatus().toString());
                elements.add(holder);
            }

            userWithTests.add(
                    UserWithTests.of(user, elements)
            );
        }

        return userWithTests;


    }
}
