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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Optional;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

  private static final int MAX_WIDTH = 4096;
  private static final int MAX_HEIGHT = 2048;
  private static final long MAX_FILE_SIZE = 8 * 1024 * 1024;

  private final Cloudinary cloudinary;

  public CloudinaryServiceImpl(Cloudinary cloudinary) {
    this.cloudinary = cloudinary;
  }

  @Override
  public Optional<CloudinaryResponse> handle(UploadImageCommand command) {

    MultipartFile file = command.file();

    validateFile(file);

    File compressedFile = null;

    try {

      compressedFile = compressImage(file);

      String hash = generateHash(compressedFile);

      @SuppressWarnings("unchecked")
      Map<String, Object> result = cloudinary.uploader().upload(
          compressedFile,
          ObjectUtils.asMap(
              "folder", "virtu-pro/nodes",
              "public_id", hash,
              "overwrite", false,
              "resource_type", "image"
          )
      );

      return Optional.of(mapResponse(result));

    } catch (Exception e) {

      throw new RuntimeException("Error uploading image", e);

    } finally {

      if (compressedFile != null && compressedFile.exists()) {
        compressedFile.delete();
      }
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

  private File compressImage(MultipartFile file) {

    try {

      File tempFile = Files.createTempFile(
          "virtu360-",
          ".jpg"
      ).toFile();

      Thumbnails.of(file.getInputStream())
          .size(MAX_WIDTH, MAX_HEIGHT)
          .outputQuality(0.8)
          .outputFormat("jpg")
          .toFile(tempFile);

      return tempFile;

    } catch (Exception e) {

      throw new RuntimeException(
          "Error compressing image",
          e
      );
    }
  }

  private String generateHash(File file) {

    try (
        InputStream inputStream = new FileInputStream(file)
    ) {

      MessageDigest digest = MessageDigest.getInstance("SHA-256");

      DigestInputStream digestStream =
          new DigestInputStream(inputStream, digest);

      byte[] buffer = new byte[8192];

      while (digestStream.read(buffer) != -1) {
        // stream hashing
      }

      byte[] hashBytes = digest.digest();

      StringBuilder hex = new StringBuilder();

      for (byte b : hashBytes) {
        hex.append(String.format("%02x", b));
      }

      return hex.toString();

    } catch (Exception e) {

      throw new RuntimeException(
          "Error generating hash",
          e
      );
    }
  }
}
