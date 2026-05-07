package com.example.virtu360.tour.interfaces.rest.transform;

import com.example.virtu360.tour.domain.model.commands.ConnectNodesCommand;
import com.example.virtu360.tour.interfaces.rest.resources.ConnectNodeResource;

import java.util.UUID;

public class ConnectNodesCommandFromResourceAssembler {

  private ConnectNodesCommandFromResourceAssembler() {
  }

  public static ConnectNodesCommand toCommandFromResource(
      UUID projectId,
      UUID fromNodeId,
      ConnectNodeResource resource
  ) {

    return new ConnectNodesCommand(
        projectId,
        fromNodeId,
        resource.toNodeId(),
        resource.yaw(),
        resource.pitch()
    );
  }
}
