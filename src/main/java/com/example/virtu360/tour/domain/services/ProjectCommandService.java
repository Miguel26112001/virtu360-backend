package com.example.virtu360.tour.domain.services;

import com.example.virtu360.tour.domain.model.aggregates.Project;
import com.example.virtu360.tour.domain.model.commands.*;

import java.util.Optional;

public interface ProjectCommandService {

  Optional<Project> handle(CreateProjectCommand command);

  Optional<Project> handle(DeleteProjectCommand command);

  Optional<Project> handle(PublishProjectCommand command);

  Optional<Project> handle(UnpublishProjectCommand command);

  Optional<Project> handle(CreateNodeCommand command);

  Optional<Project> handle(RemoveNodeCommand command);

  Optional<Project> handle(ConnectNodesCommand command);

  Optional<Project> handle(RemoveLinkCommand command);

  Optional<Project> handle(AddMarkerCommand command);

  Optional<Project> handle(RemoveMarkerCommand command);
}
