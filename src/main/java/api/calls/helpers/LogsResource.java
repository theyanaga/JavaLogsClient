package api.calls.helpers;

import api.calls.entities.CSVIndexes;
import api.calls.entities.ServerInputWrapper;
import api.calls.entities.ServerOutputWrapper;
import gradingTools.logs.LocalChecksLogData;
import gradingTools.logs.localChecksStatistics.compiledLogGenerator.LocalLogDataAnalyzer;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import parsing.entities.RowFromServer;
import parsing.entities.Users;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.util.Arrays;
import java.util.List;

@Path("/get-cyverse-log")
public class LogsResource {

    public static final String MY_PATH = "/Users/felipeyanaga/UNC/Research/LocalCheckLogs/";
    public static final String FILE_TYPE = ".csv";

    @Inject
    @RestClient
    LogsService logsService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ClientHeaderParam(name="Connection", value="keep-alive")
    @ClientHeaderParam(name="Content-Type", value="application/json")
    public ServerOutputWrapper getLogs(ServerInputWrapper serverRequestBodyWrapper) throws IOException {

        ServerOutputWrapper serverOutputWrapper = logsService.getLogs(serverRequestBodyWrapper);

        return serverOutputWrapper;
    }

    @GET
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ClientHeaderParam(name="Connection", value="keep-alive")
    @ClientHeaderParam(name="Content-Type", value="application/json")
    public RowFromServer testPersist() {
        ServerInputWrapper serverInputWrapper = ServerInputWrapper.createServerRequest(0);
        ServerOutputWrapper wrapper = logsService.getLogs(serverInputWrapper);

        String[] csvLine = wrapper.getLogs().get(0).getLog().getJson().split(",");

        System.out.println(Arrays.toString(csvLine));

        Users users = new Users(wrapper.getLogs().get(0).getMachineId());

        users.persist();

        RowFromServer serverRow = new RowFromServer.Builder().row(csvLine[CSVIndexes.ROW.getIndex()])
                .user(users)
                .time(csvLine[CSVIndexes.TIME.getIndex()])
                .percentPassed(csvLine[CSVIndexes.PERCENT_PASSES.getIndex()])
                .percentPassed(csvLine[CSVIndexes.CHANGE.getIndex()])
                .testName(csvLine[CSVIndexes.PERCENT_PASSES.getIndex()])
                .passedTests(csvLine[CSVIndexes.PASSED_TESTS.getIndex()])
                .partialTests(csvLine[CSVIndexes.PARTIAL_TESTS.getIndex()])
                .failedTests(csvLine[CSVIndexes.FAILED_TESTS.getIndex()])
                .untestedTests(csvLine[CSVIndexes.UNTESTED_TESTS.getIndex()])
                .sessionNumber(csvLine[CSVIndexes.SESSION_NUMBER.getIndex()])
                .sessionRunNumber(csvLine[CSVIndexes.SESSION_RUN_NUMBER.getIndex()])
                .isSuite(csvLine[CSVIndexes.IS_SUITE.getIndex()])
                .suiteTests(csvLine[CSVIndexes.SUITE_TESTS.getIndex()])
                .prerequisiteTests(csvLine[CSVIndexes.PREREQUISITE_TESTS.getIndex()])
                .extraCreditTests(csvLine[CSVIndexes.EXTRA_CREDIT_TESTS.getIndex()])
                .testScores(csvLine[CSVIndexes.TEST_SCORES.getIndex()])
                .failFromPrerequisite(csvLine[CSVIndexes.FAIL_FROM_PREREQUISITE.getIndex()])
                .build();

        serverRow.persist();

        List<RowFromServer> list = RowFromServer.listAll();

        for (RowFromServer row : list) {
            System.out.println(row.getPassedTests());
        }

        RowFromServer rowFromServer = RowFromServer.find("user_id", 1).firstResult();

        return LocalLogDataAnalyzer.getData()
    }



}
