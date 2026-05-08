package com.example.virtu360.tour.interfaces.rest.transform;

import com.example.virtu360.tour.domain.model.commands.UpdateGalleryMarkerCommand;
import com.example.virtu360.tour.domain.model.valueobjects.Position;
import com.example.virtu360.tour.interfaces.rest.resources.UpdateGalleryMarkerResource;

import java.util.UUID;

public class UpdateGalleryMarkerCommandFromResourceAssembler {

  private UpdateGalleryMarkerCommandFromResourceAssembler() {
  }

  public static UpdateGalleryMarkerCommand toCommandFromResource(
      UUID projectId,
      UUID nodeId,
      UUID markerId,
      UpdateGalleryMarkerResource resource
  ) {

    return new UpdateGalleryMarkerCommand(
        projectId,
        nodeId,
        markerId,
        new Position(
            resource.yaw(),
            resource.pitch()
        ),
        resource.title(),
        resource.tooltip(),
        resource.summary(),
        resource.imageUrls()
    );
  }
}