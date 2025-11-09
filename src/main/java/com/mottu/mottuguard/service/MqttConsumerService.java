package com.mottu.mottuguard.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mottu.mottuguard.dto.websocket.*;
import com.mottu.mottuguard.websocket.MottuWebSocketHandler;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import jakarta.annotation.PreDestroy;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * MQTT Consumer Service that subscribes to IoT device messages
 * and broadcasts them via WebSocket
 */
@Service
public class MqttConsumerService implements MqttCallback {

    private static final Logger logger = LoggerFactory.getLogger(MqttConsumerService.class);

    @Value("${mqtt.broker}")
    private String brokerUrl;

    @Value("${mqtt.client-id}")
    private String clientId;

    @Value("${mqtt.username:}")
    private String username;

    @Value("${mqtt.password:}")
    private String password;

    @Value("${mqtt.topics}")
    private String topicsConfig;

    @Autowired
    private MottuWebSocketHandler webSocketHandler;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private MqttClient mqttClient;

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        try {
            logger.info("Initializing MQTT Consumer Service...");

            mqttClient = new MqttClient(brokerUrl, clientId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setAutomaticReconnect(true);

            if (username != null && !username.isEmpty()) {
                options.setUserName(username);
                options.setPassword(password.toCharArray());
            }

            mqttClient.setCallback(this);
            mqttClient.connect(options);

            logger.info("Connected to MQTT broker: {}", brokerUrl);

            // Subscribe to topics
            String[] topics = topicsConfig.split(",");
            for (String topic : topics) {
                mqttClient.subscribe(topic.trim());
                logger.info("Subscribed to MQTT topic: {}", topic.trim());
            }

        } catch (MqttException e) {
            logger.error("Failed to initialize MQTT consumer", e);
        }
    }

    @PreDestroy
    public void shutdown() {
        try {
            if (mqttClient != null && mqttClient.isConnected()) {
                mqttClient.disconnect();
                mqttClient.close();
                logger.info("MQTT client disconnected");
            }
        } catch (MqttException e) {
            logger.error("Error disconnecting MQTT client", e);
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        logger.error("MQTT connection lost", cause);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        try {
            String payload = new String(message.getPayload());
            logger.debug("Received MQTT message on topic {}: {}", topic, payload);

            // Parse JSON payload
            JsonNode jsonNode = objectMapper.readTree(payload);

            // Process based on topic pattern
            if (topic.matches("mottu/uwb/.+/position")) {
                handlePositionMessage(jsonNode);
            } else if (topic.matches("mottu/uwb/.+/ranging")) {
                handleRangingMessage(jsonNode);
            } else if (topic.matches("mottu/status/.+")) {
                handleStatusMessage(jsonNode);
            } else if (topic.matches("mottu/motion/.+")) {
                handleMotionEvent(jsonNode);
            } else if (topic.matches("mottu/event/.+")) {
                handleEvent(jsonNode);
            }

        } catch (Exception e) {
            logger.error("Error processing MQTT message from topic: {}", topic, e);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Not used for consumer
    }

    private void handlePositionMessage(JsonNode json) {
        String tagId = json.has("tagId") ? json.get("tagId").asText() : null;
        Double x = json.has("x") ? json.get("x").asDouble() : null;
        Double y = json.has("y") ? json.get("y").asDouble() : null;
        Instant timestamp = json.has("timestamp") ? Instant.parse(json.get("timestamp").asText()) : Instant.now();

        PositionUpdateMessage message = new PositionUpdateMessage(tagId, x, y, timestamp);
        webSocketHandler.broadcastPositionUpdate(message);

        logger.info("Broadcasted position update for tag: {}", tagId);
    }

    private void handleRangingMessage(JsonNode json) {
        String tagId = json.has("tagId") ? json.get("tagId").asText() : null;
        Instant timestamp = json.has("timestamp") ? Instant.parse(json.get("timestamp").asText()) : Instant.now();

        Map<String, Double> ranges = new HashMap<>();
        if (json.has("ranges")) {
            JsonNode rangesNode = json.get("ranges");
            rangesNode.fields().forEachRemaining(entry -> {
                ranges.put(entry.getKey(), entry.getValue().asDouble());
            });
        }

        RangingUpdateMessage message = new RangingUpdateMessage(tagId, ranges, timestamp);
        webSocketHandler.broadcastRangingUpdate(message);

        logger.info("Broadcasted ranging update for tag: {}", tagId);
    }

    private void handleStatusMessage(JsonNode json) {
        String tagId = json.has("tagId") ? json.get("tagId").asText() : null;
        String status = json.has("status") ? json.get("status").asText() : null;
        Instant timestamp = json.has("timestamp") ? Instant.parse(json.get("timestamp").asText()) : Instant.now();

        StatusUpdateMessage message = new StatusUpdateMessage(tagId, status, timestamp);
        webSocketHandler.broadcastStatusUpdate(message);

        logger.info("Broadcasted status update for tag: {} - status: {}", tagId, status);
    }

    private void handleMotionEvent(JsonNode json) {
        String tagId = json.has("tagId") ? json.get("tagId").asText() : null;
        Instant timestamp = json.has("timestamp") ? Instant.parse(json.get("timestamp").asText()) : Instant.now();

        Map<String, Object> eventData = objectMapper.convertValue(json, Map.class);

        EventMessage message = new EventMessage(tagId, "motion", eventData, timestamp);
        webSocketHandler.broadcastMotionEvent(message);

        logger.info("Broadcasted motion event for tag: {}", tagId);
    }

    private void handleEvent(JsonNode json) {
        String tagId = json.has("tagId") ? json.get("tagId").asText() : null;
        String eventType = json.has("eventType") ? json.get("eventType").asText() : "unknown";
        Instant timestamp = json.has("timestamp") ? Instant.parse(json.get("timestamp").asText()) : Instant.now();

        Map<String, Object> eventData = objectMapper.convertValue(json, Map.class);

        EventMessage message = new EventMessage(tagId, eventType, eventData, timestamp);

        if ("geofence".equalsIgnoreCase(eventType)) {
            webSocketHandler.broadcastGeofenceEvent(message);
        } else if ("offline".equalsIgnoreCase(eventType)) {
            webSocketHandler.broadcastOfflineEvent(message);
        }

        logger.info("Broadcasted {} event for tag: {}", eventType, tagId);
    }
}
