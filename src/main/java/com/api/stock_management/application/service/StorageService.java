package com.api.stock_management.application.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    public String store(MultipartFile file, Path uploadPath) {
        if (file.isEmpty()) {
            throw new RuntimeException("Falha ao armazenar arquivo vazio.");
        }

        // Limpa o nome do arquivo e gera um nome único
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = "";
        try {
            // Pega a extensão (ex: .png, .jpg)
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        } catch(Exception e) {
            fileExtension = "";
        }
        String fileName = UUID.randomUUID().toString() + fileExtension;

        try (InputStream inputStream = file.getInputStream()) {
            // Garante que o diretório de destino existe (ex: ./uploads/produtos)
            Files.createDirectories(uploadPath);

            Path targetLocation = uploadPath.resolve(fileName);
            // Copia o arquivo para o disco
            Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName; // Retorna o novo nome
        } catch (IOException e) {
            throw new RuntimeException("Falha ao armazenar o arquivo " + fileName, e);
        }
    }
}