package com.example.virtu360.tour.domain.model.entities;

import com.example.virtu360.shared.domain.model.entities.AuditableModel;
import com.example.virtu360.tour.domain.model.aggregates.Node;
import com.example.virtu360.tour.domain.model.valueobjects.MarkerType;
import com.example.virtu360.tour.domain.model.valueobjects.Position;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "markers")
@Getter
@NoArgsConstructor
public class Marker extends AuditableModel {

  @Id
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "node_id", nullable = false)
  private Node node;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private MarkerType type;

  @Embedded
  private Position position;

  @Column(length = 150)
  private String tooltip;

  @Column(length = 150)
  private String title;

  @Column(columnDefinition = "TEXT")
  private String content;

  @Column(columnDefinition = "TEXT")
  private String description;

  // =========================
  // FACTORY
  // =========================
  public static Marker create(
      MarkerType type,
      Position position,
      String tooltip,
      String title,
      String content,
      String description
  ) {
    Marker marker = new Marker();
    marker.id = UUID.randomUUID().toString();
    marker.type = Objects.requireNonNull(type);
    marker.position = Objects.requireNonNull(position);
    marker.tooltip = tooltip;
    marker.title = title;
    marker.content = content;
    marker.description = description;
    return marker;
  }

  // =========================
  // RELATION MANAGEMENT
  // =========================
  public void assignTo(Node node) {
    this.node = node;
  }

  public void removeFromNode() {
    this.node = null;
  }
}
