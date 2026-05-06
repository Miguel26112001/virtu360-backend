package com.example.virtu360.tour.domain.model.entities;

import com.example.virtu360.shared.domain.model.entities.AuditableModel;
import com.example.virtu360.tour.domain.model.valueobjects.Position;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
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
@Table(name = "links")
@Getter
@NoArgsConstructor
public class Link extends AuditableModel {

  @Id
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "from_node_id", nullable = false)
  private Node fromNode;

  @Column(name = "to_node_id", nullable = false)
  private UUID toNodeId;

  @Embedded
  private Position position;

  // =========================
  // FACTORY
  // =========================
  public static Link create(UUID toNodeId, Position position) {
    Link link = new Link();
    link.id = UUID.randomUUID();
    link.toNodeId = Objects.requireNonNull(toNodeId);
    link.position = Objects.requireNonNull(position);
    return link;
  }

  // =========================
  // RELATION MANAGEMENT
  // =========================
  public void assignFrom(Node node) {
    this.fromNode = Objects.requireNonNull(node);
  }

  public void removeFromNode() {
    this.fromNode = null;
  }

  // =========================
  // EQUALITY
  // =========================
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Link link)) return false;
    return id != null && id.equals(link.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
