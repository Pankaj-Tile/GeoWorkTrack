package com.smart.entity;


import java.sql.Date;
import java.sql.Time;

import javax.persistence.*;

@Entity
@Table(name="UserHistory")
public class UserHistory {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer HistoryId;
	private Integer userId ;
    private Integer geofenceId;
    private String userName;
    private String geofenceName;
    private Date date;
    private Time entryTime;
     private Time exitTime;
    
    public Integer getGeofenceId() {
        return geofenceId;
    }
    public String getGeofenceName() {
        return geofenceName;
    }
    public Integer getHistoryId() {
        return HistoryId;
    }
    public Integer getUserId() {
        return userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setGeofenceId(Integer geofenceId) {
        this.geofenceId = geofenceId;
    }
    public void setGeofenceName(String geofenceName) {
        this.geofenceName = geofenceName;
    }
    public void setHistoryId(Integer historyId) {
        HistoryId = historyId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Date getDate() {
        return date;
    }
    public Time getEntryTime() {
        return entryTime;
    }
    public Time getExitTime() {
        return exitTime;
    }
    public void setEntryTime(Time entryTime) {
        this.entryTime = entryTime;
    }
    public void setExitTime(Time exitTime) {
        this.exitTime = exitTime;
    }
   
}
