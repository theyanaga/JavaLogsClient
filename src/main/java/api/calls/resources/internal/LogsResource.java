package api.calls.resources.internal;

import api.calls.entities.ServerInputWrapper;
import api.calls.entities.ServerOutputWrapper;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import parsing.entities.*;
import parsing.relations.Assignment;
import parsing.relations.Course;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.*;

/**
 * Use this class to retrieve the logs from the server
 */
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

        long skip = 0;

        for(int i = 0; i < 2; i++) {
            ServerOutputWrapper wrapper = logsService.getLogs(ServerInputWrapper.createServerRequest(
                    (int) (skip + i)
            ));

            String[] csvLine = wrapper.getLogs().get(0).getLog().getJson().split(",");

            User users = User.of(wrapper.getLogs().get(0).getMachineId());

            Course course = Course.of(Course.JAVA_THREADS, Season.SUMMER, 2022);

            Assignment assignment = Assignment.of(1, course);

            RowFromServer serverRow = RowFromServer.of(users, assignment, csvLine);
            serverRow.persistAndFlush();
        }

        return 0;
    }



}
