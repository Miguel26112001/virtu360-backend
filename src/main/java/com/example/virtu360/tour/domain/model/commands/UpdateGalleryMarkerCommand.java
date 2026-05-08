package com.example.virtu360.tour.domain.model.commands;

import com.example.virtu360.tour.domain.model.valueobjects.Position;

import java.util.List;
import java.util.UUID;

public record UpdateGalleryMarkerCommand(
    UUID projectId,
    UUID nodeId,
    UUID markerId,

    Position position,

    String title,
    String tooltip,
    String summary,

    List<String> imageUrls
) {
}