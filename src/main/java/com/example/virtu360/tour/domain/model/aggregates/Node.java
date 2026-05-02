package com.example.virtu360.tour.domain.model.aggregates;

import com.example.virtu360.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.example.virtu360.tour.domain.model.entities.Link;
import com.example.virtu360.tour.domain.model.entities.Marker;
import com.example.virtu360.tour.domain.model.valueobjects.Position;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "nodes")
@Getter
@NoArgsConstructor
public class Node extends AuditableAbstractAggregateRoot<Node> {

  @Column(name = "panorama_url", nullable = false, columnDefinition = "TEXT")
  private String panoramaUrl;

  @Column(name = "thumbnail_url", columnDefinition = "TEXT")
  private String thumbnailUrl;

  @Column(length = 100)
  private String caption;

  @Column(length = 255)
  private String panoramaPublicId;

  @OneToMany(mappedBy = "fromNode", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Link> links = new ArrayList<>();

  @OneToMany(mappedBy = "node", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Marker> markers = new ArrayList<>();

  // =========================
  // FACTORY
  // =========================
  public static Node create(
      String panoramaUrl,
      String thumbnailUrl,
      String caption,
      String panoramaPublicId) {
    Node node = new Node();
    node.panoramaUrl = Objects.requireNonNull(panoramaUrl);
    node.thumbnailUrl = thumbnailUrl;
    node.caption = caption;
    node.panoramaPublicId = panoramaPublicId;
    return node;
  }

  // =========================
  // MARKERS
  // =========================
  public void addMarker(Marker marker) {
    markers.add(marker);
    marker.assignTo(this);
  }

  public void removeMarker(Marker marker) {
    markers.remove(marker);
    marker.removeFromNode();
  }

  // =========================
  // LINKS
  // =========================
  public Link connectTo(Node target, Position position) {
    Objects.requireNonNull(target, "Target node cannot be null");
    Objects.requireNonNull(position, "Position cannot be null");

    boolean exists = links.stream()
        .anyMatch(l -> l.getToNodeId().equals(target.getId().toString()));

    if (exists) {
      throw new IllegalStateException("Link already exists to this node");
    }

    Link link = Link.create(target.getId().toString(), position);
    links.add(link);
    link.assignFrom(this);

    return link;
  }

  public void removeLink(Link link) {
    links.remove(link);
    link.removeFromNode();
  }
}
