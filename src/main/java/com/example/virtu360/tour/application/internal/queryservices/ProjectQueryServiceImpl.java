package com.example.virtu360.tour.application.internal.queryservices;

import com.example.virtu360.tour.domain.model.entities.Node;
import com.example.virtu360.tour.domain.model.entities.Link;
import com.example.virtu360.tour.domain.model.entities.Marker;
import com.example.virtu360.tour.domain.model.queries.GetAllNodesQuery;
import com.example.virtu360.tour.domain.model.queries.GetLinksByNodeIdQuery;
import com.example.virtu360.tour.domain.model.queries.GetMarkersByNodeIdQuery;
import com.example.virtu360.tour.domain.model.queries.GetNodeByIdQuery;
import com.example.virtu360.tour.domain.services.ProjectQueryService;
import com.example.virtu360.tour.infrastructure.persistence.jpa.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectQueryServiceImpl implements ProjectQueryService {

  private final ProjectRepository projectRepository;

  public ProjectQueryServiceImpl(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public List<Node> handle(GetAllNodesQuery query) {
    return projectRepository.findAll();
  }

  @Override
  public Optional<Node> handle(GetNodeByIdQuery query) {
    return projectRepository.findById(query.nodeId());
  }

  @Override
  public List<Link> handle(GetLinksByNodeIdQuery query) {
    var node = projectRepository.findById(query.nodeId())
      .orElseThrow(() -> new RuntimeException("Node not found"));

    return node.getLinks();
  }

  @Override
  public List<Marker> handle(GetMarkersByNodeIdQuery query) {
    var node = projectRepository.findById(query.nodeId())
      .orElseThrow(() -> new RuntimeException("Node not found"));

    return node.getMarkers();
  }
}
