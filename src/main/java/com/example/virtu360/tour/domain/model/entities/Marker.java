package com.example.virtu360.tour.domain.model.entities;

import com.example.virtu360.shared.domain.model.entities.AuditableModel;
import com.example.virtu360.tour.domain.model.valueobjects.MarkerType;
import com.example.virtu360.tour.domain.model.valueobjects.Position;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "markers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name = "marker_type",
    discriminatorType = DiscriminatorType.STRING
)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = InfoMarker.class, name = "INFO"),
    @JsonSubTypes.Type(value = VideoMarker.class, name = "VIDEO"),
    @JsonSubTypes.Type(value = GalleryMarker.class, name = "GALLERY")
})
@Getter
@NoArgsConstructor
public abstract class Marker extends AuditableModel {

  @Id
  private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(
      name = "marker_type",
      insertable = false,
      updatable = false
  )
  private MarkerType type;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "node_id", nullable = false)
  private Node node;

  @Embedded
  private Position position;

  @Column(length = 150)
  private String title;

  @Column(length = 150)
  private String tooltip;

  @Column(columnDefinition = "TEXT")
  private String summary;

  protected Marker(
      Position position,
      String title,
      String tooltip,
      String summary
  ) {

    this.id = UUID.randomUUID();
    this.position = Objects.requireNonNull(position);
    this.title = title;
    this.tooltip = tooltip;
    this.summary = summary;
  }

  protected void updateBase(
      Position position,
      String title,
      String tooltip,
      String summary
  ) {
    this.position = position;
    this.title = title;
    this.tooltip = tooltip;
    this.summary = summary;
  }

  // =========================
  // RELATION MANAGEMENT
  // =========================

  public void assignTo(Node node) {
    this.node = Objects.requireNonNull(node);
  }

  public void removeFromNode() {
    this.node = null;
  }

  // =========================
  // EQUALITY
  // =========================

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (!(o instanceof Marker marker)) {
      return false;
    }

    return id != null && id.equals(marker.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}

