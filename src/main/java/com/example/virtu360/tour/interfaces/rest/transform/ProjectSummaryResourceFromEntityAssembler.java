package com.example.virtu360.tour.interfaces.rest.transform;

import com.example.virtu360.tour.domain.model.aggregates.Project;
import com.example.virtu360.tour.interfaces.rest.resources.ProjectSummaryResource;

public class ProjectSummaryResourceFromEntityAssembler {

  private ProjectSummaryResourceFromEntityAssembler() {
  }

  public static ProjectSummaryResource toResourceFromEntity(Project entity) {

    return new ProjectSummaryResource(
        entity.getId(),
        entity.getTitle(),
        entity.getDescription(),
        entity.isPublished(),
        entity.getNodes().size()
    );
  }
}