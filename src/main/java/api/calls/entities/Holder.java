package api.calls.entities;

public class Holder {
    public String name;
    public String status;

    public Holder(String name, String status) {
        this.name = name;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Holder{" +
                "name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
