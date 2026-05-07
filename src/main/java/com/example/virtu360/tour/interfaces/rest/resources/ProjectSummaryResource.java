package com.example.virtu360.tour.interfaces.rest.resources;

import java.util.UUID;

public record ProjectSummaryResource(
    UUID id,
    String title,
    String description,
    boolean published,
    int totalNodes
) {
}