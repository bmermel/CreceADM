package com.crece.crece.controller;

import com.crece.crece.model.Archivo;
import com.crece.crece.model.dto.ArchivoDTO;
import com.crece.crece.model.dto.GetEdificioListDto;
import com.crece.crece.service.ArchivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/file")
@CrossOrigin(origins = "*")
public class ArchivoController {


    @Autowired
    private ArchivoService service;

    @CrossOrigin(origins = "*")
    @PostMapping("/fileSystem/{edificioId}")
    public ResponseEntity<?> uploadImageToFIleSystem(@RequestParam("image")MultipartFile file, @PathVariable Long edificioId, @PathVariable String categoria) throws IOException {
        String uploadImage = service.uploadImageToFileSystem(file, edificioId, categoria);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/fileSystem/uploadedFiles/{fileName}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) {
        try {
            byte[] imageData = service.downloadImageFromFileSystem(fileName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", fileName);
            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Archivo no encontrado: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la solicitud");
        }
    }

    @GetMapping("/fileSystem/all")
    public ResponseEntity<?> getAllFiles() {
        List<ArchivoDTO> archivos = service.getAllArchivos();  // Necesitas implementar este método en ArchivoService
        return ResponseEntity.status(HttpStatus.OK)
                .body(archivos);
    }

    @DeleteMapping("/fileSystem/delete/{id}")
    public ResponseEntity<?> borrarArchivo(@PathVariable Long id){
        service.borrarArchivo(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Archivo borrado");
    }

}
