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
    value = "/api/v1/projects/{projectId}",
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

  @PostMapping(
      value = "/nodes",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE
  )
  public ResponseEntity<NodeResource> createNode(
      @PathVariable UUID projectId,
      @ModelAttribute CreateNodeResource resource
  ) {

    var command =
        CreateNodeCommandFromResourceAssembler
            .toCommandFromResource(projectId, resource);

    var optionalNode =
        projectCommandService.handle(command);

    if (optionalNode.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    var nodeResource =
        NodeResourceFromEntityAssembler
            .toResourceFromEntity(optionalNode.get());

    return ResponseEntity.ok(nodeResource);
  }

  // =========================
  // REMOVE NODE
  // =========================

  @DeleteMapping("/nodes/{nodeId}")
  public ResponseEntity<Void> removeNode(
      @PathVariable UUID projectId,
      @PathVariable UUID nodeId
  ) {

    var command = new RemoveNodeCommand(
        projectId,
        nodeId
    );

    projectCommandService.handle(command);

    return ResponseEntity.noContent().build();
  }

  // =========================
  // CONNECT NODES
  // =========================

  @PostMapping("/nodes/{fromNodeId}/links")
  public ResponseEntity<LinkResource> connectNodes(
      @PathVariable UUID projectId,
      @PathVariable UUID fromNodeId,
      @RequestBody ConnectNodeResource resource
  ) {

    var command =
        ConnectNodesCommandFromResourceAssembler
            .toCommandFromResource(
                projectId,
                fromNodeId,
                resource
            );

    var optionalLink =
        projectCommandService.handle(command);

    if (optionalLink.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    var linkResource =
        LinkResourceFromEntityAssembler
            .toResourceFromEntity(optionalLink.get());

    return ResponseEntity.ok(linkResource);
  }

  // =========================
  // REMOVE LINK
  // =========================

  @DeleteMapping("/nodes/{fromNodeId}/links/{linkId}")
  public ResponseEntity<Void> removeLink(
      @PathVariable UUID projectId,
      @PathVariable UUID fromNodeId,
      @PathVariable UUID linkId
  ) {

    var command = new RemoveLinkCommand(
        projectId,
        fromNodeId,
        linkId
    );

    projectCommandService.handle(command);

    return ResponseEntity.noContent().build();
  }

  // =========================
  // ADD MARKER
  // =========================

  @PostMapping("/nodes/{nodeId}/markers")
  public ResponseEntity<MarkerResource> addMarker(
      @PathVariable UUID projectId,
      @PathVariable UUID nodeId,
      @RequestBody AddMarkerToNodeResource resource
  ) {

    var command =
        AddMarkerCommandFromResourceAssembler
            .toCommandFromResource(
                projectId,
                nodeId,
                resource
            );

    var optionalMarker =
        projectCommandService.handle(command);

    if (optionalMarker.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    var markerResource =
        MarkerResourceFromEntityAssembler
            .toResourceFromEntity(optionalMarker.get());

    return ResponseEntity.ok(markerResource);
  }

  // =========================
  // REMOVE MARKER
  // =========================

  @DeleteMapping("/nodes/{nodeId}/markers/{markerId}")
  public ResponseEntity<Void> removeMarker(
      @PathVariable UUID projectId,
      @PathVariable UUID nodeId,
      @PathVariable UUID markerId
  ) {

    var command = new RemoveMarkerCommand(
        projectId,
        nodeId,
        markerId
    );

    projectCommandService.handle(command);

    return ResponseEntity.noContent().build();
  }

  // =========================
  // GET ALL NODES
  // =========================

  @GetMapping("/nodes")
  public ResponseEntity<List<NodeResource>> getNodes(
      @PathVariable UUID projectId
  ) {

    var query =
        new GetNodesByProjectIdQuery(projectId);

    var nodes =
        projectQueryService.handle(query);

    var resources = nodes.stream()
        .map(NodeResourceFromEntityAssembler::toResourceFromEntity)
        .toList();

    return ResponseEntity.ok(resources);
  }

  // =========================
  // GET NODE BY ID
  // =========================

  @GetMapping("/nodes/{nodeId}")
  public ResponseEntity<NodeResource> getNodeById(
      @PathVariable UUID projectId,
      @PathVariable UUID nodeId
  ) {

    var query =
        new GetNodeByIdQuery(
            projectId,
            nodeId
        );

    var optionalNode =
        projectQueryService.handle(query);

    if (optionalNode.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    var resource =
        NodeResourceFromEntityAssembler
            .toResourceFromEntity(optionalNode.get());

    return ResponseEntity.ok(resource);
  }

  // =========================
  // GET LINKS
  // =========================

  @GetMapping("/nodes/{nodeId}/links")
  public ResponseEntity<List<LinkResource>> getLinks(
      @PathVariable UUID projectId,
      @PathVariable UUID nodeId
  ) {

    var query =
        new GetLinksByNodeIdQuery(
            projectId,
            nodeId
        );

    var links =
        projectQueryService.handle(query);

    var resources = links.stream()
        .map(LinkResourceFromEntityAssembler::toResourceFromEntity)
        .toList();

    return ResponseEntity.ok(resources);
  }

  // =========================
  // GET MARKERS
  // =========================

  @GetMapping("/nodes/{nodeId}/markers")
  public ResponseEntity<List<MarkerResource>> getMarkers(
      @PathVariable UUID projectId,
      @PathVariable UUID nodeId
  ) {

    var query =
        new GetMarkersByNodeIdQuery(
            projectId,
            nodeId
        );

    var markers =
        projectQueryService.handle(query);

    var resources = markers.stream()
        .map(MarkerResourceFromEntityAssembler::toResourceFromEntity)
        .toList();

    return ResponseEntity.ok(resources);
  }
}
