package com.smart.dao;


import com.smart.entity.User;
import com.smart.entity.UserLocation;
import com.smart.dao.UserLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserLocationService {

    @Autowired
    private UserLocationRepository repository;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public void saveOrUpdateLocation(UserLocation location) {
        UserLocation existingLocation = repository.findByUserId(location.getUserId());
        if (existingLocation != null) {
            existingLocation.setLatitude(location.getLatitude());
            existingLocation.setLongitude(location.getLongitude());
            existingLocation.setTimestamp(LocalDateTime.now().format(formatter));
            repository.save(existingLocation);
        } else {
            location.setTimestamp(LocalDateTime.now().format(formatter));
            repository.save(location);
        }
    }

    public void deleteLocationByUserId(String userId) {
        UserLocation existingLocation = repository.findByUserId(userId);
        if (existingLocation != null) {
            repository.delete(existingLocation);
        }
    }

    public List<UserLocation> getAllLocations() {
        return repository.findAll();
    }

    @Scheduled(fixedRate = 60000) // Runs every minute
    public void cleanUpStaleEntries() {
        LocalDateTime oneMinuteAgo = LocalDateTime.now().minusMinutes(1);
        List<UserLocation> allLocations = repository.findAll();
        for (UserLocation location : allLocations) {
            LocalDateTime locationTime = LocalDateTime.parse(location.getTimestamp(), formatter);
            if (locationTime.isBefore(oneMinuteAgo)) {
                repository.delete(location);
            }
        }
    }
}
