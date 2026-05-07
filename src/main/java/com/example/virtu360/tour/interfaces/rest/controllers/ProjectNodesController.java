package com.example.virtu360.tour.interfaces.rest.controllers;

import com.example.virtu360.tour.domain.model.commands.RemoveLinkCommand;
import com.example.virtu360.tour.domain.model.commands.RemoveMarkerCommand;
import com.example.virtu360.tour.domain.model.commands.RemoveNodeCommand;
import com.example.virtu360.tour.domain.model.queries.GetLinksByNodeIdQuery;
import com.example.virtu360.tour.domain.model.queries.GetMarkersByNodeIdQuery;
import com.example.virtu360.tour.domain.model.queries.GetNodeByIdQuery;
import com.example.virtu360.tour.domain.model.queries.GetNodesByProjectIdQuery;
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
    value = "/api/v1/projects/{projectId}/nodes",
    produces = MediaType.APPLICATION_JSON_VALUE
)
@Tag(
    name = "Project Nodes",
    description = "Project node management endpoints"
)
public class ProjectNodesController {

  private final ProjectCommandService projectCommandService;
  private final ProjectQueryService projectQueryService;

  public ProjectNodesController(
      ProjectCommandService projectCommandService,
      ProjectQueryService projectQueryService
  ) {
    this.projectCommandService = projectCommandService;
    this.projectQueryService = projectQueryService;
  }

  // =========================
  // CREATE NODE
  // =========================

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ProjectResource> createNode(
      @PathVariable UUID projectId,
      @ModelAttribute CreateNodeResource resource
  ) {

    var command =
        CreateNodeCommandFromResourceAssembler
            .toCommandFromResource(projectId, resource);

    var result = projectCommandService.handle(command);

    return result.map(value -> ResponseEntity.ok(
        ProjectResourceFromEntityAssembler
            .toResourceFromEntity(value)
    )).orElseGet(() -> ResponseEntity.badRequest().build());
  }

  // =========================
  // GET NODES
  // =========================

  @GetMapping
  public List<NodeResource> getNodes(
      @PathVariable UUID projectId
  ) {

    return projectQueryService.handle(
            new GetNodesByProjectIdQuery(projectId)
        ).stream()
        .map(NodeResourceFromEntityAssembler::toResourceFromEntity)
        .toList();
  }

  // =========================
  // GET NODE
  // =========================

  @GetMapping("/{nodeId}")
  public ResponseEntity<NodeResource> getNodeById(
      @PathVariable UUID projectId,
      @PathVariable UUID nodeId
  ) {

    var node = projectQueryService.handle(
        new GetNodeByIdQuery(projectId, nodeId)
    );

    return node.map(value -> ResponseEntity.ok(
        NodeResourceFromEntityAssembler
            .toResourceFromEntity(value)
    )).orElseGet(() -> ResponseEntity.notFound().build());
  }

  // =========================
  // DELETE NODE
  // =========================

  @DeleteMapping("/{nodeId}")
  public ResponseEntity<Void> deleteNode(
      @PathVariable UUID projectId,
      @PathVariable UUID nodeId
  ) {

    projectCommandService.handle(
        new RemoveNodeCommand(projectId, nodeId)
    );

    return ResponseEntity.noContent().build();
  }

  // =========================
  // LINKS
  // =========================

  @PostMapping("/{nodeId}/links")
  public ResponseEntity<ProjectResource> connectNodes(
      @PathVariable UUID projectId,
      @PathVariable UUID nodeId,
      @RequestBody ConnectNodeResource resource
  ) {

    var command =
        ConnectNodesCommandFromResourceAssembler
            .toCommandFromResource(projectId, nodeId, resource);

    var result = projectCommandService.handle(command);

    return result.map(value -> ResponseEntity.ok(
        ProjectResourceFromEntityAssembler
            .toResourceFromEntity(value)
    )).orElseGet(() -> ResponseEntity.badRequest().build());
  }

  @GetMapping("/{nodeId}/links")
  public List<LinkResource> getLinks(
      @PathVariable UUID projectId,
      @PathVariable UUID nodeId
  ) {

    return projectQueryService.handle(
            new GetLinksByNodeIdQuery(projectId, nodeId)
        ).stream()
        .map(LinkResourceFromEntityAssembler::toResourceFromEntity)
        .toList();
  }

  @DeleteMapping("/{nodeId}/links/{linkId}")
  public ResponseEntity<Void> removeLink(
      @PathVariable UUID projectId,
      @PathVariable UUID nodeId,
      @PathVariable UUID linkId
  ) {

    projectCommandService.handle(
        new RemoveLinkCommand(projectId, nodeId, linkId)
    );

    return ResponseEntity.noContent().build();
  }

  // =========================
  // MARKERS
  // =========================

  @PostMapping("/{nodeId}/markers")
  public ResponseEntity<ProjectResource> addMarker(
      @PathVariable UUID projectId,
      @PathVariable UUID nodeId,
      @RequestBody AddMarkerToNodeResource resource
  ) {

    var command =
        AddMarkerCommandFromResourceAssembler
            .toCommandFromResource(projectId, nodeId, resource);

    var result = projectCommandService.handle(command);

    return result.map(value -> ResponseEntity.ok(
        ProjectResourceFromEntityAssembler
            .toResourceFromEntity(value)
    )).orElseGet(() -> ResponseEntity.badRequest().build());
  }

  @GetMapping("/{nodeId}/markers")
  public List<MarkerResource> getMarkers(
      @PathVariable UUID projectId,
      @PathVariable UUID nodeId
  ) {

    return projectQueryService.handle(
            new GetMarkersByNodeIdQuery(projectId, nodeId)
        ).stream()
        .map(MarkerResourceFromEntityAssembler::toResourceFromEntity)
        .toList();
  }

  @DeleteMapping("/{nodeId}/markers/{markerId}")
  public ResponseEntity<Void> removeMarker(
      @PathVariable UUID projectId,
      @PathVariable UUID nodeId,
      @PathVariable UUID markerId
  ) {

    projectCommandService.handle(
        new RemoveMarkerCommand(projectId, nodeId, markerId)
    );

    return ResponseEntity.noContent().build();
  }
}
