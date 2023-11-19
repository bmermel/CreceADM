package com.crece.crece.service;

import com.crece.crece.model.Archivo;
import com.crece.crece.repository.ArchivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
public class ArchivoService{

  //  @Autowired
  //  private StorageRepository repository;

    @Autowired
    private ArchivoRepository fileDataRepository;

    private final String FOLDER_PATH="C:\\Users\\Il Gordo VC\\Desktop\\Proyecto Administracion Crece\\CreceADM-main\\BackEnd\\uploadedFiles\\";


    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        String filePath=FOLDER_PATH+file.getOriginalFilename();

        Archivo fileData=fileDataRepository.save(Archivo.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath).build());

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