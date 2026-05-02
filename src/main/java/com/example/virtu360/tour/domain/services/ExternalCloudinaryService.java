package com.example.virtu360.tour.domain.services;

import com.example.virtu360.shared.domain.model.dto.CloudinaryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ExternalCloudinaryService {

  Optional<CloudinaryResponse> uploadImage(MultipartFile file);

  void deleteImage(String publicId);
}
