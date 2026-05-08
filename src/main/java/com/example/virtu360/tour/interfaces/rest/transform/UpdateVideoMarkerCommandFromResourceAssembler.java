package com.example.virtu360.tour.interfaces.rest.transform;

import com.example.virtu360.tour.domain.model.commands.UpdateVideoMarkerCommand;
import com.example.virtu360.tour.domain.model.valueobjects.Position;
import com.example.virtu360.tour.interfaces.rest.resources.UpdateVideoMarkerResource;

import java.util.UUID;

public class UpdateVideoMarkerCommandFromResourceAssembler {

  private UpdateVideoMarkerCommandFromResourceAssembler() {
  }

  public static UpdateVideoMarkerCommand toCommandFromResource(
      UUID projectId,
      UUID nodeId,
      UUID markerId,
      UpdateVideoMarkerResource resource
  ) {

    return new UpdateVideoMarkerCommand(
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
        resource.videoUrl(),
        resource.youtube()
    );
  }
}