package com.example.virtu360.tour.domain.model.entities;

import com.example.virtu360.shared.domain.model.entities.AuditableModel;
import com.example.virtu360.tour.domain.model.aggregates.Project;
import com.example.virtu360.tour.domain.model.valueobjects.Position;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Table(name = "nodes")
@Getter
@NoArgsConstructor
public class Node extends AuditableModel {

  @Id
  private UUID id;

  @Column(name = "panorama_url", nullable = false, columnDefinition = "TEXT")
  private String panoramaUrl;

  @Column(name = "thumbnail_url", columnDefinition = "TEXT")
  private String thumbnailUrl;

  @Column(length = 100)
  private String caption;

  @Column(length = 255)
  private String panoramaPublicId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id", nullable = false)
  private Project project;

  @OneToMany(mappedBy = "fromNode", cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<Link> links = new ArrayList<>();

  @OneToMany(mappedBy = "node", cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<Marker> markers = new ArrayList<>();

  // =========================
  // FACTORY
  // =========================
  public static Node create(
      String panoramaUrl,
      String thumbnailUrl,
      String caption,
      String panoramaPublicId
  ) {
    Node node = new Node();
    node.id = UUID.randomUUID();
    node.panoramaUrl = Objects.requireNonNull(panoramaUrl);
    node.thumbnailUrl = thumbnailUrl;
    node.caption = caption;
    node.panoramaPublicId = panoramaPublicId;
    return node;
  }

  // =========================
  // READ MODEL
  // =========================
  public List<Link> getLinks() {
    return Collections.unmodifiableList(links);
  }

  public List<Marker> getMarkers() {
    return Collections.unmodifiableList(markers);
  }

  // =========================
  // RELATION MANAGEMENT
  // =========================
  public void assignTo(Project project) {
    this.project = Objects.requireNonNull(project);
  }

  public void removeFromProject() {
    this.project = null;
  }

  // =========================
  // MARKERS
  // =========================
  public void addMarker(Marker marker) {
    Objects.requireNonNull(marker);

    boolean exists = markers.stream()
        .anyMatch(m -> m.getId().equals(marker.getId()));

    if (exists) {
      throw new IllegalStateException("Marker already exists");
    }

    markers.add(marker);
    marker.assignTo(this);
  }

  public void removeMarker(Marker marker) {
    markers.remove(marker);
    marker.removeFromNode();
  }

  public Marker findMarker(UUID markerId) {

    return markers.stream()
        .filter(marker ->
            marker.getId().equals(markerId)
        )
        .findFirst()
        .orElseThrow(() ->
            new IllegalArgumentException(
                "Marker not found"
            ));
  }

  public InfoMarker findInfoMarker(UUID markerId) {

    Marker marker = findMarker(markerId);

    if (!(marker instanceof InfoMarker infoMarker)) {
      throw new IllegalArgumentException(
          "Marker is not INFO type"
      );
    }

    return infoMarker;
  }

  public VideoMarker findVideoMarker(UUID markerId) {

    Marker marker = findMarker(markerId);

    if (!(marker instanceof VideoMarker videoMarker)) {
      throw new IllegalArgumentException(
          "Marker is not VIDEO type"
      );
    }

    return videoMarker;
  }

  public GalleryMarker findGalleryMarker(UUID markerId) {

    Marker marker = findMarker(markerId);

    if (!(marker instanceof GalleryMarker galleryMarker)) {
      throw new IllegalArgumentException(
          "Marker is not GALLERY type"
      );
    }

    return galleryMarker;
  }

  // =========================
  // LINKS
  // =========================
  public Link connectTo(Node target, Position position) {

    Objects.requireNonNull(target);
    Objects.requireNonNull(position);

    if (!this.project.equals(target.getProject())) {
      throw new IllegalStateException(
          "Cannot connect nodes from different projects"
      );
    }

    if (this.equals(target)) {
      throw new IllegalStateException(
          "Cannot connect node to itself"
      );
    }

    boolean exists = links.stream()
        .anyMatch(l -> l.getToNodeId().equals(target.getId()));

    if (exists) {
      throw new IllegalStateException(
          "Link already exists"
      );
    }

    Link link = Link.create(
        target.getId(),
        position
    );

    links.add(link);

    link.assignFrom(this);

    return link;
  }

  public void removeLink(Link link) {
    links.remove(link);
    link.removeFromNode();
  }

  public Link findLink(UUID linkId) {

    return links.stream()
        .filter(link ->
            link.getId().equals(linkId)
        )
        .findFirst()
        .orElseThrow(() ->
            new IllegalArgumentException(
                "Link not found"
            ));
  }

  // =========================
  // EQUALITY
  // =========================
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Node node)) return false;
    return id != null && id.equals(node.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
