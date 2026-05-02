package com.example.virtu360.shared.domain.services;


import com.example.virtu360.shared.domain.model.dto.CloudinaryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface CloudinaryContextFacade {

  Optional<CloudinaryResponse> uploadFile(MultipartFile file);

  void deleteFile(String publicId);
}
