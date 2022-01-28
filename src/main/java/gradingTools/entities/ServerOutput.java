package gradingTools.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import gradingTools.gradingTools.entities.Log;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerOutput {

    @JsonProperty("_id")
    private String id;

    @JsonProperty("_rev")
    private String rev;

    @JsonProperty("machine_id")
    private String machineId;

    @JsonProperty("course_id")
    private String courseId;

    @JsonProperty("created_timestamp")
    private String createdTimeStamp;

    @JsonProperty("log")
    private gradingTools.gradingTools.entities.Log log;

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(String createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public gradingTools.gradingTools.entities.Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "LogOutput{" +
                "id='" + id + '\'' +
                ", rev='" + rev + '\'' +
                ", courseId='" + courseId + '\'' +
                ", createdTimeStamp='" + createdTimeStamp + '\'' +
                ", log=" + log +
                '}';
    }


}
