package com.example.virtu360.tour.infrastructure.persistence.jpa.repositories;

import com.example.virtu360.tour.domain.model.entities.Marker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarkerRepository extends JpaRepository<Marker, String> {

  List<Marker> findByNodeId(Long node_id);
}