package com.smart.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart.entity.*;
@Repository
public interface GeofenceRepository extends JpaRepository<Geofence, Long> {
    // Custom query methods can go here if needed
}