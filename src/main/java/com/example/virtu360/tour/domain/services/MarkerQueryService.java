package com.example.virtu360.tour.domain.services;

import com.example.virtu360.tour.domain.model.entities.Marker;
import com.example.virtu360.tour.domain.model.queries.GetMarkersByNodeIdQuery;

import java.util.List;

public interface MarkerQueryService {

  List<Marker> handle(GetMarkersByNodeIdQuery query);
}
