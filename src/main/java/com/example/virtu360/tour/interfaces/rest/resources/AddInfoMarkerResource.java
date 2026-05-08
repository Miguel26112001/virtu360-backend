package com.example.virtu360.tour.interfaces.rest.resources;

public record AddInfoMarkerResource(
    PositionResource position,
    String tooltip,
    String title,
    String summary,
    String content,
    String description
) implements AddMarkerResource {
}