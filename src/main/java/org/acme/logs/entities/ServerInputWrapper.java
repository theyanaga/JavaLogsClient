package org.acme.logs.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.models.servers.Server;
import org.jboss.jandex.ModuleInfo;

public class ServerInputWrapper {

    @JsonProperty("body")
    private ServerInput serverInput;

    public static ServerInputWrapper createServerRequest(int skip) {

        ServerInputWrapper wrapper = new ServerInputWrapper();

        ServerInput serverInput = new ServerInput();

        serverInput.setCourseId("COMP301");
        serverInput.setLimit(1);
        serverInput.setLogType("eclipse");
        serverInput.setSkip(skip);
        serverInput.setPassword("password");

        wrapper.setBody(serverInput);

        return wrapper;
    }

    public ServerInput getBody() {
        return serverInput;
    }

    public void setBody(ServerInput serverInput) {
        this.serverInput = serverInput;
    }

    @Override
    public String toString() {
        return "Test{" +
                "body=" + serverInput +
                '}';
    }

}
