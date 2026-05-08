package com.example.virtu360.tour.interfaces.rest.transform;

import com.example.virtu360.tour.domain.model.entities.GalleryMarker;
import com.example.virtu360.tour.domain.model.entities.InfoMarker;
import com.example.virtu360.tour.domain.model.entities.Marker;
import com.example.virtu360.tour.domain.model.entities.VideoMarker;
import com.example.virtu360.tour.interfaces.rest.resources.MarkerResource;
import com.example.virtu360.tour.interfaces.rest.resources.PositionResource;

import java.util.List;

public class MarkerResourceFromEntityAssembler {

  private MarkerResourceFromEntityAssembler() {
  }

  public static MarkerResource toResourceFromEntity(Marker marker) {

    String content = null;
    String description = null;

    String videoUrl = null;
    Boolean youtube = null;

    List<String> imageUrls = null;

    if (marker instanceof InfoMarker info) {
      content = info.getContent();
      description = info.getDescription();
    }

    if (marker instanceof VideoMarker video) {
      videoUrl = video.getVideoUrl();
      youtube = video.isYoutube();
    }

    if (marker instanceof GalleryMarker gallery) {
      imageUrls = gallery.getImageUrls();
    }

    return new MarkerResource(
        marker.getId(),
        marker.getNode().getId(),
        marker.getType(),
        new PositionResource(
            marker.getPosition().yaw(),
            marker.getPosition().pitch()
        ),
        marker.getTooltip(),
        marker.getTitle(),
        marker.getSummary(),

        content,
        description,

        videoUrl,
        youtube,

        imageUrls
    );
  }
}