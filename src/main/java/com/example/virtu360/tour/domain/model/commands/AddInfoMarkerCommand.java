package com.example.virtu360.tour.domain.model.commands;

import java.util.UUID;

public record AddInfoMarkerCommand(
    UUID projectId,
    UUID nodeId,
    double yaw,
    double pitch,
    String title,
    String tooltip,
    String summary,
    String content,
    String description
) {
}