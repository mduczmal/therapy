package com.mduczmal.therapy.ad.image;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface ImageStorageService {

    void init();

    Image store(MultipartFile file);

    Path load(String filename);

    void deleteAll();
}
