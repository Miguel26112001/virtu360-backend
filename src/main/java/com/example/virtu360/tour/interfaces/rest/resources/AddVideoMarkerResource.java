package com.example.virtu360.tour.interfaces.rest.resources;

public record AddVideoMarkerResource(
    PositionResource position,
    String tooltip,
    String title,
    String summary,
    String videoUrl,
    boolean youtube
) implements AddMarkerResource {
}