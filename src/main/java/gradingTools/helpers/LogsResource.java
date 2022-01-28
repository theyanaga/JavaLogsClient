package gradingTools.helpers;

import gradingTools.gradingTools.entities.ServerInputWrapper;
import gradingTools.entities.ServerOutputWrapper;
import helpers.LogsService;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.*;

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



}
