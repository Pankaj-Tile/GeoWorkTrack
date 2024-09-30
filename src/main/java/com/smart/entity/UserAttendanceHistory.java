package com.smart.entity;
import javax.persistence.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;

@Entity
@Table(name = "user_attendance_history")
public class UserAttendanceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate;  // Date of attendance

    @Column(name = "entry_time", nullable = false)
    private LocalTime entryTime;  // Time of entry into geofence

    @Column(name = "exit_time", nullable = false)
    private LocalTime exitTime;  // Time of exit from geofence

    @Column(name = "presence_percentage", nullable = false)
    private Double presencePercentage;  // Presence percentage for the day

    @Column(name = "overtime", nullable = false)
    private boolean overtime;  // True if overtime was worked

    @Column(name = "overtime_duration", nullable = true)
    private Duration overtimeDuration;  // Overtime duration (actual working time - office hours)

    @Column(name = "office_hours_start", nullable = false)
    private LocalTime officeHoursStart;  // Office hours start time (set by admin)

    @Column(name = "office_hours_end", nullable = false)
    private LocalTime officeHoursEnd;  // Office hours end time (set by admin)

    @Column(name = "status", nullable = false)
    private String status;  // Status (e.g., Present, Late, Left Early, etc.)

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public LocalTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalTime entryTime) {
        this.entryTime = entryTime;
    }

    public LocalTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalTime exitTime) {
        this.exitTime = exitTime;
    }

    public Double getPresencePercentage() {
        return presencePercentage;
    }

    public void setPresencePercentage(Double presencePercentage) {
        this.presencePercentage = presencePercentage;
    }

    public boolean isOvertime() {
        return overtime;
    }

    public void setOvertime(boolean overtime) {
        this.overtime = overtime;
    }

    public Duration getOvertimeDuration() {
        return overtimeDuration;
    }

    public void setOvertimeDuration(Duration overtimeDuration) {
        this.overtimeDuration = overtimeDuration;
    }

    public LocalTime getOfficeHoursStart() {
        return officeHoursStart;
    }

    public void setOfficeHoursStart(LocalTime officeHoursStart) {
        this.officeHoursStart = officeHoursStart;
    }

    public LocalTime getOfficeHoursEnd() {
        return officeHoursEnd;
    }

    public void setOfficeHoursEnd(LocalTime officeHoursEnd) {
        this.officeHoursEnd = officeHoursEnd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
