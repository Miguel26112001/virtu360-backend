package com.example.virtu360.tour.application.internal.commandservices;

import com.example.virtu360.tour.domain.model.aggregates.Project;
import com.example.virtu360.tour.domain.model.commands.*;
import com.example.virtu360.tour.domain.model.entities.Link;
import com.example.virtu360.tour.domain.model.entities.Node;
import com.example.virtu360.tour.domain.model.entities.Marker;
import com.example.virtu360.tour.domain.model.valueobjects.Position;
import com.example.virtu360.tour.domain.services.ExternalCloudinaryService;
import com.example.virtu360.tour.domain.services.ProjectCommandService;
import com.example.virtu360.tour.infrastructure.persistence.jpa.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectCommandServiceImpl implements ProjectCommandService {

  private final ProjectRepository projectRepository;
  private final ExternalCloudinaryService externalCloudinaryService;

  public ProjectCommandServiceImpl(
      ProjectRepository projectRepository,
      ExternalCloudinaryService externalCloudinaryService
  ) {
    this.projectRepository = projectRepository;
    this.externalCloudinaryService = externalCloudinaryService;
  }

  // =========================
  // PROJECTS
  // =========================

  @Override
  public Optional<Project> handle(CreateProjectCommand command) {

    Project project = Project.create(
        command.ownerId(),
        command.title(),
        command.description()
    );

    projectRepository.save(project);

    return Optional.of(project);
  }

  @Override
  public void handle(DeleteProjectCommand command) {

    Project project = getProject(command.projectId());

    projectRepository.delete(project);

    deleteProjectPanoramas(project);
  }

  @Override
  public Optional<Project> handle(PublishProjectCommand command) {

    Project project = getProject(command.projectId());

    project.publish();

    projectRepository.save(project);

    return Optional.of(project);
  }

  @Override
  public Optional<Project> handle(UnpublishProjectCommand command) {

    Project project = getProject(command.projectId());

    project.unpublish();

    projectRepository.save(project);

    return Optional.of(project);
  }

  // =========================
  // NODES
  // =========================

  @Override
  public Optional<Node> handle(CreateNodeCommand command) {

    Project project = getProject(command.projectId());

    var optionalUpload =
        externalCloudinaryService.uploadImage(command.file());

    if (optionalUpload.isEmpty()) {
      throw new RuntimeException("Unable to upload image");
    }

    var upload = optionalUpload.get();

    String thumbnailUrl = generateThumbnailUrl(upload.url());

    Node node = Node.create(
        upload.url(),
        thumbnailUrl,
        command.caption(),
        upload.publicId()
    );

    project.addNode(node);

    projectRepository.save(project);

    return Optional.of(node);
  }

  @Override
  public void handle(RemoveNodeCommand command) {

    Project project = getProject(command.projectId());

    Node node = project.findNode(command.nodeId());

    project.removeNode(node);

    projectRepository.save(project);

    deletePanorama(node);
  }

  // =========================
  // LINKS
  // =========================

  @Override
  public Optional<Link> handle(ConnectNodesCommand command) {

    Project project = getProject(command.projectId());

    Node from = project.findNode(command.fromNodeId());
    Node to = project.findNode(command.toNodeId());

    Link link = from.connectTo(
        to,
        new Position(command.yaw(), command.pitch())
    );

    projectRepository.save(project);

    return Optional.of(link);
  }

  @Override
  public void handle(RemoveLinkCommand command) {

    Project project = getProject(command.projectId());

    Node fromNode = project.findNode(command.fromNodeId());

    Link link = fromNode.getLinks().stream()
        .filter(l -> l.getId().equals(command.linkId()))
        .findFirst()
        .orElseThrow(() ->
            new IllegalArgumentException("Link not found"));

    fromNode.removeLink(link);

    projectRepository.save(project);
  }

  // =========================
  // MARKERS
  // =========================

  @Override
  public Optional<Marker> handle(AddMarkerCommand command) {

    Project project = getProject(command.projectId());

    Node node = project.findNode(command.nodeId());

    Marker marker = Marker.create(
        command.type(),
        new Position(command.yaw(), command.pitch()),
        command.tooltip(),
        command.title(),
        command.content(),
        command.description()
    );

    node.addMarker(marker);

    projectRepository.save(project);

    return Optional.of(marker);
  }

  @Override
  public void handle(RemoveMarkerCommand command) {

    Project project = getProject(command.projectId());

    Node node = project.findNode(command.nodeId());

    Marker marker = node.getMarkers().stream()
        .filter(m -> m.getId().equals(command.markerId()))
        .findFirst()
        .orElseThrow(() ->
            new IllegalArgumentException("Marker not found"));

    node.removeMarker(marker);

    projectRepository.save(project);
  }

  // =========================
  // HELPERS
  // =========================

  private Project getProject(UUID projectId) {
    return projectRepository.findById(projectId)
        .orElseThrow(() ->
            new IllegalArgumentException("Project not found"));
  }

  private String generateThumbnailUrl(String url) {
    return url.replace(
        "/upload/",
        "/upload/w_300,c_limit,q_auto,f_auto/"
    );
  }

  private void deletePanorama(Node node) {
    if (node.getPanoramaPublicId() != null) {
      externalCloudinaryService.deleteImage(
          node.getPanoramaPublicId()
      );
    }
  }

  private void deleteProjectPanoramas(Project project) {

    project.getNodes().stream()
        .map(Node::getPanoramaPublicId)
        .filter(id -> id != null && !id.isBlank())
        .forEach(externalCloudinaryService::deleteImage);
  }
}
