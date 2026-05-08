package com.example.virtu360.tour.interfaces.rest.transform;

import com.example.virtu360.tour.domain.model.commands.AddGalleryMarkerCommand;
import com.example.virtu360.tour.interfaces.rest.resources.AddGalleryMarkerResource;

import java.util.UUID;

public class AddGalleryMarkerCommandFromResourceAssembler {

  private AddGalleryMarkerCommandFromResourceAssembler() {
  }

  public static AddGalleryMarkerCommand toCommandFromResource(
      UUID projectId,
      UUID nodeId,
      AddGalleryMarkerResource resource
  ) {

    return new AddGalleryMarkerCommand(
        projectId,
        nodeId,
        resource.position().yaw(),
        resource.position().pitch(),
        resource.tooltip(),
        resource.title(),
        resource.summary(),
        resource.imageUrls()
    );
  }
}