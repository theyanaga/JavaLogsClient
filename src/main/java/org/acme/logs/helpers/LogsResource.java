package org.acme.logs.helpers;

import org.acme.logs.entities.ServerInputWrapper;
import org.acme.logs.entities.ServerOutputWrapper;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.*;

@Path("/get-cyverse-log")
public class LogsResource {

    public static final String MY_PATH = "/Users/felipeyanaga/UNC/Research/Logs/";

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

        File directory = new File(MY_PATH + LogsHelper.formatMachineIdToUserId(serverOutputWrapper.getLogs().get(0).getMachineId()));

        int fileNumber;

        if (!directory.exists()) {
            directory.mkdir();
            fileNumber = 0;
        }
        else {
            if (directory.listFiles() == null) {
                fileNumber = 0;
            }
            else {
                fileNumber = directory.listFiles().length;
            }
        }

        FileOutputStream stream = new FileOutputStream(MY_PATH +
                LogsHelper.formatMachineIdToUserId(serverOutputWrapper.getLogs().get(0).getMachineId())
                + "/" + LogsHelper.formatMachineIdToUserId(serverOutputWrapper.getLogs().get(0).getMachineId())
                + "." + fileNumber + ".xml");

        String log = serverOutputWrapper.getLogs().get(0).getLog().getJson();

        String logWithRemovedQuotation = LogsHelper.deleteUselesQuotationMark(log);

        stream.write(logWithRemovedQuotation.getBytes());

        stream.close();

        System.out.println("we got here");

        return serverOutputWrapper;
    }



}
