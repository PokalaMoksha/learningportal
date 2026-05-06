package com.learning.certificate.service;

import com.learning.common.exception.BadApiRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl
       implements FileService {

    @Override
    public String uploadFile(
                  MultipartFile file,
                  String path)
                  throws IOException {

        String originalFileName =
               file.getOriginalFilename();

        String extension =
               originalFileName.substring(
               originalFileName.lastIndexOf("."));

        String fileName =
               UUID.randomUUID().toString()
               + extension;

        String fullPath =
               path + File.separator + fileName;

        if (extension.equalsIgnoreCase(".pdf") ||
            extension.equalsIgnoreCase(".jpg") ||
            extension.equalsIgnoreCase(".jpeg") ||
            extension.equalsIgnoreCase(".png")) {

            File folder = new File(path);
            if (!folder.exists())
                folder.mkdirs();

            Files.copy(
                file.getInputStream(),
                Paths.get(fullPath));

            log.info("File uploaded: {}",
                     fileName);

            return fileName;

        } else {
            throw new BadApiRequest(
                "Only PDF, JPG, JPEG, PNG " +
                "allowed!");
        }
    }

    @Override
    public InputStream getResource(
                       String path,
                       String name)
                       throws IOException {

        String fullPath =
               path + File.separator + name;

        log.info("Fetching file: {}", name);

        return new FileInputStream(fullPath);
    }
}
