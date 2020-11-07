package com.mduczmal.therapy.ad.image;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FilesystemStorageService implements ImageStorageService, CommandLineRunner {
    private String images;
    private final ImageRepository imageRepository;

    public FilesystemStorageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }
    @Override
    public void init() {
        String dir = System.getenv( "FILES");
        images = dir + "/images";
        Path path = Paths.get(images);
        try {
            if (!Files.exists(path)) Files.createDirectory(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Image store(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        try {
            byte[] bytes = file.getBytes();
            Image image = new Image();
            Path path = Path.of(images + "/" + image.getFilename());
            Files.write(path, bytes);
            imageRepository.save(image);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Path load(String filename) {
        return Path.of(images + "/" + filename);
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void run(String... args) throws Exception {
        init();
    }
}
