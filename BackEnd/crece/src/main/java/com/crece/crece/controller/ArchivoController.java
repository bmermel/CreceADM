package com.crece.crece.controller;

import com.crece.crece.model.Archivo;
import com.crece.crece.model.MailStructure;
import com.crece.crece.model.Usuario;
import com.crece.crece.model.dto.*;
import com.crece.crece.service.ArchivoService;
import com.crece.crece.service.EdificioService;
import com.crece.crece.service.MailService;
import com.crece.crece.service.UsuarioService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/file")

@CrossOrigin(origins = "*")
public class ArchivoController {


    @Autowired
    private ArchivoService service;

    @Autowired
    private MailService mailService;
    @Autowired
    private UsuarioService usuarioService;

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<?> uploadImageToFIleSystem(@RequestBody NuevoArchivoDTO nuevoArchivoDTO) throws IOException {


        service.uploadImageToFileSystem(nuevoArchivoDTO);

        // Crear un objeto MailStructure
        MailStructure mailStructure = new MailStructure();
        mailStructure.setSubject(nuevoArchivoDTO.getCategoria() + ": Nueva Comunicación");
        mailStructure.setMessage("Mensaje del correo");

        // Supongamos también que tienes una lista de destinatarios (mails)
        List<String> mails = usuarioService.getEmailsPorEdificioSinTipo(nuevoArchivoDTO.getEdificioId());
        System.out.println(mails);
        // Ahora puedes llamar al método sendMailAttach de mailService de forma asíncrona
        CompletableFuture.runAsync(() -> sendMailAsync(mails, mailStructure, nuevoArchivoDTO.getFilePath()));

        return ResponseEntity.status(HttpStatus.OK)
                .body(nuevoArchivoDTO);
    }

    @Async
    private CompletableFuture<Void> sendMailAsync(List<String> mails, MailStructure mailStructure, String fileURL) {
        try {
            mailService.sendMailAttach(mails, mailStructure, fileURL);
            System.out.println("Correo enviado después de cargar el archivo.");
        } catch (MessagingException | UnsupportedEncodingException | MalformedURLException e) {
            e.printStackTrace();
            // Manejar la excepción si es necesario
        }
        return null;
    }
/*    @GetMapping("/fileSystem/uploadedFiles/{id}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable Long id) {
        try {
            byte[] imageData = service.downloadImageFromFileSystem(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", id.toString());
            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Archivo con el siguiente ID no encontrado: " + id);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la solicitud");
        }
    }*/
/*    @GetMapping("/fileSystem/uploadedFiles/{id}")
    public String getNombrePorID(@PathVariable Long id){
        return service.obtenerNombrePorId(id);
    }*/

    @GetMapping("/fileSystem/all")
    public ResponseEntity<?> getAllFiles() {
        List<ArchivoYEdificioDTO> archivos = service.getAllArchivos();  // Necesitas implementar este método en ArchivoService
        return ResponseEntity.status(HttpStatus.OK)
                .body(archivos);
    }

    @DeleteMapping("/fileSystem/delete/{id}")
    public ResponseEntity<?> borrarArchivo(@PathVariable Long id){
        service.borrarArchivo(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Archivo borrado");
    }

   /* @GetMapping("/fileSystem/archivosPorEdificio/{edificioId}")
    public ResponseEntity<?> getArchivosPorEdificio(@PathVariable Long edificioId) {
        List<ArchivoDTO> archivos = service.getArchivosPorEdificio(edificioId);
        return ResponseEntity.status(HttpStatus.OK).body(archivos);
    }

    @GetMapping("/fileSystem/archivosPorCategoria/{categoria}")
    public ResponseEntity<?> getArchivosPorCategoria(@PathVariable String categoria) {
        List<ArchivoDTO> archivos = service.getArchivosPorCategoria(categoria);
        return ResponseEntity.status(HttpStatus.OK).body(archivos);
    }

    @GetMapping("/fileSystem/archivosPorNombre/{nombre}")
    public ResponseEntity<?> getArchivosPorNombre(@PathVariable String nombre) {
        List<ArchivoDTO> archivos = service.getArchivosPorNombre(nombre);
        return ResponseEntity.status(HttpStatus.OK).body(archivos);
    }

    @GetMapping("/fileSystem/archivosOrdenadosPorFecha")
    public ResponseEntity<?> getArchivosOrdenadosPorFecha() {
        List<ArchivoDTO> archivos = service.getArchivosOrdenadosPorFecha();
        return ResponseEntity.status(HttpStatus.OK).body(archivos);
    }*/

}
