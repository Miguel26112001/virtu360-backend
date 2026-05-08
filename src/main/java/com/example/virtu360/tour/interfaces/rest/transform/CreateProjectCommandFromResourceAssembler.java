package com.example.virtu360.tour.interfaces.rest.transform;

import com.example.virtu360.tour.domain.model.commands.CreateProjectCommand;
import com.example.virtu360.tour.interfaces.rest.resources.CreateProjectResource;

public class CreateProjectCommandFromResourceAssembler {

  private CreateProjectCommandFromResourceAssembler() {
  }

  public static CreateProjectCommand toCommandFromResource(
      String ownerId,
      CreateProjectResource resource
  ) {

    return new CreateProjectCommand(
        ownerId,
        resource.title(),
        resource.description()
    );
  }
}