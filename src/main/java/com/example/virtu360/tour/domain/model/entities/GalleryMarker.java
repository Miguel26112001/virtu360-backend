package com.example.virtu360.tour.domain.model.entities;

import com.example.virtu360.tour.domain.model.valueobjects.Position;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("GALLERY")
@Getter
@NoArgsConstructor
public class GalleryMarker extends Marker {

  @ElementCollection
  @CollectionTable(
      name = "marker_gallery_images",
      joinColumns = @JoinColumn(name = "marker_id")
  )
  @Column(name = "image_url")
  private List<String> imageUrls = new ArrayList<>();

  public GalleryMarker(
      Position position,
      String title,
      String tooltip,
      String summary,
      List<String> imageUrls
  ) {

    super(position, title, tooltip, summary);

    this.imageUrls = imageUrls;
  }

  public static GalleryMarker create(
      Position pos,
      String title,
      String tooltip,
      String summary,
      List<String> urls
  ) {

    return new GalleryMarker(
        pos,
        title,
        tooltip,
        summary,
        urls
    );
  }
}