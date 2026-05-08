package com.example.virtu360.tour.interfaces.rest.resources;

import java.util.UUID;

public record UpdateProjectResource(
    String title,
    String description,
    UUID startingNodeId
) {
}