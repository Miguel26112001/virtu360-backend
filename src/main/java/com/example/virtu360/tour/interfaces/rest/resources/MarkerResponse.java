package com.example.virtu360.tour.interfaces.rest.resources;

public record MarkerResponse(
    String id,
    String nodeId,
    String type,
    PositionResource position,
    String tooltip,
    String title,
    String content,
    String description
) {
}
