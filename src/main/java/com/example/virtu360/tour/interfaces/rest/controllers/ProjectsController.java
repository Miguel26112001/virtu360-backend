package com.example.virtu360.tour.interfaces.rest.controllers;

import com.example.virtu360.tour.domain.model.queries.*;
import com.example.virtu360.tour.domain.services.ProjectCommandService;
import com.example.virtu360.tour.domain.services.ProjectQueryService;
import com.example.virtu360.tour.interfaces.rest.resources.*;
import com.example.virtu360.tour.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(
    value = "/api/v1/projects",
    produces = MediaType.APPLICATION_JSON_VALUE
)
@Tag(
    name = "Projects",
    description = "Project management endpoints"
)
public class ProjectsController {

  private final ProjectCommandService projectCommandService;
  private final ProjectQueryService projectQueryService;

  public ProjectsController(
      ProjectCommandService projectCommandService,
      ProjectQueryService projectQueryService
  ) {
    this.projectCommandService = projectCommandService;
    this.projectQueryService = projectQueryService;
  }

  // =========================
  // CREATE
  // =========================

  @PostMapping
  public ResponseEntity<ProjectResource> createProject(
      @RequestBody CreateProjectResource resource
  ) {

    // TEMPORAL
    String ownerId = "demo-user";

    var command =
        CreateProjectCommandFromResourceAssembler
            .toCommandFromResource(ownerId, resource);

    var project = projectCommandService.handle(command);

    return project.map(value -> ResponseEntity.ok(
        ProjectResourceFromEntityAssembler
            .toResourceFromEntity(value)
    )).orElseGet(() -> ResponseEntity.badRequest().build());
  }

  // =========================
  // GET BY ID
  // =========================

  @GetMapping("/{projectId}")
  public ResponseEntity<ProjectDetailsResource> getProjectById(
      @PathVariable UUID projectId
  ) {

    var project = projectQueryService.handle(
        new GetProjectByIdQuery(projectId)
    );

    return project.map(value -> ResponseEntity.ok(
        ProjectDetailsResourceFromEntityAssembler
            .toResourceFromEntity(value)
    )).orElseGet(() -> ResponseEntity.notFound().build());
  }

  // =========================
  // GET PUBLISHED
  // =========================

  @GetMapping("/published")
  public List<ProjectSummaryResource> getPublishedProjects() {

    return projectQueryService.handle(
            new GetPublishedProjectsQuery()
        ).stream()
        .map(ProjectSummaryResourceFromEntityAssembler::toResourceFromEntity)
        .toList();
  }

  // =========================
  // PUBLISH
  // =========================

  @PostMapping("/{projectId}/publish")
  public ResponseEntity<ProjectResource> publishProject(
      @PathVariable UUID projectId
  ) {

    var command =
        PublishProjectCommandFromResourceAssembler
            .toCommandFromResource(projectId);

    var project = projectCommandService.handle(command);

    return project.map(value -> ResponseEntity.ok(
        ProjectResourceFromEntityAssembler
            .toResourceFromEntity(value)
    )).orElseGet(() -> ResponseEntity.notFound().build());
  }

  // =========================
  // UNPUBLISH
  // =========================

  @PostMapping("/{projectId}/unpublish")
  public ResponseEntity<ProjectResource> unpublishProject(
      @PathVariable UUID projectId
  ) {

    var command =
        UnpublishProjectCommandFromResourceAssembler
            .toCommandFromResource(projectId);

    var project = projectCommandService.handle(command);

    return project.map(value -> ResponseEntity.ok(
        ProjectResourceFromEntityAssembler
            .toResourceFromEntity(value)
    )).orElseGet(() -> ResponseEntity.notFound().build());
  }

  // =========================
  // DELETE
  // =========================

  @DeleteMapping("/{projectId}")
  public ResponseEntity<Void> deleteProject(
      @PathVariable UUID projectId
  ) {

    projectCommandService.handle(
        new com.example.virtu360.tour.domain.model.commands.DeleteProjectCommand(projectId)
    );

    return ResponseEntity.noContent().build();
  }
}
