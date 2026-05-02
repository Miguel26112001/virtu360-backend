package com.example.virtu360.tour.interfaces.rest.transform;

import com.example.virtu360.tour.domain.model.entities.Marker;
import com.example.virtu360.tour.interfaces.rest.resources.MarkerResponse;
import com.example.virtu360.tour.interfaces.rest.resources.PositionResource;

public class MarkerResourceFromEntityAssembler {

  private MarkerResourceFromEntityAssembler() {}

  public static MarkerResponse toResourceFromEntity(Marker marker) {
    return new MarkerResponse(
      marker.getId(),
      marker.getNode().getId().toString(),
      marker.getType().toString(),
      new PositionResource(
        marker.getPosition().yaw(),
        marker.getPosition().pitch()
      ),
      marker.getTooltip(),
      marker.getTitle(),
      marker.getContent(),
      marker.getDescription()
    );
  }
}
