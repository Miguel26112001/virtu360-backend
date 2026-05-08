package com.example.virtu360.tour.interfaces.rest.resources;

import java.util.List;

public record UpdateGalleryMarkerResource(
    double yaw,
    double pitch,
    String title,
    String tooltip,
    String summary,
    List<String> imageUrls
) {
}