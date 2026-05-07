package com.example.virtu360.tour.domain.model.queries;

import java.util.UUID;

public record GetMarkersByNodeIdQuery(
    UUID projectId,
    UUID nodeId
) {
}