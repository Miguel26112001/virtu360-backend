package com.example.virtu360.tour.domain.model.commands;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record CreateNodeCommand(
    UUID projectId,
    String caption,
    MultipartFile file
) {
}
