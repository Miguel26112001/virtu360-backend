package com.example.virtu360.shared.application.internal.commandservices;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.virtu360.shared.domain.model.commands.DeleteImageCommand;
import com.example.virtu360.shared.domain.model.commands.UploadImageCommand;
import com.example.virtu360.shared.domain.model.dto.CloudinaryResponse;
import com.example.virtu360.shared.domain.services.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;
import java.util.Optional;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

  private static final long MAX_FILE_SIZE = 15 * 1024 * 1024;

  private final Cloudinary cloudinary;

  public CloudinaryServiceImpl(Cloudinary cloudinary) {
    this.cloudinary = cloudinary;
  }

  @Override
  public Optional<CloudinaryResponse> handle(
      UploadImageCommand command
  ) {

    MultipartFile file = command.file();

    validateFile(file);

    try {

      @SuppressWarnings("unchecked")
      Map<String, Object> result =
          cloudinary.uploader().upload(
              file.getBytes(),
              ObjectUtils.asMap(
                  "folder", "virtu-pro/nodes",
                  "resource_type", "image",
                  "overwrite", false
              )
          );

      return Optional.of(
          mapResponse(result)
      );

    } catch (Exception e) {

      e.printStackTrace();

      throw new RuntimeException(
          "Error uploading image: " + e.getMessage(),
          e
      );
    }
  }

  @Override
  public void handle(DeleteImageCommand command) {

    try {

      cloudinary.uploader().destroy(
          command.publicId(),
          ObjectUtils.emptyMap()
      );

    } catch (Exception e) {

      throw new RuntimeException(
          "Error deleting image from Cloudinary",
          e
      );
    }
  }

  private CloudinaryResponse mapResponse(
      Map<String, Object> result
  ) {

    return new CloudinaryResponse(
        result.get("secure_url").toString(),
        result.get("public_id").toString()
    );
  }

  private void validateFile(MultipartFile file) {

    if (file.isEmpty()) {
      throw new RuntimeException("Image is empty");
    }

    if (file.getSize() > MAX_FILE_SIZE) {
      throw new RuntimeException("Image exceeds maximum allowed size");
    }

    String contentType = file.getContentType();

    if (contentType == null ||
        !contentType.startsWith("image/")) {

      throw new RuntimeException("Invalid image type");
    }
  }
}
