package com.crece.crece.service;

import com.crece.crece.model.Archivo;
import com.crece.crece.model.Edificio;
import com.crece.crece.model.Novedades;
import com.crece.crece.model.dto.ArchivoDTO;
import com.crece.crece.model.dto.EdificioDTO;
import com.crece.crece.model.dto.GetEdificioListDto;
import com.crece.crece.model.dto.NovedadesDTO;
import com.crece.crece.repository.ArchivoRepository;
import com.crece.crece.repository.NovedadesRepository;
import org.modelmapper.ModelMapper;
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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NovedadesService {
    @Autowired
    private NovedadesRepository repository;
    @Autowired
    private ModelMapper modelMapper;

    public void eliminarNovedad(Long id) {
        repository.deleteById(id);
    }
    public void guardarNovedad(NovedadesDTO novedadesDTO){
        Novedades novedad = modelMapper.map(novedadesDTO, Novedades.class);
        repository.save(novedad);
    }

    public List<NovedadesDTO> obtenerNovedades (){
        List<Novedades> novedadesList = repository.findAll();
        return novedadesList.stream()
                .map(novedad -> modelMapper.map(novedad, NovedadesDTO.class))
                .collect(Collectors.toList());
    }

    public void editarNovedad(Long id, NovedadesDTO novedadesDTO) {
        Optional<Novedades> novedadOptional = repository.findById(id);

        if (novedadOptional.isPresent()) {
            Novedades novedadExistente = novedadOptional.get();
            modelMapper.map(novedadesDTO, novedadExistente);
            repository.save(novedadExistente);
        } else {
            // Manejar la situación en la que no se encuentra la novedad con el id proporcionado
            throw new NoSuchElementException("No se encontró la novedad con ID: " + id);
        }
    }

}
