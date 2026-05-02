package com.example.virtu360.tour.interfaces.rest.transform;

import com.example.virtu360.tour.domain.model.commands.ConnectNodesCommand;
import com.example.virtu360.tour.interfaces.rest.resources.ConnectNodeResource;

public class ConnectNodesCommandFromResourceAssembler {

  private ConnectNodesCommandFromResourceAssembler() {
  }

  public static ConnectNodesCommand toCommandFromResource(Long fromNodeId, ConnectNodeResource resource) {
    return new ConnectNodesCommand(
        fromNodeId,
        resource.toNodeId(),
        resource.yaw(),
        resource.pitch()
    );
  }
}
