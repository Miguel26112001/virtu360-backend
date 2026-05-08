package com.example.virtu360.tour.interfaces.rest.resources;

import java.util.List;
import java.util.UUID;

public record MarkerResource(
    UUID id,
    UUID nodeId,
    String type,
    PositionResource position,
    String tooltip,
    String title,
    String summary,

    // INFO
    String content,
    String description,

    // VIDEO
    String videoUrl,
    Boolean youtube,

    // GALLERY
    List<String> imageUrls
) {
}