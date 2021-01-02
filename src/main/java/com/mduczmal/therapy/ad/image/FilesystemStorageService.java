package com.mduczmal.therapy.ad.image;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class FilesystemStorageService implements ImageStorageService, CommandLineRunner {
    private final String images = System.getenv( "FILES") + "/images";
    private final ImageRepository imageRepository;
    private final Base64.Encoder base64encoder = Base64.getEncoder();

    public FilesystemStorageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }
    @Override
    public void init() {
        try {
            Path path = Paths.get(images);
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
    public byte[] load(UUID id) throws IOException {
        Image image = imageRepository.findById(id).orElseThrow(() -> new FileNotFoundException("Image " + id + " not found"));
        Path path = Path.of(images + "/" + image.getFilename());
        return base64encoder.encode(Files.readAllBytes(path));
    }

    @Override
    public Path getPath(String filename) {
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
