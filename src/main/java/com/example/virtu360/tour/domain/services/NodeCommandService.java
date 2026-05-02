package com.example.virtu360.tour.domain.services;

import com.example.virtu360.tour.domain.model.aggregates.Node;
import com.example.virtu360.tour.domain.model.commands.AddMarkerCommand;
import com.example.virtu360.tour.domain.model.commands.ConnectNodesCommand;
import com.example.virtu360.tour.domain.model.commands.CreateNodeCommand;
import com.example.virtu360.tour.domain.model.entities.Link;

import java.util.Optional;

public interface NodeCommandService {

  Optional<Node> handle(CreateNodeCommand command);

  Optional<Link> handle(ConnectNodesCommand command);

  void handle(AddMarkerCommand command);
}
