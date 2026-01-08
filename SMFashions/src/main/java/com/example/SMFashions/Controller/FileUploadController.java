package com.example.SMFashions.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileUploadController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    // =============================
    // UPLOAD IMAGES
    // =============================
    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadImages(
            @RequestParam("files") MultipartFile[] files
    ) throws IOException {

        List<String> urls = new ArrayList<>();

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue;
            }

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir, filename);

            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            // Public URL
            String fileUrl = "http://localhost:8080/files/" + filename;
            urls.add(fileUrl);
        }

        return ResponseEntity.ok(urls);
    }

    // =============================
    // FETCH IMAGE
    // =============================
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getFile(
            @PathVariable String filename
    ) throws IOException {

        Path path = Paths.get(uploadDir, filename);
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }
}
