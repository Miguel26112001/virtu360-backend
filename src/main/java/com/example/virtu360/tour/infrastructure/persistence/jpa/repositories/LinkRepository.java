package com.example.virtu360.tour.infrastructure.persistence.jpa.repositories;

import com.example.virtu360.tour.domain.model.entities.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, String> {

  List<Link> findByFromNodeId(Long fromNode_id);
}