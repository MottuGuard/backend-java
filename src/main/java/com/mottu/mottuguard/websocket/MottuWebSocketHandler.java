package com.mottu.mottuguard.websocket;

import com.mottu.mottuguard.dto.websocket.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * WebSocket handler for real-time updates
 * This is the Java equivalent of SignalR MottuHub
 *
 * Client subscription topics:
 * - /topic/position-updates
 * - /topic/ranging-updates
 * - /topic/status-updates
 * - /topic/motion-events
 * - /topic/geofence-events
 * - /topic/offline-events
 */
@Controller
public class MottuWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(MottuWebSocketHandler.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * Handle incoming position updates from clients
     * Clients send to: /app/position-update
     */
    @MessageMapping("/position-update")
    public void handlePositionUpdate(@Payload PositionUpdateMessage message) {
        logger.info("Received position update for tag: {}", message.getTagId());

        // Broadcast to all subscribers
        messagingTemplate.convertAndSend("/topic/position-updates", message);
    }

    /**
     * Handle incoming ranging updates from clients
     * Clients send to: /app/ranging-update
     */
    @MessageMapping("/ranging-update")
    public void handleRangingUpdate(@Payload RangingUpdateMessage message) {
        logger.info("Received ranging update for tag: {}", message.getTagId());

        // Broadcast to all subscribers
        messagingTemplate.convertAndSend("/topic/ranging-updates", message);
    }

    /**
     * Handle incoming status updates from clients
     * Clients send to: /app/status-update
     */
    @MessageMapping("/status-update")
    public void handleStatusUpdate(@Payload StatusUpdateMessage message) {
        logger.info("Received status update for tag: {} - status: {}", message.getTagId(), message.getStatus());

        // Broadcast to all subscribers
        messagingTemplate.convertAndSend("/topic/status-updates", message);
    }

    /**
     * Handle incoming motion events from clients
     * Clients send to: /app/motion-event
     */
    @MessageMapping("/motion-event")
    public void handleMotionEvent(@Payload EventMessage message) {
        logger.info("Received motion event for tag: {}", message.getTagId());

        // Broadcast to all subscribers
        messagingTemplate.convertAndSend("/topic/motion-events", message);
    }

    /**
     * Handle incoming geofence events from clients
     * Clients send to: /app/geofence-event
     */
    @MessageMapping("/geofence-event")
    public void handleGeofenceEvent(@Payload EventMessage message) {
        logger.info("Received geofence event for tag: {}", message.getTagId());

        // Broadcast to all subscribers
        messagingTemplate.convertAndSend("/topic/geofence-events", message);
    }

    /**
     * Handle incoming offline events from clients
     * Clients send to: /app/offline-event
     */
    @MessageMapping("/offline-event")
    public void handleOfflineEvent(@Payload EventMessage message) {
        logger.info("Received offline event for tag: {}", message.getTagId());

        // Broadcast to all subscribers
        messagingTemplate.convertAndSend("/topic/offline-events", message);
    }

    /**
     * Server-side method to broadcast position updates
     * Called by MQTT consumer or other services
     */
    public void broadcastPositionUpdate(PositionUpdateMessage message) {
        messagingTemplate.convertAndSend("/topic/position-updates", message);
    }

    /**
     * Server-side method to broadcast ranging updates
     */
    public void broadcastRangingUpdate(RangingUpdateMessage message) {
        messagingTemplate.convertAndSend("/topic/ranging-updates", message);
    }

    /**
     * Server-side method to broadcast status updates
     */
    public void broadcastStatusUpdate(StatusUpdateMessage message) {
        messagingTemplate.convertAndSend("/topic/status-updates", message);
    }

    /**
     * Server-side method to broadcast motion events
     */
    public void broadcastMotionEvent(EventMessage message) {
        messagingTemplate.convertAndSend("/topic/motion-events", message);
    }

    /**
     * Server-side method to broadcast geofence events
     */
    public void broadcastGeofenceEvent(EventMessage message) {
        messagingTemplate.convertAndSend("/topic/geofence-events", message);
    }

    /**
     * Server-side method to broadcast offline events
     */
    public void broadcastOfflineEvent(EventMessage message) {
        messagingTemplate.convertAndSend("/topic/offline-events", message);
    }
}
