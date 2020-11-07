package com.mduczmal.therapy.ad.image;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

@RestController
public class ImageController {

    private final ImageStorageService imageStorageService;

    ImageController(ImageStorageService imageStorageService) {
        this.imageStorageService = imageStorageService;
    }

    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String, String> upload(@RequestParam("image") MultipartFile file) {
        Image image = imageStorageService.store(file);
        Path path = imageStorageService.load(image.getFilename());
        return Collections.singletonMap("path", path.toString());
    }
}