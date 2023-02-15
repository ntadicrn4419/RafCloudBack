package com.raf.RafCloudBack.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotNull
    private MachineStatus status;
    @Column
    @NotNull
    private Long createdBy; //user foreign key id
    @Column
    @NotNull
    private boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MachineStatus getStatus() {
        return status;
    }

    public void setStatus(MachineStatus status) {
        this.status = status;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "id=" + id +
                ", status=" + status +
                ", createdBy=" + createdBy +
                ", active=" + active +
                '}';
    }
}
