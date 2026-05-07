package com.example.virtu360.tour.domain.services;

import com.example.virtu360.tour.domain.model.aggregates.Project;
import com.example.virtu360.tour.domain.model.commands.*;
import com.example.virtu360.tour.domain.model.entities.Link;
import com.example.virtu360.tour.domain.model.entities.Marker;
import com.example.virtu360.tour.domain.model.entities.Node;

import java.util.Optional;

public interface ProjectCommandService {

  // PROJECTS
  Optional<Project> handle(CreateProjectCommand command);

  void handle(DeleteProjectCommand command);

  Optional<Project> handle(PublishProjectCommand command);

  Optional<Project> handle(UnpublishProjectCommand command);

  // NODES
  Optional<Node> handle(CreateNodeCommand command);

  void handle(RemoveNodeCommand command);

  // LINKS
  Optional<Link> handle(ConnectNodesCommand command);

  void handle(RemoveLinkCommand command);

  // MARKERS
  Optional<Marker> handle(AddMarkerCommand command);

  void handle(RemoveMarkerCommand command);
}
