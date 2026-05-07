package com.example.virtu360.tour.interfaces.rest.transform;

import com.example.virtu360.tour.domain.model.commands.CreateNodeCommand;
import com.example.virtu360.tour.interfaces.rest.resources.CreateNodeResource;

import java.util.UUID;

public class CreateNodeCommandFromResourceAssembler {

  private CreateNodeCommandFromResourceAssembler() {
  }

  public static CreateNodeCommand toCommandFromResource(
      UUID projectId,
      CreateNodeResource resource
  ) {

    return new CreateNodeCommand(
        projectId,
        resource.caption(),
        resource.file()
    );
  }
}
