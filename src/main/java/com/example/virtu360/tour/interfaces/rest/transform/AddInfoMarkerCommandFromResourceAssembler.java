package com.example.virtu360.tour.interfaces.rest.transform;

import com.example.virtu360.tour.domain.model.commands.AddInfoMarkerCommand;
import com.example.virtu360.tour.interfaces.rest.resources.AddInfoMarkerResource;

import java.util.UUID;

public class AddInfoMarkerCommandFromResourceAssembler {

  private AddInfoMarkerCommandFromResourceAssembler() {
  }

  public static AddInfoMarkerCommand toCommandFromResource(
      UUID projectId,
      UUID nodeId,
      AddInfoMarkerResource resource
  ) {

    return new AddInfoMarkerCommand(
        projectId,
        nodeId,
        resource.position().yaw(),
        resource.position().pitch(),
        resource.tooltip(),
        resource.title(),
        resource.summary(),
        resource.content(),
        resource.description()
    );
  }
}