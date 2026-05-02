package com.example.virtu360.shared.interfaces.acl;

import com.example.virtu360.shared.domain.model.commands.DeleteImageCommand;
import com.example.virtu360.shared.domain.model.commands.UploadImageCommand;
import com.example.virtu360.shared.domain.model.dto.CloudinaryResponse;
import com.example.virtu360.shared.domain.services.CloudinaryContextFacade;
import com.example.virtu360.shared.domain.services.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class CloudinaryContextFacadeImpl implements CloudinaryContextFacade {

  private final CloudinaryService cloudinaryService;

  public CloudinaryContextFacadeImpl(CloudinaryService cloudinaryService) {
    this.cloudinaryService = cloudinaryService;
  }

  @Override
  public Optional<CloudinaryResponse> uploadFile(MultipartFile file) {

    var uploadImageCommand = new UploadImageCommand(file);

    return cloudinaryService.handle(uploadImageCommand);
  }

  @Override
  public void deleteFile(String publicId) {

    var deleteImageCommand = new DeleteImageCommand(publicId);
    cloudinaryService.handle(deleteImageCommand);
  }
}
