package com.example.virtu360.tour.interfaces.rest.transform;

import com.example.virtu360.tour.domain.model.aggregates.Project;
import com.example.virtu360.tour.interfaces.rest.resources.NodeResource;
import com.example.virtu360.tour.interfaces.rest.resources.ProjectDetailsResource;

import java.util.List;

public class ProjectDetailsResourceFromEntityAssembler {

  private ProjectDetailsResourceFromEntityAssembler() {
  }

  public static ProjectDetailsResource toResourceFromEntity(Project entity) {

    List<NodeResource> nodes = entity.getNodes()
        .stream()
        .map(NodeResourceFromEntityAssembler::toResourceFromEntity)
        .toList();

    return new ProjectDetailsResource(
        entity.getId(),
        entity.getOwnerId(),
        entity.getTitle(),
        entity.getDescription(),
        entity.isPublished(),
        nodes,
        entity.getCreatedAt(),
        entity.getUpdatedAt()
    );
  }
}