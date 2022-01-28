package gradingTools.gradingTools.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import gradingTools.entities.ServerInput;

public class ServerInputWrapper {

    private static final String LOG_TYPE = "LocalChecksLog";
    private static final String PASSWORD = "password";
    private static final String COURSE_ID = "comp524f21_assignment2_F21Assignment2Suite";
    private static final int LIMIT = 2;

    @JsonProperty("body")
    private gradingTools.entities.ServerInput serverInput;

    public static ServerInputWrapper createServerRequest(int skip) {

        ServerInputWrapper wrapper = new ServerInputWrapper();

        gradingTools.entities.ServerInput serverInput = new gradingTools.entities.ServerInput();

        serverInput.setCourseId(COURSE_ID);
        serverInput.setLimit(LIMIT);
        serverInput.setLogType(LOG_TYPE); // Ask Andrew about test logs.
        serverInput.setSkip(skip); // Label for assignment and concurrency
        serverInput.setPassword(PASSWORD);

        wrapper.setBody(serverInput);

        return wrapper;
    }

    public gradingTools.entities.ServerInput getBody() {
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
