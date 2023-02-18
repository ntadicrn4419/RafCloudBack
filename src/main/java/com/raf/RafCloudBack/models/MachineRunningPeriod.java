package com.raf.RafCloudBack.models;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
@Entity
public class MachineRunningPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Date dateStarted;

    @Column
    private Date dateStopped;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "machine_id")
    @NotNull
    private Machine machine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(Date dateWhenStarted) {
        this.dateStarted = dateWhenStarted;
    }

    public Date getDateStopped() {
        return dateStopped;
    }

    public void setDateStopped(Date dateWhenStopped) {
        this.dateStopped = dateWhenStopped;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }
}
