package com.example.virtu360.shared.domain.services;

import com.example.virtu360.shared.domain.model.commands.DeleteImageCommand;
import com.example.virtu360.shared.domain.model.commands.UploadImageCommand;
import com.example.virtu360.shared.domain.model.dto.CloudinaryResponse;

import java.util.Optional;

public interface CloudinaryService {

  Optional<CloudinaryResponse> handle(UploadImageCommand command);

  void handle(DeleteImageCommand command);
}
