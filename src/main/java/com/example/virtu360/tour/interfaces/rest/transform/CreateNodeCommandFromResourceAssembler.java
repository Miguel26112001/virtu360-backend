package com.example.virtu360.tour.interfaces.rest.transform;

import com.example.virtu360.tour.domain.model.commands.CreateNodeCommand;
import com.example.virtu360.tour.interfaces.rest.resources.CreateNodeResource;

public class CreateNodeCommandFromResourceAssembler {

  private CreateNodeCommandFromResourceAssembler() {
  }

  public static CreateNodeCommand toCommandFromResource(CreateNodeResource resource) {
    return new CreateNodeCommand(
        resource.caption(),
        resource.file()
    );
  }
}
