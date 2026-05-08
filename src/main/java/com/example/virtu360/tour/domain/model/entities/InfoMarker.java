package com.example.virtu360.tour.domain.model.entities;

import com.example.virtu360.tour.domain.model.valueobjects.Position;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("INFO")
@Getter
@NoArgsConstructor
public class InfoMarker extends Marker {

  @Column(columnDefinition = "TEXT")
  private String content;

  @Column(columnDefinition = "TEXT")
  private String description;

  public InfoMarker(
      Position position,
      String title,
      String tooltip,
      String summary,
      String content,
      String description
  ) {

    super(position, title, tooltip, summary);

    this.content = content;
    this.description = description;
  }

  public static InfoMarker create(
      Position pos,
      String title,
      String tooltip,
      String summary,
      String content,
      String description
  ) {
    return new InfoMarker(
        pos,
        title,
        tooltip,
        summary,
        content,
        description
    );
  }
}
