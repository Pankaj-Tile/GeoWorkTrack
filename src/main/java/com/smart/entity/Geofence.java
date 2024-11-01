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

    @Column(nullable = false)
    private Double latitude; // Center latitude

    @Column(nullable = false)
    private Double longitude; // Center longitude

    @Column(nullable = false)
    private Double radius; // Geofence radius in meters

    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "office_hours_start", nullable = false)
    private LocalTime officeHoursStart;

    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "office_hours_end", nullable = false)
    private LocalTime officeHoursEnd;

    // Bounding box coordinates
    @Column(nullable = false)
    private Double latitudeA;

    @Column(nullable = false)
    private Double longitudeA;

    @Column(nullable = false)
    private Double latitudeB;

    @Column(nullable = false)
    private Double longitudeB;

    @Column(nullable = false)
    private Double latitudeC;

    @Column(nullable = false)
    private Double longitudeC;

    @Column(nullable = false)
    private Double latitudeD;

    @Column(nullable = false)
    private Double longitudeD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;  // The admin who set the geofence

    // Default constructor
    public Geofence() {
    }

    // Constructor with arguments
    public Geofence(String geofenceplace, Double latitude, Double longitude, Double radius,
                    LocalTime officeHoursStart, LocalTime officeHoursEnd, User admin) {
        this.geofenceplace = geofenceplace;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.officeHoursStart = officeHoursStart;
        this.officeHoursEnd = officeHoursEnd;
        this.admin = admin;

        // Calculate bounding box coordinates
        calculateBoundingBox();
    }

    // Method to calculate the bounding box based on radius and center coordinates
    private void calculateBoundingBox() {
        double earthRadius = 6371000; // Earth's radius in meters
        double latOffset = radius / earthRadius * (180 / Math.PI);
        double lonOffset = radius / (earthRadius * Math.cos(Math.toRadians(latitude))) * (180 / Math.PI);

        this.latitudeA = latitude + latOffset;
        this.longitudeA = longitude - lonOffset;

        this.latitudeB = latitude + latOffset;
        this.longitudeB = longitude + lonOffset;

        this.latitudeC = latitude - latOffset;
        this.longitudeC = longitude + lonOffset;

        this.latitudeD = latitude - latOffset;
        this.longitudeD = longitude - lonOffset;
    }

    // Ensure bounding box is calculated before saving
    @PrePersist
    @PreUpdate
    private void prePersist() {
        calculateBoundingBox();
    }

    // Getters and Setters for all fields...

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

    public Double getLatitudeA() {
        return latitudeA;
    }

    public void setLatitudeA(Double latitudeA) {
        this.latitudeA = latitudeA;
    }

    public Double getLongitudeA() {
        return longitudeA;
    }

    public void setLongitudeA(Double longitudeA) {
        this.longitudeA = longitudeA;
    }

    public Double getLatitudeB() {
        return latitudeB;
    }

    public void setLatitudeB(Double latitudeB) {
        this.latitudeB = latitudeB;
    }

    public Double getLongitudeB() {
        return longitudeB;
    }

    public void setLongitudeB(Double longitudeB) {
        this.longitudeB = longitudeB;
    }

    public Double getLatitudeC() {
        return latitudeC;
    }

    public void setLatitudeC(Double latitudeC) {
        this.latitudeC = latitudeC;
    }

    public Double getLongitudeC() {
        return longitudeC;
    }

    public void setLongitudeC(Double longitudeC) {
        this.longitudeC = longitudeC;
    }

    public Double getLatitudeD() {
        return latitudeD;
    }

    public void setLatitudeD(Double latitudeD) {
        this.latitudeD = latitudeD;
    }

    public Double getLongitudeD() {
        return longitudeD;
    }

    public void setLongitudeD(Double longitudeD) {
        this.longitudeD = longitudeD;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    // Method to check if user is inside the geofence bounding box
    public boolean isInsideGeofence(Double userLatitude, Double userLongitude) {
        return (userLatitude <= this.latitudeA && userLatitude >= this.latitudeC &&
                userLongitude >= this.longitudeA && userLongitude <= this.longitudeB);
    }
}
