package com.example.virtu360.tour.interfaces.rest.resources;

public record UpdateInfoMarkerResource(
    double yaw,
    double pitch,
    String title,
    String tooltip,
    String summary,
    String content,
    String description
) {
}