package com.raf.RafCloudBack.dto;

import java.sql.Timestamp;

public class MachineScheduleDto {
    private Long id;
    private Timestamp datetime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }
}
