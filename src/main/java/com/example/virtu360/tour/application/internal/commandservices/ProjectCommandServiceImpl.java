package com.example.virtu360.tour.application.internal.commandservices;

import com.example.virtu360.tour.domain.model.entities.Node;
import com.example.virtu360.tour.domain.model.commands.AddMarkerCommand;
import com.example.virtu360.tour.domain.model.commands.ConnectNodesCommand;
import com.example.virtu360.tour.domain.model.commands.CreateNodeCommand;
import com.example.virtu360.tour.domain.model.entities.Link;
import com.example.virtu360.tour.domain.model.entities.Marker;
import com.example.virtu360.tour.domain.model.valueobjects.Position;
import com.example.virtu360.tour.domain.services.ExternalCloudinaryService;
import com.example.virtu360.tour.domain.services.ProjectCommandService;
import com.example.virtu360.tour.infrastructure.persistence.jpa.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectCommandServiceImpl implements ProjectCommandService {

  private final ProjectRepository projectRepository;
  private final ExternalCloudinaryService externalCloudinaryService;

  public ProjectCommandServiceImpl(
      ProjectRepository projectRepository,
      ExternalCloudinaryService externalCloudinaryService) {
    this.projectRepository = projectRepository;
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

    projectRepository.save(node);

    return Optional.of(node);
  }

  @Override
  public Optional<Link> handle(ConnectNodesCommand command) {
    var fromNode = projectRepository.findById(command.fromNodeId())
        .orElseThrow(() -> new RuntimeException("From node not found"));

    var toNode = projectRepository.findById(command.toNodeId())
        .orElseThrow(() -> new RuntimeException("To node not found"));

    var link = fromNode.connectTo(
        toNode,
        new Position(command.yaw(), command.pitch())
    );

    projectRepository.save(fromNode);

    return Optional.of(link);
  }

  @Override
  public Optional<Marker> handle(AddMarkerCommand command) {

    var node = projectRepository.findById(command.nodeId())
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

    projectRepository.save(node);

    return Optional.of(marker);
  }

  private String generateThumbnailUrl(String url) {
    return url.replace(
        "/upload/",
        "/upload/w_300,c_limit,q_auto,f_auto/"
    );
  }
}
