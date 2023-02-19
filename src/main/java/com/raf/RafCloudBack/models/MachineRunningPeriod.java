package com.raf.RafCloudBack.models;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
public class MachineRunningPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Timestamp dateStarted;
    @Column
    private Timestamp dateStopped;

//    @Column
//    private Date dateStarted;
//
//    @Column
//    private Date dateStopped;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "machine_id")
    @NotNull
    private Machine machine;

    public Long getId() {
        return id;
    }

    public Timestamp getDateStarted() {
        return dateStarted;
    }

    public Timestamp getDateStopped() {
        return dateStopped;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDateStarted(Timestamp dateStarted) {
        this.dateStarted = dateStarted;
    }

    public void setDateStopped(Timestamp dateStopped) {
        this.dateStopped = dateStopped;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }
}
