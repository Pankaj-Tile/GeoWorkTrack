package com.smart.websoket;

import com.smart.entity.Geofence;
import com.smart.entity.UserLocation;
import com.smart.dao.GeofenceService;
import com.smart.dao.UserLocationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private UserLocationService vehicleLocationService;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private GeofenceService geofenceService;

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    
   @Override
public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
    String payload = message.getPayload();
    UserLocation location;
    try {
        if (payload.contains("removeLocation")) {
            String userId = objectMapper.readTree(payload).get("userId").asText();
            vehicleLocationService.deleteLocationByUserId(userId);
        } else if (payload.contains("fetchLocations")) {
            List<UserLocation> locations = vehicleLocationService.getAllLocations();
            String locationsJson = objectMapper.writeValueAsString(locations);
            session.sendMessage(new TextMessage(locationsJson));
        } else {
            location = objectMapper.readValue(payload, UserLocation.class);
            Long geofenceId = getGeofenceIdForLocation(location);
            if (geofenceId == null) {
                session.sendMessage(new TextMessage("{\"message\": \"You are not in the geofence area.\"}"));
            } else {
                location.setGeofenceId(geofenceId);
                vehicleLocationService.saveOrUpdateLocation(location);
                for (WebSocketSession webSocketSession : sessions) {
                    if (webSocketSession.isOpen()) {
                        webSocketSession.sendMessage(new TextMessage(payload));
                    }
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private Long getGeofenceIdForLocation(UserLocation location) {
    List<Geofence> geofences = geofenceService.getAllGeofences();
    for (Geofence geofence : geofences) {
        if (geofence.isInsideGeofence(location.getLatitude(), location.getLongitude())) {
            return geofence.getId();
        }
    }
    return null;
}


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        sessions.remove(session);
    }
}
