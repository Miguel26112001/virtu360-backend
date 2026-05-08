package com.example.virtu360.tour.domain.model.commands;

import java.util.UUID;

public record UpdateProjectCommand(
    UUID projectId,
    String title,
    String description,
    UUID startingNodeId
) {
}