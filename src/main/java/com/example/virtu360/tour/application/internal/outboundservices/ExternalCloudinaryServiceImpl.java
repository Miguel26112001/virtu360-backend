package com.example.virtu360.tour.application.internal.outboundservices;

import com.example.virtu360.shared.domain.model.dto.CloudinaryResponse;
import com.example.virtu360.shared.domain.services.CloudinaryContextFacade;
import com.example.virtu360.tour.domain.services.ExternalCloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class ExternalCloudinaryServiceImpl implements ExternalCloudinaryService {

  private final CloudinaryContextFacade cloudinaryContextFacade;

  public ExternalCloudinaryServiceImpl(CloudinaryContextFacade cloudinaryContextFacade) {
    this.cloudinaryContextFacade = cloudinaryContextFacade;
  }

  @Override
  public Optional<CloudinaryResponse> uploadImage(MultipartFile file) {

    if (file == null || file.isEmpty()) {
      return Optional.empty();
    }

    try {
      return cloudinaryContextFacade.uploadFile(file);
    } catch (Exception e) {
      System.err.println("Error uploading image through ACL: " + e.getMessage());
      return Optional.empty();
    }
  }

  @Override
  public void deleteImage(String publicId) {
    try {
      cloudinaryContextFacade.deleteFile(publicId);
    } catch (Exception e) {
      System.err.println("Could not delete image: " + e.getMessage());
    }
  }
}
