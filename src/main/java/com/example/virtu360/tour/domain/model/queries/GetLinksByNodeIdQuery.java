package com.example.virtu360.tour.domain.model.queries;

import java.util.UUID;

public record GetLinksByNodeIdQuery(
    UUID projectId,
    UUID nodeId
) {
}