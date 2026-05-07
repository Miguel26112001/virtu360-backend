package com.example.virtu360.tour.domain.model.queries;

import java.util.UUID;

public record GetNodeByIdQuery(
    UUID projectId,
    UUID nodeId
) {
}