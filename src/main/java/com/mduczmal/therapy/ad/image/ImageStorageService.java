package com.mduczmal.therapy.ad.image;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {

    void init();

    void store(MultipartFile file);

    Resource load(String filename);

    void deleteAll();
}
