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

    Optional<Project> projectOpt = projectRepository.findById(query.projectId());

    projectOpt.ifPresent(project -> {
      project.getNodes().size();
    });

    return projectOpt;
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

    List<Project> projects = projectRepository.findByPublishedTrue();

    projects.forEach(project -> project.getNodes().size());

    return projects;
  }

  // =========================
  // NODES
  // =========================

  @Override
  @Transactional(readOnly = true)
  public List<Node> handle(
      GetNodesByProjectIdQuery query
  ) {

    return nodeRepository.findByProjectId(
        query.projectId()
    );
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Node> handle(
      GetNodeByIdQuery query
  ) {

    validateProject(query.projectId());

    Optional<Node> nodeOpt = nodeRepository.findById(query.nodeId());

    nodeOpt.ifPresent(node -> {
      node.getLinks().size();
      node.getMarkers().size();
    });

    return nodeOpt;
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Node> handle(
      GetStartingNodeQuery query
  ) {

    Project project = getProject(query.projectId());

    if (project.getStartingNodeId() == null) {
      return Optional.empty();
    }

    Optional<Node> nodeOpt = nodeRepository.findById(project.getStartingNodeId());

    nodeOpt.ifPresent(node -> {
      node.getLinks().size();
      node.getMarkers().size();
    });

    return nodeOpt;
  }

  // =========================
  // LINKS
  // =========================

  @Override
  @Transactional(readOnly = true)
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
  @Transactional(readOnly = true)
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
  @Transactional(readOnly = true)
  public List<InfoMarker> handle(
      GetInfoMarkersByNodeIdQuery query
  ) {

    validateNode(
        query.projectId(),
        query.nodeId()
    );

    return markerRepository
        .findByNodeIdAndType(query.nodeId(), MarkerType.INFO)
        .stream()
        .map(InfoMarker.class::cast)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<VideoMarker> handle(
      GetVideoMarkersByNodeIdQuery query
  ) {

    validateNode(
        query.projectId(),
        query.nodeId()
    );

    return markerRepository
        .findByNodeIdAndType(query.nodeId(), MarkerType.VIDEO)
        .stream()
        .map(VideoMarker.class::cast)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<GalleryMarker> handle(
      GetGalleryMarkersByNodeIdQuery query
  ) {

    validateNode(
        query.projectId(),
        query.nodeId()
    );

    return markerRepository
        .findByNodeIdAndType(query.nodeId(), MarkerType.GALLERY)
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

    if (!nodeRepository.existsById(nodeId)) {
      throw new IllegalArgumentException("Node not found");
    }

    boolean belongsToProject = nodeRepository.existsByIdAndProjectId(nodeId, projectId);
    if (!belongsToProject) {
      throw new IllegalArgumentException("Node does not belong to project");
    }
  }
}
