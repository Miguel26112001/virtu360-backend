package com.example.virtu360.tour.interfaces.rest.resources;

public record LinkResource(
    String from,
    String to,
    double yaw,
    double pitch
) {
}
