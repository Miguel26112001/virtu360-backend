package com.example.virtu360.tour.domain.model.commands;

import com.example.virtu360.tour.domain.model.valueobjects.Position;

import java.util.UUID;

public record UpdateVideoMarkerCommand(
    UUID projectId,
    UUID nodeId,
    UUID markerId,

    Position position,

    String title,
    String tooltip,
    String summary,

    String videoUrl,
    boolean youtube
) {
}