package com.example.virtu360.tour.application.internal.queryservices;

import com.example.virtu360.tour.domain.model.entities.Node;
import com.example.virtu360.tour.domain.model.entities.Link;
import com.example.virtu360.tour.domain.model.entities.Marker;
import com.example.virtu360.tour.domain.model.queries.GetAllNodesQuery;
import com.example.virtu360.tour.domain.model.queries.GetLinksByNodeIdQuery;
import com.example.virtu360.tour.domain.model.queries.GetMarkersByNodeIdQuery;
import com.example.virtu360.tour.domain.model.queries.GetNodeByIdQuery;
import com.example.virtu360.tour.domain.services.NodeQueryService;
import com.example.virtu360.tour.infrastructure.persistence.jpa.repositories.NodeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NodeQueryServiceImpl implements NodeQueryService {

  private final NodeRepository nodeRepository;

  public NodeQueryServiceImpl(NodeRepository nodeRepository) {
    this.nodeRepository = nodeRepository;
  }

  @Override
  public List<Node> handle(GetAllNodesQuery query) {
    return nodeRepository.findAll();
  }

  @Override
  public Optional<Node> handle(GetNodeByIdQuery query) {
    return nodeRepository.findById(query.nodeId());
  }

  @Override
  public List<Link> handle(GetLinksByNodeIdQuery query) {
    var node = nodeRepository.findById(query.nodeId())
      .orElseThrow(() -> new RuntimeException("Node not found"));

    return node.getLinks();
  }

  @Override
  public List<Marker> handle(GetMarkersByNodeIdQuery query) {
    var node = nodeRepository.findById(query.nodeId())
      .orElseThrow(() -> new RuntimeException("Node not found"));

    return node.getMarkers();
  }
}
