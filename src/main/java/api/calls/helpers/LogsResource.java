package api.calls.helpers;

import api.calls.entities.ServerInputWrapper;
import api.calls.entities.ServerOutputWrapper;
import gradingTools.logs.localChecksStatistics.collectors.Collector;
import gradingTools.logs.localChecksStatistics.collectors.StandardCollectors.AttemptsCollectorV2;
import gradingTools.logs.localChecksStatistics.collectors.StandardCollectors.FinalStatusCollector;
import gradingTools.logs.localChecksStatistics.compiledLogGenerator.CollectorManager;
import gradingTools.logs.localChecksStatistics.compiledLogGenerator.LocalLogDataAnalyzer;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import parsing.entities.*;
import parsing.helpers.AndrewOutputProcessor;
import parsing.relations.Assignment;
import parsing.relations.Course;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Path("/get-cyverse-log")
public class LogsResource {

    public static final String MY_PATH = "/Users/felipeyanaga/UNC/Research/LocalCheckLogs/";
    public static final String FILE_TYPE = ".csv";

    @Inject
    @RestClient
    LogsService logsService;

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ClientHeaderParam(name="Connection", value="keep-alive")
    @ClientHeaderParam(name="Content-Type", value="application/json")
    public int persistLogs() throws IOException {

        long skip = RowFromServer.count();

        for(int i = 0; i < 100; i++) {
            ServerOutputWrapper wrapper = logsService.getLogs(ServerInputWrapper.createServerRequest(
                    (int) (skip + i)
            ));

            String[] csvLine = wrapper.getLogs().get(0).getLog().getJson().split(",");

            User users = User.of(wrapper.getLogs().get(0).getMachineId());

            Course course = Course.of(Course.COMP_524, Season.FALL, 2021);

            Assignment assignment = Assignment.of(2, course);

            RowFromServer serverRow = RowFromServer.of(users, assignment, csvLine);
            serverRow.persistAndFlush();
        }

        return 0;
    }

    @Path("/persist")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ClientHeaderParam(name="Connection", value="keep-alive")
    @ClientHeaderParam(name="Content-Type", value="application/json")
    public boolean persistRowsFromServer(long skip) {

        ServerOutputWrapper wrapper = logsService.getLogs(ServerInputWrapper.createServerRequest( (int) skip));

        String[] csvLine = wrapper.getLogs().get(0).getLog().getJson().split(",");

        User users = User.of(wrapper.getLogs().get(0).getMachineId());

        Course course = Course.of(Course.COMP_524, Season.FALL, 2021);

        Assignment assignment = Assignment.of(2, course);

        RowFromServer serverRow = RowFromServer.of(users, assignment, csvLine);
        serverRow.persist();

        return true;
    }

    @GET
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ClientHeaderParam(name="Connection", value="keep-alive")
    @ClientHeaderParam(name="Content-Type", value="application/json")
    public UserWithTests testPersist() {

        // add difference in logs before vs after

//        ServerInputWrapper serverInputWrapper = ServerInputWrapper.createServerRequest(1);
//        ServerOutputWrapper wrapper = logsService.getLogs(serverInputWrapper);
//
//        String[] csvLine = wrapper.getLogs().get(0).getLog().getJson().split(",");
//
//        User user = User.of(wrapper.getLogs().get(0).getMachineId());
//
//        Course course = Course.of(Course.COMP_524, Season.FALL, 2021);
//
//        Assignment assignment = Assignment.of(2, course);
//
//        RowFromServer serverRow = RowFromServer.of(user, assignment, csvLine);
//        serverRow.persist();
//
//        RowFromServer rowFromServer = RowFromServer.find("user_id", 1).firstResult();
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
//        lines.add(rowFromServer.createCSVLineFromRow());
//        return UserWithTests.of(User.findById(1), AndrewOutputProcessor.processInput(
//                LocalLogDataAnalyzer.runEvaluationFromDatabase(lines, cm),
//                Assignment.findById(3))
//        );
        return null;
    }



}
