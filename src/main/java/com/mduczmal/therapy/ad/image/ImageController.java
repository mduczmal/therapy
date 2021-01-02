package com.mduczmal.therapy.ad.image;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
public class ImageController {

    private final ImageStorageService imageStorageService;

    ImageController(ImageStorageService imageStorageService) {
        this.imageStorageService = imageStorageService;
    }

    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String, UUID> upload(@RequestParam("image") MultipartFile file) {
        Image image = imageStorageService.store(file);
        return Collections.singletonMap("id", image.getId());
    }

    @GetMapping(value = "/v2/image", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String, byte[]> getImage(@RequestParam UUID id) {
            return Map.of("image", imageStorageService.load(id));
    }
}