package com.example.virtu360.tour.domain.model.commands;

import java.util.UUID;

public record ConnectNodesCommand(
    UUID projectId,
    UUID fromNodeId,
    UUID toNodeId,
    double yaw,
    double pitch
) {
}
