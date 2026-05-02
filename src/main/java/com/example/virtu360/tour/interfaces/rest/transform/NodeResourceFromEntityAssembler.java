package com.example.virtu360.tour.interfaces.rest.transform;

import com.example.virtu360.tour.domain.model.aggregates.Node;
import com.example.virtu360.tour.interfaces.rest.resources.NodeResource;

public class NodeResourceFromEntityAssembler {
  private NodeResourceFromEntityAssembler() {
  }

  public static NodeResource toResourceFromEntity(Node entity) {
    return new NodeResource(
        entity.getId().toString(),
        entity.getPanoramaUrl(),
        entity.getThumbnailUrl(),
        entity.getCaption()
    );
  }
}
