package com.example.virtu360.tour.interfaces.rest.transform;

import com.example.virtu360.tour.domain.model.commands.UpdateProjectCommand;
import com.example.virtu360.tour.interfaces.rest.resources.UpdateProjectResource;

import java.util.UUID;

public class UpdateProjectCommandFromResourceAssembler {

  private UpdateProjectCommandFromResourceAssembler() {
  }

  public static UpdateProjectCommand toCommandFromResource(
      UUID projectId,
      UpdateProjectResource resource
  ) {

    return new UpdateProjectCommand(
        projectId,
        resource.title(),
        resource.description(),
        resource.startingNodeId()
    );
  }
}