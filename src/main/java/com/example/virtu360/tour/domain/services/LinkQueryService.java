package com.example.virtu360.tour.domain.services;

import com.example.virtu360.tour.domain.model.aggregates.Node;
import com.example.virtu360.tour.domain.model.queries.GetLinksByNodeIdQuery;

import java.util.List;

public interface LinkQueryService {

  List<Node> handle(GetLinksByNodeIdQuery query);
}
