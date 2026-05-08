package com.example.virtu360.tour.interfaces.rest.resources;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ProjectResource(
    UUID id,
    String ownerId,
    String title,
    String description,
    boolean published,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
}