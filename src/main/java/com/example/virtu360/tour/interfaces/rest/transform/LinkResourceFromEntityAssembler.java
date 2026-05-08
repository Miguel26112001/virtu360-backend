package com.example.virtu360.tour.interfaces.rest.transform;

import com.example.virtu360.tour.domain.model.entities.Link;
import com.example.virtu360.tour.interfaces.rest.resources.LinkResource;

public class LinkResourceFromEntityAssembler {

  private LinkResourceFromEntityAssembler() {
  }

  public static LinkResource toResourceFromEntity(Link entity) {

    return new LinkResource(
        entity.getId(),
        entity.getFromNode().getId(),
        entity.getToNodeId(),
        entity.getPosition().yaw(),
        entity.getPosition().pitch()
    );
  }
}