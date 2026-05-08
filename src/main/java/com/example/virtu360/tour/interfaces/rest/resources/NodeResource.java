package com.example.virtu360.tour.interfaces.rest.resources;

import java.util.UUID;

public record NodeResource(
    UUID id,
    String panoramaUrl,
    String thumbnailUrl,
    String caption
) {
}