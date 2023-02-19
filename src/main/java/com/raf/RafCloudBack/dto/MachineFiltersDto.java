package com.raf.RafCloudBack.dto;

import java.sql.Timestamp;

public class MachineFiltersDto {
    private String name;
    private String status;
    private Timestamp runningStarted;
    private Timestamp runningStopped;
    private Timestamp createdAtLowerBound;
    private Timestamp createdAtUpperBound;

    public MachineFiltersDto() {
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getRunningStarted() {
        return runningStarted;
    }

    public Timestamp getRunningStopped() {
        return runningStopped;
    }

    public Timestamp getCreatedAtLowerBound() {
        return createdAtLowerBound;
    }

    public void setCreatedAtLowerBound(Timestamp createdAtLowerBound) {
        this.createdAtLowerBound = createdAtLowerBound;
    }

    public Timestamp getCreatedAtUpperBound() {
        return createdAtUpperBound;
    }

    public void setCreatedAtUpperBound(Timestamp createdAtUpperBound) {
        this.createdAtUpperBound = createdAtUpperBound;
    }
}
