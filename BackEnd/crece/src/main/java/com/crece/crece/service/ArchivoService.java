package com.crece.crece.service;

import com.crece.crece.model.Archivo;
import com.crece.crece.model.dto.ArchivoDTO;
import com.crece.crece.repository.IArchivosRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArchivoService{
    @Autowired
    private IArchivosRepository archivosRepository;

    @Autowired
    ObjectMapper mapper;

    private void guardarArchivo(ArchivoDTO archivoDTO){
        Archivo archivo = mapper.convertValue(archivoDTO, Archivo.class);
        archivosRepository.save(archivo);
    }

    public void crearArchivo(ArchivoDTO odontologoDTO) {
        guardarArchivo(odontologoDTO);
    }

    public ArchivoDTO leerArchivo(Long id) {
        Optional<Archivo> archivo = archivosRepository.findById(id);
        ArchivoDTO archivoDTO = null;
        if(archivo.isPresent())
            archivoDTO = mapper.convertValue(archivo, ArchivoDTO.class);
        return archivoDTO;
    }

    public void modificarArchivo(ArchivoDTO archivoDTO) {
        guardarArchivo(archivoDTO);
    }

    public void eliminarArchivo(Long id) {
        archivosRepository.deleteById(id);
    }
}
