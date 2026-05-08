package com.example.virtu360.tour.domain.model.commands;

import java.util.UUID;

public record RemoveMarkerCommand(
    UUID projectId,
    UUID nodeId,
    UUID markerId
) {
}
