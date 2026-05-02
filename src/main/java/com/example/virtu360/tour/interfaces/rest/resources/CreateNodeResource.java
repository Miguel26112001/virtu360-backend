package com.example.virtu360.tour.interfaces.rest.resources;

import org.springframework.web.multipart.MultipartFile;

public record CreateNodeResource(
    String caption,
    MultipartFile file) {
}
