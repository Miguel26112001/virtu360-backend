package com.example.virtu360.tour.domain.model.commands;

import org.springframework.web.multipart.MultipartFile;

public record CreateNodeCommand(
    String caption,
    MultipartFile file
) {
}
