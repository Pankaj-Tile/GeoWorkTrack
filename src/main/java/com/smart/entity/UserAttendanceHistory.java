package com.smart.entity;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.*;


@Entity
@Table(name = "USER_HISTORY")
public class UserAttendanceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exid;
    private long id;
    private double latitude;
    private double longitude;
    private String timestamp;
    private String userId;
    private Long geofenceId; 
    private Date entryDate;
    private Time entryTime;
    private Date exitDate;
    private Time exitTime;
    private boolean status;
}
