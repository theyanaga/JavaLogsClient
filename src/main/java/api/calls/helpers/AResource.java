package api.calls.helpers;

import api.calls.entities.Log;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/potato")
public class AResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Log test() {
        Log log = new Log();
        log.setJson("test");
        return log;
    }

}
