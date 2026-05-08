package com.example.virtu360.tour.domain.model.queries;

import java.util.UUID;

public record GetGalleryMarkersByNodeIdQuery(
    UUID projectId,
    UUID nodeId
) {
}