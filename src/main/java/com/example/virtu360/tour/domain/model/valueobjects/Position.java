package com.example.virtu360.tour.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Position(double yaw, double pitch) {

  public static Position of(double yaw, double pitch) {
    return new Position(yaw, pitch);
  }
}
