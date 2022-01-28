package gradingTools.helpers;

import gradingTools.entities.ServerOutputWrapper;
import gradingTools.gradingTools.entities.ServerInputWrapper;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/get-cyverse-log")
@RegisterRestClient
public interface LogsService {

    @POST
    @ClientHeaderParam(name="Connection", value="keep-alive")
    @ClientHeaderParam(name="Content-Type", value="application/json")
    ServerOutputWrapper getLogs(ServerInputWrapper serverRequestBodyWrapper);
}
