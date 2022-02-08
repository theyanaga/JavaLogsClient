package api.calls.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import gradingTools.logs.localChecksStatistics.collectors.Collector;
import gradingTools.logs.localChecksStatistics.collectors.StandardCollectors.AttemptsCollectorV2;
import gradingTools.logs.localChecksStatistics.collectors.StandardCollectors.FinalStatusCollector;
import gradingTools.logs.localChecksStatistics.compiledLogGenerator.CollectorManager;
import gradingTools.logs.localChecksStatistics.compiledLogGenerator.LocalLogDataAnalyzer;
import parsing.entities.LocalChecksTest;
import parsing.entities.RowFromServer;
import parsing.entities.RowFromServerRepository;
import parsing.entities.User;
import parsing.entities.UserWithTests;
import parsing.helpers.AndrewOutputProcessor;

@Path("/potato")
public class AResource {

    @Inject
    RowFromServerRepository rowFromServerRepository;

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


        users.add(UserWithTests.of(User.findById( (long) 1), AndrewOutputProcessor.processInput(LocalLogDataAnalyzer.runEvaluationFromDatabase(lines, cm))));

        return users;
    }

    @Path("/salad")
    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getTestNames() {
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

        List<LocalChecksTest> tests = AndrewOutputProcessor.processInput(LocalLogDataAnalyzer.runEvaluationFromDatabase(lines, cm));

        return tests.stream().map(LocalChecksTest::getName).collect(Collectors.toList());
    }

    @Path("/allUsers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserWithTests> getAllUsersAndTheirTests() {
        return rowFromServerRepository.findCsvLineByUserMap().entrySet().stream()
            .map(entry -> {
                CollectorManager cm = new CollectorManager(new Collector[] {new AttemptsCollectorV2(), new FinalStatusCollector()});
                return UserWithTests.of(
                    entry.getKey(), 
                    AndrewOutputProcessor.processInput(LocalLogDataAnalyzer.runEvaluationFromDatabase(entry.getValue(), cm)));
            })
            .collect(Collectors.toList());
    }

}
