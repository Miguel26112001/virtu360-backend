package com.example.virtu360.tour.interfaces.rest.resources;

import java.util.UUID;

public record ConnectNodeResource(
    UUID toNodeId,
    double yaw,
    double pitch
) {
}
