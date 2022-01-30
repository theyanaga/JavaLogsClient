package parsing.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Users extends PanacheEntity {

    @Column(name="machine_id")
    private String machineId;

    public Users(String machineId) {
        this.machineId = machineId;
    }

    public Users() {
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }
}
