package com.example.virtu360.tour.application.internal.commandservices;

import com.example.virtu360.tour.domain.model.aggregates.Node;
import com.example.virtu360.tour.domain.model.commands.AddMarkerCommand;
import com.example.virtu360.tour.domain.model.commands.ConnectNodesCommand;
import com.example.virtu360.tour.domain.model.commands.CreateNodeCommand;
import com.example.virtu360.tour.domain.model.entities.Link;
import com.example.virtu360.tour.domain.model.entities.Marker;
import com.example.virtu360.tour.domain.model.valueobjects.Position;
import com.example.virtu360.tour.domain.services.ExternalCloudinaryService;
import com.example.virtu360.tour.domain.services.NodeCommandService;
import com.example.virtu360.tour.infrastructure.persistence.jpa.repositories.NodeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NodeCommandServiceImpl implements NodeCommandService {

  private final NodeRepository nodeRepository;
  private final ExternalCloudinaryService externalCloudinaryService;

  public NodeCommandServiceImpl(
      NodeRepository nodeRepository,
      ExternalCloudinaryService externalCloudinaryService) {
    this.nodeRepository = nodeRepository;
    this.externalCloudinaryService = externalCloudinaryService;
  }

  @Override
  public Optional<Node> handle(CreateNodeCommand command) {

    var optionalUpload = externalCloudinaryService.uploadImage(command.file());
    if (optionalUpload.isEmpty()) {
      throw new RuntimeException("Unable to upload image");
    }

    var upload = optionalUpload.get();
    String thumbnailUrl = generateThumbnailUrl(upload.url());
    var node = Node.create(
        upload.url(),
        thumbnailUrl,
        command.caption(),
        upload.publicId());

    nodeRepository.save(node);

    return Optional.of(node);
  }

  @Override
  public Optional<Link> handle(ConnectNodesCommand command) {
    var fromNode = nodeRepository.findById(command.fromNodeId())
        .orElseThrow(() -> new RuntimeException("From node not found"));

    var toNode = nodeRepository.findById(command.toNodeId())
        .orElseThrow(() -> new RuntimeException("To node not found"));

    var link = fromNode.connectTo(
        toNode,
        new Position(command.yaw(), command.pitch())
    );

    nodeRepository.save(fromNode);

    return Optional.of(link);
  }

  @Override
  public void handle(AddMarkerCommand command) {

    var node = nodeRepository.findById(command.nodeId())
        .orElseThrow(() -> new RuntimeException("Node not found"));

    var marker = Marker.create(
        command.type(),
        new Position(command.yaw(), command.pitch()),
        command.tooltip(),
        command.title(),
        command.content(),
        command.description()
    );

    node.addMarker(marker);

    nodeRepository.save(node);
  }

  private String generateThumbnailUrl(String url) {
    return url.replace(
        "/upload/",
        "/upload/w_300,c_limit,q_auto,f_auto/"
    );
  }
}
