package com.example.virtu360.tour.infrastructure.persistence.jpa.repositories;

import com.example.virtu360.tour.domain.model.entities.Node;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NodeRepository
    extends JpaRepository<Node, UUID> {

  List<Node> findByProjectId(UUID projectId);
}