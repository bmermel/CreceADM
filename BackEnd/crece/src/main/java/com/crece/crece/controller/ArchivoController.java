package com.crece.crece.controller;

import com.crece.crece.model.dto.ArchivoDTO;
import com.crece.crece.service.ArchivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/archivos")
public class ArchivoController {


        @Autowired
        ArchivoService archivoService;

        @PostMapping()
        public ResponseEntity<?> crearArchivo(@RequestBody ArchivoDTO archivoDTO){
            archivoService.crearArchivo(archivoDTO);
            return ResponseEntity.ok(HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ArchivoDTO getArchivo(@PathVariable Long id){
            return archivoService.leerArchivo(id);
        }

        @PutMapping
        public ResponseEntity<?> modificarOdontologo(@RequestBody ArchivoDTO archivoDTO){
            archivoService.modificarArchivo(archivoDTO);
            return ResponseEntity.ok(HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<?> eliminarArchivo(@PathVariable Long id){
            ResponseEntity<String> response = null;
            archivoService.eliminarArchivo(id);
            response = ResponseEntity.status(HttpStatus.OK).body("Eliminado");
            return response;
        }


        /*@GetMapping("/listadoOdontologos")
        public Collection<ArchivoDTO> getTodos(){
            return archivoService.getTodos();
        }*/
    }
