package api.calls.helpers;

import api.calls.entities.Log;
import gradingTools.logs.localChecksStatistics.collectors.Collector;
import gradingTools.logs.localChecksStatistics.collectors.StandardCollectors.AttemptsCollectorV2;
import gradingTools.logs.localChecksStatistics.collectors.StandardCollectors.FinalStatusCollector;
import gradingTools.logs.localChecksStatistics.compiledLogGenerator.CollectorManager;
import gradingTools.logs.localChecksStatistics.compiledLogGenerator.LocalLogDataAnalyzer;
import io.quarkus.panache.common.Page;
import parsing.entities.LocalChecksTest;
import parsing.entities.RowFromServer;
import parsing.entities.User;
import parsing.entities.UserWithTests;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import parsing.entities.*;
import parsing.helpers.AndrewOutputProcessor;

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
        List<RowFromServer> rows = RowFromServer.find("user_id", 1).list();

        Collector[] collectors = {
                new AttemptsCollectorV2(),
                new FinalStatusCollector(),
        };

        CollectorManager cm = new CollectorManager(collectors);

        List<String> lines = new ArrayList<>();

        for (RowFromServer row : rows)  {
            lines.add(row.createCSVLineFromRow());
        }

        List<UserWithTests> users = new ArrayList<>();


        users.add(UserWithTests.of(User.findById( (long) 1), AndrewOutputProcessor.processInput(LocalLogDataAnalyzer.runEvaluationFromDatabase(lines, cm),
                Assignment.findById( (long) 3))));

        return users;
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
        PanacheQuery<User> usersQuery = User.findAll();

        List<User> users = usersQuery.page(Page.ofSize(20)).list();

        List<UserWithTests> userWithTests = new ArrayList<>();

        for (User user : users) {
            List<LocalTest> tests = LocalTest.find("user_id", user.id).list();
            userWithTests.add(UserWithTests.of(
                    user, tests
            ));
        }

        return userWithTests;
    }
}
