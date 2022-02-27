package parsing.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import parsing.entities.projections.LocalTestNameAndStatusWithUserId;
import parsing.entities.projections.UserId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Entity
@Table(name="users")
public class User extends PanacheEntity {

    private static final int USERS_TO_DISPLAY = 3;

    @Column(name="machine_id")
    private String machineId;

    public String firstName;

    public String lastName;

    @Transient
    private String fullName;

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

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    @Transactional
    public static List<User> findUsersfromList(List<LocalTestNameAndStatusWithUserId> tests) {
        int counter = 3;
        if (tests.isEmpty()) {
            return new ArrayList<>();
        }
        else {
            if (tests.size() < counter) {
                counter = tests.size();
            }
        }
        return IntStream.range(0, counter).<User>mapToObj(i -> User.findById(tests.get(i).getUserId())).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "User{" +
                "machineId='" + machineId + '\'' +
                ", id=" + id +
                '}';
    }
}
