package com.example.virtu360.tour.application.internal.queryservices;

import com.example.virtu360.tour.domain.model.aggregates.Project;
import com.example.virtu360.tour.domain.model.entities.*;
import com.example.virtu360.tour.domain.model.queries.*;
import com.example.virtu360.tour.domain.model.valueobjects.MarkerType;
import com.example.virtu360.tour.domain.services.ProjectQueryService;
import com.example.virtu360.tour.infrastructure.persistence.jpa.repositories.LinkRepository;
import com.example.virtu360.tour.infrastructure.persistence.jpa.repositories.MarkerRepository;
import com.example.virtu360.tour.infrastructure.persistence.jpa.repositories.NodeRepository;
import com.example.virtu360.tour.infrastructure.persistence.jpa.repositories.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectQueryServiceImpl implements ProjectQueryService {

  private final ProjectRepository projectRepository;
  private final NodeRepository nodeRepository;
  private final LinkRepository linkRepository;
  private final MarkerRepository markerRepository;

  public ProjectQueryServiceImpl(
      ProjectRepository projectRepository,
      NodeRepository nodeRepository,
      LinkRepository linkRepository,
      MarkerRepository markerRepository
  ) {
    this.projectRepository = projectRepository;
    this.nodeRepository = nodeRepository;
    this.linkRepository = linkRepository;
    this.markerRepository = markerRepository;
  }

  // =========================
  // PROJECTS
  // =========================

  @Override
  @Transactional(readOnly = true)
  public Optional<Project> handle(GetProjectByIdQuery query) {

    return projectRepository.findById(query.projectId());
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Project> handle(
      GetPublishedProjectByIdQuery query
  ) {

    return projectRepository.findById(query.projectId())
        .filter(Project::isPublished);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Project> handle(
      GetProjectsByOwnerIdQuery query
  ) {
    List<Project> projects = projectRepository.findByOwnerId(query.ownerId());

    projects.forEach(project -> project.getNodes().size());
    return projects;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Project> handle(
      GetPublishedProjectsQuery query
  ) {

    return projectRepository.findByPublishedTrue();
  }

  // =========================
  // NODES
  // =========================

  @Override
  public List<Node> handle(
      GetNodesByProjectIdQuery query
  ) {

    return nodeRepository.findByProjectId(
        query.projectId()
    );
  }

  @Override
  public Optional<Node> handle(
      GetNodeByIdQuery query
  ) {

    validateProject(query.projectId());

    return nodeRepository.findById(query.nodeId());
  }

  @Override
  public Optional<Node> handle(
      GetStartingNodeQuery query
  ) {

    Project project = getProject(query.projectId());

    if (project.getStartingNodeId() == null) {
      return Optional.empty();
    }

    return nodeRepository.findById(
        project.getStartingNodeId()
    );
  }

  // =========================
  // LINKS
  // =========================

  @Override
  public List<Link> handle(
      GetLinksByNodeIdQuery query
  ) {

    validateNode(
        query.projectId(),
        query.nodeId()
    );

    return linkRepository.findByFromNodeId(
        query.nodeId()
    );
  }

  // =========================
  // MARKERS
  // =========================

  @Override
  public List<Marker> handle(
      GetMarkersByNodeIdQuery query
  ) {

    validateNode(
        query.projectId(),
        query.nodeId()
    );

    return markerRepository.findByNodeId(
        query.nodeId()
    );
  }

  @Override
  public List<InfoMarker> handle(
      GetInfoMarkersByNodeIdQuery query
  ) {

    validateNode(
        query.projectId(),
        query.nodeId()
    );

    return markerRepository
        .findByNodeIdAndType(
            query.nodeId(),
            MarkerType.INFO
        )
        .stream()
        .map(InfoMarker.class::cast)
        .toList();
  }

  @Override
  public List<VideoMarker> handle(
      GetVideoMarkersByNodeIdQuery query
  ) {

    validateNode(
        query.projectId(),
        query.nodeId()
    );

    return markerRepository
        .findByNodeIdAndType(
            query.nodeId(),
            MarkerType.VIDEO
        )
        .stream()
        .map(VideoMarker.class::cast)
        .toList();
  }

  @Override
  public List<GalleryMarker> handle(
      GetGalleryMarkersByNodeIdQuery query
  ) {

    validateNode(
        query.projectId(),
        query.nodeId()
    );

    return markerRepository
        .findByNodeIdAndType(
            query.nodeId(),
            MarkerType.GALLERY
        )
        .stream()
        .map(GalleryMarker.class::cast)
        .toList();
  }

  // =========================
  // HELPERS
  // =========================

  private Project getProject(UUID projectId) {

    return projectRepository.findById(projectId)
        .orElseThrow(() ->
            new IllegalArgumentException(
                "Project not found"
            ));
  }

  private void validateProject(UUID projectId) {

    if (!projectRepository.existsById(projectId)) {
      throw new IllegalArgumentException(
          "Project not found"
      );
    }
  }

  private void validateNode(
      UUID projectId,
      UUID nodeId
  ) {

    Node node = nodeRepository.findById(nodeId)
        .orElseThrow(() ->
            new IllegalArgumentException(
                "Node not found"
            ));

    if (!node.getProject().getId().equals(projectId)) {
      throw new IllegalArgumentException(
          "Node does not belong to project"
      );
    }
  }
}
