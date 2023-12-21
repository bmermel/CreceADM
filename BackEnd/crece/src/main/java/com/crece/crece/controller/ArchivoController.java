package com.crece.crece.controller;

import com.crece.crece.model.Archivo;
import com.crece.crece.model.MailStructure;
import com.crece.crece.model.dto.ArchivoDTO;
import com.crece.crece.model.dto.GetEdificioListDto;
import com.crece.crece.service.ArchivoService;
import com.crece.crece.service.MailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/file")
@CrossOrigin(origins = "*")
public class ArchivoController {


    @Autowired
    private ArchivoService service;
    @Autowired
    private MailService mailService;


    @CrossOrigin(origins = "*")
    @PostMapping("/fileSystem/{edificioId}/{categoria}")
    public ResponseEntity<?> uploadImageToFIleSystem(@RequestParam("image") MultipartFile file,
                                                     @PathVariable Long edificioId,
                                                     @PathVariable String categoria) throws IOException {
        String uploadedFile = service.uploadImageToFileSystem(file, edificioId, categoria);

        // Crear un objeto MailStructure
        MailStructure mailStructure = new MailStructure();
        mailStructure.setSubject(categoria + ": Nueva Comunicación");
        mailStructure.setMessage("Mensaje del correo");

        // Supongamos también que tienes una lista de destinatarios (mails)
        List<String> mails = Arrays.asList("bmermel@gmail.com", "penagonza@hotmail.com","leonardo.spadavecchia@hotmail.com");

        // Ahora puedes llamar al método sendMailAttach de mailService
        try {
            mailService.sendMailAttach(mails, mailStructure, uploadedFile);
            System.out.println("Correo enviado después de cargar el archivo.");
        } catch (MessagingException e) {
            e.printStackTrace();
            // Manejar la excepción si es necesario
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al enviar el correo después de cargar el archivo.");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadedFile);
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
