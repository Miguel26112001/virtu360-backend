package com.example.virtu360.tour.interfaces.rest.resources;

import java.util.UUID;

public record LinkResource(
    UUID id,
    UUID fromNodeId,
    UUID toNodeId,
    double yaw,
    double pitch
) {
}