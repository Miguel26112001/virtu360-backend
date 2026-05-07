package com.example.virtu360.tour.interfaces.rest.resources;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record ProjectDetailsResource(
    UUID id,
    String ownerId,
    String title,
    String description,
    boolean published,
    List<NodeResource> nodes,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
}