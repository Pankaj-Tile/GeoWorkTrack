// package com.smart.config;

// import java.io.IOException;

// import org.springframework.web.server.WebSession;

// import com.smart.entity.UserLiveLocation;

// public class MyWebSocketHandler {
    
//     @Override
// public void handleTextMessage(WebSession session, TextMessage message) throws IOException {
//     String payload = message.getPayload();
//     UserLiveLocation liveLocation;

//     try {
//         if (payload.contains("removeLocation")) {
//             String userId = objectMapper.readTree(payload).get("userId").asText();
//             userLiveLocationService.deleteLocationByUserId(userId);
//         } else if (payload.contains("fetchLocations")) {
//             List<UserLiveLocation> locations = userLiveLocationService.getAllLocations();
//             String locationsJson = objectMapper.writeValueAsString(locations);
//             session.sendMessage(new TextMessage(locationsJson));
//         } else {
//             liveLocation = objectMapper.readValue(payload, UserLiveLocation.class);

//             // Insert or update location
//             userLiveLocationService.saveOrUpdateLocation(liveLocation);

//             // Send updated location to all WebSocket sessions
//             for (WebSocketSession webSocketSession : sessions) {
//                 if (webSocketSession.isOpen()) {
//                     webSocketSession.sendMessage(new TextMessage(payload));
//                 }
//             }
//         }
//     } catch (Exception e) {
//         e.printStackTrace();
//     }
// }

// }
