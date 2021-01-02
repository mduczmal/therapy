package com.mduczmal.therapy.ad.image;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

public interface ImageStorageService {

    void init();

    Image store(MultipartFile file);

    byte[] load(UUID id) throws IOException;

    Path getPath(String filename);

    void deleteAll();
}
