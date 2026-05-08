package com.example.virtu360.tour.interfaces.rest.transform;

import com.example.virtu360.tour.domain.model.commands.UpdateInfoMarkerCommand;
import com.example.virtu360.tour.domain.model.valueobjects.Position;
import com.example.virtu360.tour.interfaces.rest.resources.UpdateInfoMarkerResource;

import java.util.UUID;

public class UpdateInfoMarkerCommandFromResourceAssembler {

  private UpdateInfoMarkerCommandFromResourceAssembler() {
  }

  public static UpdateInfoMarkerCommand toCommandFromResource(
      UUID projectId,
      UUID nodeId,
      UUID markerId,
      UpdateInfoMarkerResource resource
  ) {

    return new UpdateInfoMarkerCommand(
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
        resource.content(),
        resource.description()
    );
  }
}