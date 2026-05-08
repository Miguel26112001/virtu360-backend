package com.example.virtu360.tour.interfaces.rest.transform;

import com.example.virtu360.tour.domain.model.commands.AddVideoMarkerCommand;
import com.example.virtu360.tour.interfaces.rest.resources.AddVideoMarkerResource;

import java.util.UUID;

public class AddVideoMarkerCommandFromResourceAssembler {

  private AddVideoMarkerCommandFromResourceAssembler() {
  }

  public static AddVideoMarkerCommand toCommandFromResource(
      UUID projectId,
      UUID nodeId,
      AddVideoMarkerResource resource
  ) {

    return new AddVideoMarkerCommand(
        projectId,
        nodeId,
        resource.position().yaw(),
        resource.position().pitch(),
        resource.tooltip(),
        resource.title(),
        resource.summary(),
        resource.videoUrl(),
        resource.youtube()
    );
  }
}