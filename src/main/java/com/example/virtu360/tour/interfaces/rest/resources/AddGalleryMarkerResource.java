package com.example.virtu360.tour.interfaces.rest.resources;

import java.util.List;

public record AddGalleryMarkerResource(
    PositionResource position,
    String tooltip,
    String title,
    String summary,
    List<String> imageUrls
) implements AddMarkerResource {
}