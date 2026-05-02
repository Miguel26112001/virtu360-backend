package com.example.virtu360.shared.domain.model.aggregates;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Base aggregate root class that automatically manages creation and update timestamps.
 *
 * @param <T> the type of the aggregate extending this class
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditableAbstractAggregateRoot<T extends AbstractAggregateRoot<T>>
    extends AbstractAggregateRoot<T> {

  @Id
  @Getter
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Getter
  @Column(nullable = false, updatable = false)
  private OffsetDateTime createdAt;

  @Getter
  @Column(nullable = false)
  private OffsetDateTime updatedAt;

  /**
   * Sets creation and update timestamps before persisting.
   */
  @PrePersist
  protected void onCreate() {
    OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
    this.createdAt = now;
    this.updatedAt = now;
  }

  /**
   * Updates the timestamp before updating the entity.
   */
  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = OffsetDateTime.now(ZoneOffset.UTC);
  }
}