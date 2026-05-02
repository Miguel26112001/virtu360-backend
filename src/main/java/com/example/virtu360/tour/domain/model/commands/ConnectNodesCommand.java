package com.example.virtu360.tour.domain.model.commands;

public record ConnectNodesCommand(
    Long fromNodeId,
    Long toNodeId,
    double yaw,
    double pitch
) {
}
