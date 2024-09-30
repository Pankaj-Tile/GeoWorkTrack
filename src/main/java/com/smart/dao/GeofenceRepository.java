package com.smart.dao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.entity.*;
public interface GeofenceRepository extends JpaRepository<Geofence, Long> {
    // Custom query methods can go here if needed
}