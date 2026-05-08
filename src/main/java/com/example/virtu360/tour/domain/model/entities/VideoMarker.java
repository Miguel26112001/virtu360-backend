package com.example.virtu360.tour.domain.model.entities;

import com.example.virtu360.tour.domain.model.valueobjects.Position;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("VIDEO")
@Getter
@NoArgsConstructor
public class VideoMarker extends Marker {

  @Column(name = "video_url")
  private String videoUrl;

  @Column(name = "is_youtube")
  private boolean youtube;

  public VideoMarker(
      Position position,
      String title,
      String tooltip,
      String summary,
      String videoUrl,
      boolean youtube
  ) {

    super(position, title, tooltip, summary);

    this.videoUrl = videoUrl;
    this.youtube = youtube;
  }

  public static VideoMarker create(
      Position pos,
      String title,
      String tooltip,
      String summary,
      String url,
      boolean yt
  ) {

    return new VideoMarker(
        pos,
        title,
        tooltip,
        summary,
        url,
        yt
    );
  }

  public void update(
      Position position,
      String title,
      String tooltip,
      String summary,
      String videoUrl,
      boolean youtube
  ) {

    updateBase(
        position,
        title,
        tooltip,
        summary
    );

    this.videoUrl = videoUrl;
    this.youtube = youtube;
  }
}