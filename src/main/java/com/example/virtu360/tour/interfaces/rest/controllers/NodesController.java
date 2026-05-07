package com.example.virtu360.tour.interfaces.rest.controllers;

import com.example.virtu360.tour.domain.model.queries.GetAllNodesQuery;
import com.example.virtu360.tour.domain.model.queries.GetLinksByNodeIdQuery;
import com.example.virtu360.tour.domain.model.queries.GetMarkersByNodeIdQuery;
import com.example.virtu360.tour.domain.model.queries.GetNodeByIdQuery;
import com.example.virtu360.tour.domain.services.ProjectCommandService;
import com.example.virtu360.tour.domain.services.ProjectQueryService;
import com.example.virtu360.tour.interfaces.rest.resources.*;
import com.example.virtu360.tour.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
    value = "/api/v1/nodes",
    produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(
    name = "Nodes",
    description = "Node management endpoints"
)
public class NodesController {

  private final ProjectCommandService projectCommandService;
  private final ProjectQueryService projectQueryService;

  public NodesController(
    ProjectCommandService projectCommandService,
    ProjectQueryService projectQueryService) {
    this.projectCommandService = projectCommandService;
    this.projectQueryService = projectQueryService;
  }

  // =========================
  // COMMANDS
  // =========================

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<NodeResource> createNode(
      @ModelAttribute CreateNodeResource resource
  ) {

    var command = CreateNodeCommandFromResourceAssembler
        .toCommandFromResource(resource);

    var node = projectCommandService.handle(command);

    return node.map(value -> ResponseEntity.ok(
        NodeResourceFromEntityAssembler.toResourceFromEntity(value)
    )).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/{fromNodeId}/links")
  public ResponseEntity<LinkResource> createLink(
      @PathVariable Long fromNodeId,
      @RequestBody ConnectNodeResource resource
  ){
    var command = ConnectNodesCommandFromResourceAssembler.toCommandFromResource(fromNodeId, resource);

    var link = projectCommandService.handle(command);
    if (link.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    var linkResource = LinkResourceFromEntityAssembler.toResourceFromEntity(link.get());

    return ResponseEntity.ok(linkResource);
  }

  @PostMapping("/{nodeId}/markers")
  public ResponseEntity<MarkerResponse> addMarker(
    @PathVariable Long nodeId,
    @RequestBody AddMarkerToNodeResource resource
  ) {

    var command = AddMarkerCommandFromResourceAssembler
      .toCommandFromResource(nodeId, resource);

    var optionalMarker = projectCommandService.handle(command);

    return optionalMarker
      .map(marker -> ResponseEntity.ok(
        MarkerResourceFromEntityAssembler.toResourceFromEntity(marker)
      ))
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping()
  public List<NodeResource> getAllNodes() {
    var nodes = projectQueryService.handle(new GetAllNodesQuery());

    return nodes.stream()
      .map(NodeResourceFromEntityAssembler::toResourceFromEntity)
      .toList();
  }

  @GetMapping("{id}")
  public ResponseEntity<NodeResource> getNodeById(@PathVariable Long id) {
    var node = projectQueryService.handle(new GetNodeByIdQuery(id));

    return node.map(value -> ResponseEntity.ok(
      NodeResourceFromEntityAssembler.toResourceFromEntity(value)
    )).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/{nodeId}/links")
  public List<LinkResource> getLinksByNodeId(@PathVariable Long nodeId) {
    return projectQueryService.handle(new GetLinksByNodeIdQuery(nodeId))
      .stream()
      .map(LinkResourceFromEntityAssembler::toResourceFromEntity)
      .toList();
  }

  @GetMapping("/{nodeId}/markers")
  public List<MarkerResponse> getMarkersByNodeId(@PathVariable Long nodeId) {
    return projectQueryService.handle(new GetMarkersByNodeIdQuery(nodeId))
      .stream()
      .map(MarkerResourceFromEntityAssembler::toResourceFromEntity)
      .toList();
  }
}
