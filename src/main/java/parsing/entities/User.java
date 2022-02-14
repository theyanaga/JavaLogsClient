package parsing.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name="users")
public class User extends PanacheEntity {

    @Column(name="machine_id")
    private String machineId;

    public String firstName;

    public String lastName;

    public User(String machineId) {
        this.machineId = machineId;
    }

    public User() {
    }

    public static User of(String aMachineId) {
        User aUser = User.find("machine_id", aMachineId).firstResult();
        return Objects.requireNonNullElseGet(aUser, () -> new User(aMachineId));
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "machineId='" + machineId + '\'' +
                ", id=" + id +
                '}';
    }
}
