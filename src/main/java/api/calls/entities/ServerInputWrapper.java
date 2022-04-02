package api.calls.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerInputWrapper {

    private static final String LOG_TYPE = "LocalChecksLog";
    private static final String PASSWORD = "password";
    private static final String COURSE_ID_524 = "comp524f21_assignment2_F21Assignment2Suite";
    private static final String COURSE_ID_533 = "comp533s22_assignment2_S22Assignment2Suite";
    public static final int LIMIT = 2;

    @JsonProperty("body")
    private ServerInput serverInput;

    public static ServerInputWrapper createServerRequest(int skip) {

        ServerInputWrapper wrapper = new ServerInputWrapper();

        ServerInput serverInput = new ServerInput();

        serverInput.setCourseId(COURSE_ID_533);
        serverInput.setLimit(LIMIT);
        serverInput.setLogType(LOG_TYPE); // Ask Andrew about test logs.
        serverInput.setSkip(skip); // Label for assignment and concurrency
        serverInput.setPassword(PASSWORD);

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
