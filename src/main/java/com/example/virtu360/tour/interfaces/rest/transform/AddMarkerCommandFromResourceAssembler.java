package com.example.virtu360.tour.interfaces.rest.transform;

import com.example.virtu360.tour.domain.model.commands.AddMarkerCommand;
import com.example.virtu360.tour.domain.model.valueobjects.MarkerType;
import com.example.virtu360.tour.interfaces.rest.resources.AddMarkerToNodeResource;

import java.util.UUID;

public class AddMarkerCommandFromResourceAssembler {

  private AddMarkerCommandFromResourceAssembler(){
  }

  public static AddMarkerCommand toCommandFromResource(
      UUID projectId,
      UUID nodeId,
      AddMarkerToNodeResource resource
  ) {

    return new AddMarkerCommand(
        projectId,
        nodeId,
        MarkerType.valueOf(resource.type().toUpperCase()),
        resource.position().yaw(),
        resource.position().pitch(),
        resource.tooltip(),
        resource.title(),
        resource.content(),
        resource.description()
    );
  }
}
