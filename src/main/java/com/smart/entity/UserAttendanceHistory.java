package com.smart.entity;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.*;

@Entity
@Table(name = "USER_HISTORY")
public class UserAttendanceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long externalId;
    private Integer userId;
    private String username;
    private Long geofenceId; 
    private String geofenceName;
    private Date entryDate;
    private Time entryTime;
    private Date exitDate;
    private Time exitTime;
    private boolean status = false; // Default value set to false

    // Default constructor
    public UserAttendanceHistory() {
    }

    // Parameterized constructor
    public UserAttendanceHistory(Long externalId, Integer userId, String username, Long geofenceId, String geofenceName, Date entryDate, Time entryTime, Date exitDate, Time exitTime, boolean status) {
        this.externalId = externalId;
        this.userId = userId;
        this.username = username;
        this.geofenceId = geofenceId;
        this.geofenceName = geofenceName;
        this.entryDate = entryDate;
        this.entryTime = entryTime;
        this.exitDate = exitDate;
        this.exitTime = exitTime;
        this.status = status;
    }

    // Getters and Setters
    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getGeofenceId() {
        return geofenceId;
    }

    public void setGeofenceId(Long geofenceId) {
        this.geofenceId = geofenceId;
    }

    public String getGeofenceName() {
        return geofenceName;
    }

    public void setGeofenceName(String geofenceName) {
        this.geofenceName = geofenceName;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Time getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Time entryTime) {
        this.entryTime = entryTime;
    }

    public Date getExitDate() {
        return exitDate;
    }

    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }

    public Time getExitTime() {
        return exitTime;
    }

    public void setExitTime(Time exitTime) {
        this.exitTime = exitTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
