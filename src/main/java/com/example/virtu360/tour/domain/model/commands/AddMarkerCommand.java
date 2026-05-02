package com.example.virtu360.tour.domain.model.commands;

import com.example.virtu360.tour.domain.model.valueobjects.MarkerType;

public record AddMarkerCommand(
    Long nodeId,
    MarkerType type,
    double yaw,
    double pitch,
    String tooltip,
    String title,
    String content,
    String description
) {
}
