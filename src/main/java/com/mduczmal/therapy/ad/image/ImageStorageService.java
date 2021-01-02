package com.mduczmal.therapy.ad.image;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ImageStorageService {

    void init();

    Image store(MultipartFile file);

    byte[] load(UUID id);

    void deleteAll();
}
