package parsing.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Users extends PanacheEntity {

    @Column(name="machine_id")
    private String machineId;

    public Users(String machineId) {
        this.machineId = machineId;
    }

    public Users() {
    }

    public static Users of(String aMachineId) {
        Users aUser = Users.find("machine_id", aMachineId).firstResult();
        return Objects.requireNonNullElseGet(aUser, () -> new Users(aMachineId));
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }
}
