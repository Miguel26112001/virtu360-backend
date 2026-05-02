package com.example.virtu360.shared.domain.model.commands;

import org.springframework.web.multipart.MultipartFile;

public record UploadImageCommand(
    MultipartFile file
) {
}
