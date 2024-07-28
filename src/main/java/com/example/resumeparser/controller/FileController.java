package com.example.resumeparser.controller;

import com.example.resumeparser.service.FileService;
import com.example.resumeparser.utils.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

/***
 * @title FileController
 * @description
 * @author haoyu99
 * @version 1.0.0
 * @create 2024/7/4 1:20
 **/
@RestController
@RequestMapping("/api/files")
public class FileController {
    @Autowired
    private FileService fileService;
    @Value("${file.path}")
    private String FILE_DIRECTORY;
    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    @GetMapping("/{fileName}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(FILE_DIRECTORY).resolve(fileName).normalize();
            Resource resource = new FileSystemResource(filePath.toFile());

            if (resource.exists() && resource.isReadable()) {
                String encodedFileName = URLEncoder.encode(resource.getFilename(), StandardCharsets.UTF_8.toString());
                String contentDispositionValue = "inline; filename*=UTF-8''" + encodedFileName;

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, contentDispositionValue);
                headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

                log.info("File found: {}", filePath);
                log.info("Content-Disposition: {}", contentDispositionValue);
                log.info("Content-Type: application/pdf");

                // Check headers
                headers.forEach((key, value) -> System.out.println(key + ": " + String.join(", ", value)));

                return ResponseEntity.ok()
                        .headers(headers)
                        .body(resource);
            } else {
                System.out.println("File not found or not readable: " + filePath);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/upload")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Response<String>> uploadFiles(@RequestParam("files") MultipartFile[] files) throws Exception {
        StringBuilder message = new StringBuilder();
        for (MultipartFile file : files) {
            System.out.println(FILE_DIRECTORY);

            // Construct the file path
            Path filePath = Paths.get(FILE_DIRECTORY).resolve(file.getOriginalFilename()).normalize();
            File dest = filePath.toFile();

            // Save the file to the specified directory
            file.transferTo(dest);
            // 先用PDF解析出Str
            String resumeStr = fileService.getResumeStr(dest);
            // chat with bot get Json
            String rawJson = fileService.chatWithBot(resumeStr);
            String addResult = fileService.uploadFeiShuAndAddRecord(dest, rawJson);
            message.append("Successfully uploaded: ").append(file.getOriginalFilename()).append("\n");
            log.info("Successfully uploaded: {}", file.getOriginalFilename());
        }

        return ResponseEntity.ok(Response.ok(message.toString()));
    }
}
