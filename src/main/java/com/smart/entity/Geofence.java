package com.smart.entity;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Entity
@Table(name = "geofences")
public class Geofence {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String geofenceplace;

    public Geofence() {
    }

    public Geofence(String geofenceplace, Double latitude, Double longitude, Double radius, LocalTime officeHoursStart,
            LocalTime officeHoursEnd, User admin) {
        this.geofenceplace = geofenceplace;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.officeHoursStart = officeHoursStart;
        this.officeHoursEnd = officeHoursEnd;
        this.admin = admin;
    }

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Double radius;  // Geofence radius in meters
    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "office_hours_start", nullable = false)
    private LocalTime officeHoursStart;
    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "office_hours_end", nullable = false)
    private LocalTime officeHoursEnd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;  // The admin who set the geofence

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGeofenceplace() {
        return geofenceplace;
    }

    public void setGeofenceplace(String geofenceplace) {
        this.geofenceplace = geofenceplace;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
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

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }




    
}
