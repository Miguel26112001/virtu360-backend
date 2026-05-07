package com.example.virtu360.tour.infrastructure.persistence.jpa.repositories;

import com.example.virtu360.tour.domain.model.aggregates.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

  List<Project> findByOwnerId(String ownerId);

  List<Project> findByPublishedTrue();

  List<Project> findByOwnerIdAndPublishedTrue(String ownerId);
}