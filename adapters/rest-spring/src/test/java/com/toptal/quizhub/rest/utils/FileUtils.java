package com.toptal.quizhub.rest.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils {

    public static String readFromFileToString(String filePath) {

        try {
            final File resource = new ClassPathResource(filePath).getFile();
            final byte[] byteArray = Files.readAllBytes(resource.toPath());
            return new String(byteArray, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
