package com.example.virtu360.tour.interfaces.rest.transform;

import com.example.virtu360.tour.domain.model.entities.Marker;
import com.example.virtu360.tour.interfaces.rest.resources.MarkerResource;
import com.example.virtu360.tour.interfaces.rest.resources.PositionResource;

public class MarkerResourceFromEntityAssembler {

  private MarkerResourceFromEntityAssembler() {
  }

  public static MarkerResource toResourceFromEntity(Marker marker) {

    return new MarkerResource(
        marker.getId(),
        marker.getNode().getId(),
        marker.getType().toString().toLowerCase(),
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