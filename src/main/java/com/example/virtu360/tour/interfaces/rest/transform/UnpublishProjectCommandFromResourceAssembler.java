package com.example.virtu360.tour.interfaces.rest.transform;

import com.example.virtu360.tour.domain.model.commands.UnpublishProjectCommand;

import java.util.UUID;

public class UnpublishProjectCommandFromResourceAssembler {

  private UnpublishProjectCommandFromResourceAssembler() {
  }

  public static UnpublishProjectCommand toCommandFromResource(
      UUID projectId
  ) {

    return new UnpublishProjectCommand(projectId);
  }
}