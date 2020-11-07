package com.mduczmal.therapy.ad.image;

import com.mduczmal.therapy.therapist.Therapist;
import com.mduczmal.therapy.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

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
    public void store(MultipartFile file) {
        if (file.isEmpty()) {
            System.out.println("Empty file");
        }
        try {
            System.out.println("Processing");
            byte[] bytes = file.getBytes();
            Image image = new Image();
            Path path = Path.of(images + "/" + image.getFilename());
            Files.write(path, bytes);
            System.out.println(path + " written");
            imageRepository.save(image);
            System.out.println(image.getFilename() + " saved");
        } catch (IOException e) {
            System.out.println("Failed");
            e.printStackTrace();
        }

    }

    @Override
    public Resource load(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void run(String... args) throws Exception {
        init();
    }
}
