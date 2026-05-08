package com.example.virtu360.tour.interfaces.rest.resources;

import com.example.virtu360.tour.domain.model.valueobjects.MarkerType;

import java.util.List;
import java.util.UUID;

public record MarkerResource(
    UUID id,
    UUID nodeId,
    MarkerType type,
    PositionResource position,
    String tooltip,
    String title,
    String summary,

    String content,
    String description,

    String videoUrl,
    Boolean youtube,

    List<String> imageUrls
) {
}