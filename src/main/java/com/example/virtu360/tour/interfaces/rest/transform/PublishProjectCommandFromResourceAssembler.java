package com.example.virtu360.tour.interfaces.rest.transform;

import com.example.virtu360.tour.domain.model.commands.PublishProjectCommand;

import java.util.UUID;

public class PublishProjectCommandFromResourceAssembler {

  private PublishProjectCommandFromResourceAssembler() {
  }

  public static PublishProjectCommand toCommandFromResource(
      UUID projectId
  ) {

    return new PublishProjectCommand(projectId);
  }
}