package com.example.virtu360.tour.infrastructure.persistence.jpa.repositories;

import com.example.virtu360.tour.domain.model.aggregates.Node;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeRepository extends JpaRepository<Node, Long> {
}