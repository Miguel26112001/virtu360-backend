package com.example.virtu360.tour.infrastructure.persistence.jpa.repositories;

import com.example.virtu360.tour.domain.model.entities.Marker;
import com.example.virtu360.tour.domain.model.valueobjects.MarkerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MarkerRepository
    extends JpaRepository<Marker, UUID> {

  // =========================
  // GENERIC
  // =========================

  List<Marker> findByNodeId(UUID nodeId);

  // =========================
  // FILTERED BY TYPE
  // =========================

  List<Marker> findByNodeIdAndType(
      UUID nodeId,
      MarkerType type
  );
}