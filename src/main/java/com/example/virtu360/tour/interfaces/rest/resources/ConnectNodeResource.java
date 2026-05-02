package com.example.virtu360.tour.interfaces.rest.resources;

public record ConnectNodeResource(
    Long toNodeId,
    double yaw,
    double pitch
) {
}
