package com.example.virtu360.tour.interfaces.rest.resources;

public record UpdateVideoMarkerResource(
    double yaw,
    double pitch,
    String title,
    String tooltip,
    String summary,
    String videoUrl,
    boolean youtube
) {
}