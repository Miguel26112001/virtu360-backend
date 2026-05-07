package com.example.virtu360.tour.application.internal.queryservices;

import com.example.virtu360.tour.domain.model.aggregates.Project;
import com.example.virtu360.tour.domain.model.entities.Node;
import com.example.virtu360.tour.domain.model.entities.Link;
import com.example.virtu360.tour.domain.model.entities.Marker;
import com.example.virtu360.tour.domain.model.queries.*;
import com.example.virtu360.tour.domain.services.ProjectQueryService;
import com.example.virtu360.tour.infrastructure.persistence.jpa.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectQueryServiceImpl implements ProjectQueryService {

  private final ProjectRepository projectRepository;

  public ProjectQueryServiceImpl(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  // =========================
  // PROJECTS
  // =========================

  @Override
  public Optional<Project> handle(GetProjectByIdQuery query) {
    return projectRepository.findById(query.projectId());
  }

  @Override
  public Optional<Project> handle(GetPublishedProjectByIdQuery query) {

    return projectRepository.findById(query.projectId())
        .filter(Project::isPublished);
  }

  @Override
  public List<Project> handle(GetProjectsByOwnerIdQuery query) {

    return projectRepository.findByOwnerId(query.ownerId());
  }

  @Override
  public List<Project> handle(GetPublishedProjectsQuery query) {

    return projectRepository.findByPublishedTrue();
  }

  // =========================
  // NODES
  // =========================

  @Override
  public List<Node> handle(GetNodesByProjectIdQuery query) {

    Project project = getProject(query.projectId());

    return project.getNodes();
  }

  @Override
  public Optional<Node> handle(GetNodeByIdQuery query) {

    Project project = getProject(query.projectId());

    return project.getNodes().stream()
        .filter(node -> node.getId().equals(query.nodeId()))
        .findFirst();
  }

  @Override
  public Optional<Node> handle(GetStartingNodeQuery query) {

    Project project = getProject(query.projectId());

    return project.getNodes().stream().findFirst();
  }

  // =========================
  // LINKS
  // =========================

  @Override
  public List<Link> handle(GetLinksByNodeIdQuery query) {

    Node node = getNode(query.projectId(), query.nodeId());

    return node.getLinks();
  }

  // =========================
  // MARKERS
  // =========================

  @Override
  public List<Marker> handle(GetMarkersByNodeIdQuery query) {

    Node node = getNode(query.projectId(), query.nodeId());

    return node.getMarkers();
  }

  // =========================
  // HELPERS
  // =========================

  private Project getProject(UUID projectId) {

    return projectRepository.findById(projectId)
        .orElseThrow(() ->
            new IllegalArgumentException("Project not found"));
  }

  private Node getNode(
      UUID projectId,
      UUID nodeId
  ) {

    Project project = getProject(projectId);

    return project.getNodes().stream()
        .filter(node -> node.getId().equals(nodeId))
        .findFirst()
        .orElseThrow(() ->
            new IllegalArgumentException("Node not found"));
  }
}
