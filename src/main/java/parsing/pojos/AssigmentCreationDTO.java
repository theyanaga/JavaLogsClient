package parsing.pojos;

public class AssigmentCreationDTO {

    int assignmentNumber;
    int year;
    String className;
    String season;

    public AssigmentCreationDTO(int assignmentNumber, int year, String className, String season) {
        this.assignmentNumber = assignmentNumber;
        this.year = year;
        this.className = className;
        this.season = season.toUpperCase();
    }

    public int getAssignmentNumber() {
        return assignmentNumber;
    }

    public void setAssignmentNumber(int assignmentNumber) {
        this.assignmentNumber = assignmentNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String aClassName) {
        this.className = aClassName;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String aSeason) {
        this.season = aSeason;
    }
}
