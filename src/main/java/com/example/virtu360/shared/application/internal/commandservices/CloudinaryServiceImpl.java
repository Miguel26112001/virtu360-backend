package com.example.virtu360.shared.application.internal.commandservices;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.virtu360.shared.domain.model.commands.DeleteImageCommand;
import com.example.virtu360.shared.domain.model.commands.UploadImageCommand;
import com.example.virtu360.shared.domain.model.dto.CloudinaryResponse;
import com.example.virtu360.shared.domain.services.CloudinaryService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.Optional;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

  private final Cloudinary cloudinary;

  public CloudinaryServiceImpl(Cloudinary cloudinary) {
    this.cloudinary = cloudinary;
  }

  @Override
  public Optional<CloudinaryResponse> handle(UploadImageCommand command) {

    byte[] compressed = compressImage(command.file());

    try {
      @SuppressWarnings("unchecked")
      Map<String, Object> result = cloudinary.uploader().upload(
          compressed,
          ObjectUtils.asMap(
              "folder", "virtu-pro/nodes",
              "overwrite", true,
              "resource_type", "image"
          )
      );

      var imageResponse = mapResponse(result);

      return Optional.of(imageResponse);

    } catch (Exception e) {
      throw new RuntimeException(
          "Error uploading image: " + e
      );
    }
  }

  @Override
  public void handle(DeleteImageCommand command) {
    try {

      cloudinary.uploader().destroy(command.publicId(),  ObjectUtils.asMap());

    } catch (Exception e) {
      throw new RuntimeException("Error deleting file from Cloudinary", e);
    }
  }

  private CloudinaryResponse mapResponse(Map<String, Object> result) {
    return new CloudinaryResponse(
        result.get("secure_url").toString(),
        result.get("public_id").toString()
    );
  }

  private byte[] compressImage(MultipartFile file) {
    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

      Thumbnails.of(file.getInputStream())
          .scale(1.0)
          .outputQuality(0.7)
          .outputFormat("jpg")
          .toOutputStream(outputStream);

      return outputStream.toByteArray();

    } catch (Exception e) {
      throw new RuntimeException("Error compressing image", e);
    }
  }
}
