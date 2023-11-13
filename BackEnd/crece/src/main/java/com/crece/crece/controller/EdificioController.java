package com.crece.crece.controller;

import com.crece.crece.model.dto.EdificioDTO;
import com.crece.crece.service.EdificioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/edificios")
public class EdificioController {
    @Autowired
    EdificioService edificioService;

    @PostMapping()
    public ResponseEntity<?> crearEdificio(@RequestBody EdificioDTO edificioDTO){
        edificioService.crearEdificio(edificioDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public EdificioDTO getArchivo(@PathVariable Long id){
        return edificioService.leerEdificio(id);
    }

    @PutMapping
    public ResponseEntity<?> modificarEdificio(@RequestBody EdificioDTO edificioDTO){
        edificioService.modificarEdificio(edificioDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEdificio(@PathVariable Long id){
        ResponseEntity<String> response = null;
        edificioService.eliminarEdificio(id);
        response = ResponseEntity.status(HttpStatus.OK).body("Eliminado");
        return response;
    }
        /*@GetMapping("/listadoOdontologos")
        public Collection<ArchivoDTO> getTodos(){
            return archivoService.getTodos();
        }*/
}
