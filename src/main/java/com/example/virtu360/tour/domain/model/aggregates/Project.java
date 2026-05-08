package com.example.virtu360.tour.domain.model.aggregates;

import com.example.virtu360.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.example.virtu360.tour.domain.model.entities.Node;
import com.example.virtu360.tour.domain.model.valueobjects.Position;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Table(name = "projects")
@Getter
@NoArgsConstructor
public class Project extends AuditableAbstractAggregateRoot<Project> {

  @Column(nullable = false)
  private String ownerId;

  @Column(nullable = false, length = 150)
  private String title;

  @Column(length = 500)
  private String description;

  @Column(nullable = false)
  private boolean published = false;

  @Column(name = "starting_node_id")
  private UUID startingNodeId;

  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<Node> nodes = new ArrayList<>();

  // =========================
  // FACTORY
  // =========================
  public static Project create(
      String ownerId,
      String title,
      String description
  ) {

    Project project = new Project();

    project.ownerId = ownerId;
    project.title = title;
    project.description = description;
    project.published = false;

    return project;
  }

  // =========================
  // READ MODEL
  // =========================

  public List<Node> getNodes() {
    return Collections.unmodifiableList(nodes);
  }

  // =========================
  // NODE MANAGEMENT
  // =========================

  public void addNode(Node node) {

    Objects.requireNonNull(node);

    boolean exists = nodes.stream()
        .anyMatch(n -> n.getId().equals(node.getId()));

    if (exists) {
      throw new IllegalStateException(
          "Node already exists in project"
      );
    }

    node.assignTo(this);

    nodes.add(node);

    if (startingNodeId == null) {
      startingNodeId = node.getId();
    }
  }

  public void removeNode(Node node) {

    Objects.requireNonNull(node);

    boolean hasIncomingLinks = nodes.stream()
        .flatMap(n -> n.getLinks().stream())
        .anyMatch(l -> l.getToNodeId().equals(node.getId()));

    boolean hasOutgoingLinks = !node.getLinks().isEmpty();

    if (hasIncomingLinks || hasOutgoingLinks) {
      throw new IllegalStateException(
          "Cannot remove node with active links"
      );
    }

    nodes.remove(node);

    node.removeFromProject();

    if (node.getId().equals(startingNodeId)) {

      startingNodeId = nodes.isEmpty()
          ? null
          : nodes.getFirst().getId();
    }
  }

  public Node findNode(UUID nodeId) {
    return nodes.stream()
        .filter(n -> n.getId().equals(nodeId))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Node not found"));
  }

  // =========================
  // LINK MANAGEMENT
  // =========================

  public void connectNodes(UUID fromNodeId, UUID toNodeId, Position position) {
    Objects.requireNonNull(fromNodeId);
    Objects.requireNonNull(toNodeId);

    Node from = findNode(fromNodeId);
    Node to = findNode(toNodeId);

    if (from.equals(to)) {
      throw new IllegalStateException("Cannot link node to itself");
    }

    from.connectTo(to, position);
  }

  // =========================
  // PUBLISHING
  // =========================

  public void publish() {

    if (nodes.isEmpty()) {
      throw new IllegalStateException(
          "Project must contain at least one node"
      );
    }

    if (startingNodeId == null) {
      throw new IllegalStateException(
          "Project must have a starting node"
      );
    }

    this.published = true;
  }

  public void unpublish() {
    this.published = false;
  }

  // =========================
  // STARTING NODE
  // =========================

  public Node getStartingNode() {

    if (startingNodeId == null) {
      throw new IllegalStateException("Project has no starting node");
    }

    return findNode(startingNodeId);
  }

  public void setStartingNode(UUID nodeId) {
    if (hasNode(nodeId)) {
      throw new IllegalArgumentException(
          "Node does not belong to project"
      );
    }

    this.startingNodeId = nodeId;
  }

  public void update(
      String title,
      String description,
      UUID startingNodeId
  ) {

    this.title = title;
    this.description = description;

    if (startingNodeId != null) {

      if (hasNode(startingNodeId)) {
        throw new IllegalArgumentException(
            "Starting node does not belong to project"
        );
      }

      this.startingNodeId = startingNodeId;
    }
  }

  // =========================
  // HELPERS
  // =========================

  public boolean hasNode(UUID nodeId) {

    return nodes.stream()
        .noneMatch(node -> node.getId().equals(nodeId));
  }
}
