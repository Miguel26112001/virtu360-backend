package com.example.virtu360.tour.domain.model.commands;

import java.util.List;
import java.util.UUID;

public record AddGalleryMarkerCommand(
    UUID projectId,
    UUID nodeId,
    double yaw,
    double pitch,
    String title,
    String tooltip,
    String summary,
    List<String> imageUrls
) {
}