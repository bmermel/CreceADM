package com.crece.crece.service;

import com.crece.crece.model.Archivo;
import com.crece.crece.model.Edificio;
import com.crece.crece.model.dto.EdificioDTO;
import com.crece.crece.repository.ArchivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ArchivoService{

  //  @Autowired
  //  private StorageRepository repository;

    @Autowired
    private ArchivoRepository fileDataRepository;
    @Autowired
    private EdificioService edificioService;
    @Value("file:${user.dir}/uploadedFiles/")  // user.dir representa el directorio de trabajo actual del proyecto
    private Resource uploadDirectory;
    public String uploadImageToFileSystem(MultipartFile file, Long edificioId) throws IOException {
        String filePath=uploadDirectory.getURI().getPath() + file.getOriginalFilename();
        LocalDateTime today = LocalDateTime.now();
        Edificio edificio = edificioService.leerEdificio(edificioId).orElseThrow(( )->new RuntimeException("No existe el edificio"));

        Archivo fileData=fileDataRepository.save(Archivo.builder()
                .name(StringUtils.replace(today.toString(), ":","-") + "-"+ file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath)
                .edificio(edificio)
                .build());

        file.transferTo(new File(filePath));

        if (fileData != null) {
            return "file uploaded successfully : " + filePath;
        }
        return null;
    }


    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<Archivo> fileData = fileDataRepository.findByName(fileName);

        if (fileData.isPresent()) {
            String filePath = fileData.get().getFilePath();
            return Files.readAllBytes(new File(filePath).toPath());
        } else {
            // Manejar el caso cuando el archivo no se encuentra
            // Puedes lanzar una excepción, loggear un mensaje de error, etc.
            throw new FileNotFoundException("Archivo no encontrado: " + fileName);
        }
    }

    /*public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<Archivo> fileData = ArchivoRepository.findByName(fileName);
        String filePath=fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }*/



}