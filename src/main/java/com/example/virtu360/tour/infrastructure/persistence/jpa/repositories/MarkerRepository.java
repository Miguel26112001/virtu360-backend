package com.example.virtu360.tour.infrastructure.persistence.jpa.repositories;

import com.example.virtu360.tour.domain.model.entities.GalleryMarker;
import com.example.virtu360.tour.domain.model.entities.InfoMarker;
import com.example.virtu360.tour.domain.model.entities.Marker;
import com.example.virtu360.tour.domain.model.entities.VideoMarker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MarkerRepository
    extends JpaRepository<Marker, UUID> {

  // =========================
  // GENERIC
  // =========================

  List<Marker> findByNodeId(UUID nodeId);

  // =========================
  // INFO
  // =========================

  @Query("""
      SELECT m
      FROM InfoMarker m
      WHERE m.node.id = :nodeId
  """)
  List<InfoMarker> findInfoMarkersByNodeId(UUID nodeId);

  // =========================
  // VIDEO
  // =========================

  @Query("""
      SELECT m
      FROM VideoMarker m
      WHERE m.node.id = :nodeId
  """)
  List<VideoMarker> findVideoMarkersByNodeId(UUID nodeId);

  // =========================
  // GALLERY
  // =========================

  @Query("""
      SELECT m
      FROM GalleryMarker m
      WHERE m.node.id = :nodeId
  """)
  List<GalleryMarker> findGalleryMarkersByNodeId(UUID nodeId);
}