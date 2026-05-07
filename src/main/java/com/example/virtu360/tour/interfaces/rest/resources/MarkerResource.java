package com.example.virtu360.tour.interfaces.rest.resources;

import java.util.UUID;

public record MarkerResource(
    UUID id,
    UUID nodeId,
    String type,
    PositionResource position,
    String tooltip,
    String title,
    String content,
    String description
) {
}