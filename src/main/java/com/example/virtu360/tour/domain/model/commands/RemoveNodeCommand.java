package com.example.virtu360.tour.domain.model.commands;

import java.util.UUID;

public record RemoveNodeCommand(
    UUID projectId,
    UUID nodeId
) {
}
