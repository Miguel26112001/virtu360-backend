package com.example.virtu360.tour.domain.services;

import com.example.virtu360.tour.domain.model.aggregates.Node;
import com.example.virtu360.tour.domain.model.queries.GetAllNodesQuery;
import com.example.virtu360.tour.domain.model.queries.GetNodeByIdQuery;

import java.util.List;
import java.util.Optional;

public interface NodeQueryService {

  List<Node> handle(GetAllNodesQuery query);

  Optional<Node> handle(GetNodeByIdQuery query);
}
