package com.api.stock_management.application.service; // Ajuste o pacote se necessário

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class StorageService {

    /**
     * Armazena o arquivo fornecido no caminho especificado.
     * * @param file O arquivo a ser armazenado.
     * @param rootPath O caminho raiz onde o arquivo deve ser salvo.
     * @return O nome do arquivo gerado (incluindo a extensão).
     */
    public String store(MultipartFile file, Path rootPath) {
        // Gera um nome único para o arquivo
        String originalFilename = file.getOriginalFilename();
        String extension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        // Nome único usando UUID e a extensão original
        String fileName = UUID.randomUUID().toString() + extension;

        try {
            // Garante que o diretório de destino exista
            if (!Files.exists(rootPath)) {
                Files.createDirectories(rootPath);
            }

            // Resolve o caminho completo do arquivo
            Path destinationFile = rootPath.resolve(fileName);

            // Copia o conteúdo do arquivo para o destino
            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

            return fileName;

        } catch (IOException e) {
            // Lança exceção se houver problema ao salvar
            throw new RuntimeException("Falha ao armazenar o arquivo " + fileName, e);
        }
    }
}