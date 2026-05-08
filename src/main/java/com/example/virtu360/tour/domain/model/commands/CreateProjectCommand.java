package com.example.virtu360.tour.domain.model.commands;

public record CreateProjectCommand(
    String ownerId,
    String title,
    String description
) {
}
