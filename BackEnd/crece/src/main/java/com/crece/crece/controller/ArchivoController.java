package com.crece.crece.controller;

import com.crece.crece.model.Archivo;
import com.crece.crece.model.dto.ArchivoDTO;
import com.crece.crece.model.dto.GetEdificioListDto;
import com.crece.crece.service.ArchivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/file")
public class ArchivoController {


    @Autowired
    private ArchivoService service;

    @CrossOrigin(origins = "*")
    @PostMapping("/fileSystem/{edificioId}")
    public ResponseEntity<?> uploadImageToFIleSystem(@RequestParam("image")MultipartFile file, @PathVariable Long edificioId) throws IOException {
        String uploadImage = service.uploadImageToFileSystem(file, edificioId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/fileSystem/{fileName}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageData=service.downloadImageFromFileSystem(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/jpeg"))
                .body(imageData);

    }
    @GetMapping("/fileSystem/all")
    public ResponseEntity<?> getAllFiles() {
        List<ArchivoDTO> archivos = service.getAllArchivos();  // Necesitas implementar este método en ArchivoService
        return ResponseEntity.status(HttpStatus.OK)
                .body(archivos);
    }
}
