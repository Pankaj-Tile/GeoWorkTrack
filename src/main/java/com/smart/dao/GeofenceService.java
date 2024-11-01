package com.smart.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.entity.Geofence;

@Service
public class GeofenceService {

    @Autowired
    private GeofenceRepository geofenceRepository;
    
    public List<Geofence> getAllGeofences() {
        return geofenceRepository.findAll();
    }

    public void updateGeofence(Long id, Geofence geofence) {
        Geofence existingGeofence = geofenceRepository.findById(id)
                                   .orElseThrow(() -> new IllegalArgumentException("Invalid geofence Id:" + id));
        
        // Update the geofence fields with new data
        existingGeofence.setGeofenceplace(geofence.getGeofenceplace());
        existingGeofence.setRadius(geofence.getRadius());
        existingGeofence.setLatitude(geofence.getLatitude());
        existingGeofence.setLongitude(geofence.getLongitude());
        existingGeofence.setOfficeHoursStart(geofence.getOfficeHoursStart());
        existingGeofence.setOfficeHoursEnd(geofence.getOfficeHoursEnd());

        // Save the updated geofence back to the repository
        geofenceRepository.save(existingGeofence);
    }
}
