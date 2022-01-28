package gradingTools.gradingTools.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerInput {

    @JsonProperty("log_type")
    public String logType;

    @JsonProperty("password")
    public String password;

    public int skip;

    public int limit;

    @JsonProperty("course_id")
    public String courseId;

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "Body{" +
                "logType='" + logType + '\'' +
                ", password='" + password + '\'' +
                ", skip=" + skip +
                ", limit=" + limit +
                ", courseId='" + courseId + '\'' +
                '}';
    }
}
