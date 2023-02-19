package com.raf.RafCloudBack.models;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotNull
    private MachineStatus status;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User user;
    @NotNull
    @Column
    @NotNull
    private boolean active;

    @Column(unique = true)
    @NotNull
    private String name;

    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MachineRunningPeriod> runningPeriods = new ArrayList<>();

    @Column
    @NotNull
    private Timestamp createdAt;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MachineRunningPeriod> getRunningPeriods() {
        return runningPeriods;
    }

    public void setRunningPeriods(List<MachineRunningPeriod> runningPeriods) {
        this.runningPeriods = runningPeriods;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
