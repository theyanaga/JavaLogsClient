package parsing.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import parsing.entities.projections.UserId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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

    @Transactional
    public static List<User> findUsersFromId(List<UserId> ids) {
        List<User> users = new ArrayList<>();

        for (UserId id : ids) {
            users.add(User.findById(id.getUserId()));
        }

        return users;
    }

    @Override
    public String toString() {
        return "User{" +
                "machineId='" + machineId + '\'' +
                ", id=" + id +
                '}';
    }
}
