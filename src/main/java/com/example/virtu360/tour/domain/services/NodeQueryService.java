package com.example.virtu360.tour.domain.services;

import com.example.virtu360.tour.domain.model.aggregates.Node;
import com.example.virtu360.tour.domain.model.entities.Link;
import com.example.virtu360.tour.domain.model.entities.Marker;
import com.example.virtu360.tour.domain.model.queries.GetAllNodesQuery;
import com.example.virtu360.tour.domain.model.queries.GetLinksByNodeIdQuery;
import com.example.virtu360.tour.domain.model.queries.GetMarkersByNodeIdQuery;
import com.example.virtu360.tour.domain.model.queries.GetNodeByIdQuery;

import java.util.List;
import java.util.Optional;

public interface NodeQueryService {

  List<Node> handle(GetAllNodesQuery query);

  Optional<Node> handle(GetNodeByIdQuery query);

  List<Link> handle(GetLinksByNodeIdQuery query);

  List<Marker> handle(GetMarkersByNodeIdQuery query);
}
