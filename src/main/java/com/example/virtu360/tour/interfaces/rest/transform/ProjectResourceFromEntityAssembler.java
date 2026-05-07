package com.example.virtu360.tour.interfaces.rest.transform;

import com.example.virtu360.tour.domain.model.aggregates.Project;
import com.example.virtu360.tour.interfaces.rest.resources.ProjectResource;

public class ProjectResourceFromEntityAssembler {

  private ProjectResourceFromEntityAssembler() {
  }

  public static ProjectResource toResourceFromEntity(Project entity) {

    return new ProjectResource(
        entity.getId(),
        entity.getOwnerId(),
        entity.getTitle(),
        entity.getDescription(),
        entity.isPublished(),
        entity.getCreatedAt(),
        entity.getUpdatedAt()
    );
  }
}