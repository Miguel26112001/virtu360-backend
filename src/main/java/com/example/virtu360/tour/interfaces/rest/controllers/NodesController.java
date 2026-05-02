package com.example.virtu360.tour.interfaces.rest.controllers;

import com.example.virtu360.tour.domain.services.NodeCommandService;
import com.example.virtu360.tour.interfaces.rest.resources.ConnectNodeResource;
import com.example.virtu360.tour.interfaces.rest.resources.CreateNodeResource;
import com.example.virtu360.tour.interfaces.rest.resources.LinkResource;
import com.example.virtu360.tour.interfaces.rest.resources.NodeResource;
import com.example.virtu360.tour.interfaces.rest.transform.ConnectNodesCommandFromResourceAssembler;
import com.example.virtu360.tour.interfaces.rest.transform.CreateNodeCommandFromResourceAssembler;
import com.example.virtu360.tour.interfaces.rest.transform.LinkResourceFromEntityAssembler;
import com.example.virtu360.tour.interfaces.rest.transform.NodeResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
    value = "/api/v1/nodes",
    produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(
    name = "Nodes",
    description = "Node management endpoints"
)
public class NodesController {

  private final NodeCommandService nodeCommandService;

  public NodesController(NodeCommandService nodeCommandService) {
    this.nodeCommandService = nodeCommandService;
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<NodeResource> createNode(
      @ModelAttribute CreateNodeResource resource
  ) {

    var command = CreateNodeCommandFromResourceAssembler
        .toCommandFromResource(resource);

    var node = nodeCommandService.handle(command);

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

    var link = nodeCommandService.handle(command);
    if (link.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    var linkResource = LinkResourceFromEntityAssembler.toResourceFromEntity(link.get());

    return ResponseEntity.ok(linkResource);
  }
}
