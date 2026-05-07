package com.example.virtu360.tour.domain.services;

import com.example.virtu360.tour.domain.model.aggregates.Project;
import com.example.virtu360.tour.domain.model.entities.Link;
import com.example.virtu360.tour.domain.model.entities.Marker;
import com.example.virtu360.tour.domain.model.entities.Node;
import com.example.virtu360.tour.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface ProjectQueryService {

  // =========================
  // PROJECTS
  // =========================
  Optional<Project> handle(GetProjectByIdQuery query);

  Optional<Project> handle(GetPublishedProjectByIdQuery query);

  List<Project> handle(GetProjectsByOwnerIdQuery query);

  List<Project> handle(GetPublishedProjectsQuery query);

  // =========================
  // NODES
  // =========================
  List<Node> handle(GetNodesByProjectIdQuery query);

  Optional<Node> handle(GetNodeByIdQuery query);

  Optional<Node> handle(GetStartingNodeQuery query);

  // =========================
  // LINKS
  // =========================
  List<Link> handle(GetLinksByNodeIdQuery query);

  // =========================
  // MARKERS
  // =========================
  List<Marker> handle(GetMarkersByNodeIdQuery query);
}
