package gradingTools.gradingTools.entities;

import gradingTools.entities.ServerOutput;

import java.util.List;

public class ServerOutputWrapper {

    private List<gradingTools.entities.ServerOutput> logs;

    public List<gradingTools.entities.ServerOutput> getLogs() {
        return logs;
    }

    public void setLogs(List<ServerOutput> logs) {
        this.logs = logs;
    }
}
