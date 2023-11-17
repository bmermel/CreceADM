package com.crece.crece.service;

import com.crece.crece.model.Edificio;

import com.crece.crece.model.TipoUsuario;
import com.crece.crece.model.dto.EdificioDTO;
import com.crece.crece.model.dto.TipoUsuarioDto;
import com.crece.crece.repository.IEdificioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EdificioService {
    @Autowired
    private IEdificioRepository edificioRepository;

    @Autowired
    ObjectMapper mapper;

    private void guardarEdificio(EdificioDTO edificioDTO){
        Edificio edificio = mapper.convertValue(edificioDTO, Edificio.class);
        edificioRepository.save(edificio);
    }

    public void crearEdificio(EdificioDTO edificioDTO) {
        guardarEdificio(edificioDTO);
    }

    public EdificioDTO leerEdificio(Long id) {
        Optional<Edificio> edificio = edificioRepository.findById(id);
        EdificioDTO edificioDTO = null;
        if(edificio.isPresent())
            edificioDTO = mapper.convertValue(edificio, EdificioDTO.class);
        return edificioDTO;
    }

    public void modificarEdificio(EdificioDTO edificioDTO) {
        guardarEdificio(edificioDTO);
    }

    public void eliminarEdificio(Long id) {
        edificioRepository.deleteById(id);
}
    public Edificio convertirDtoAClase(EdificioDTO edificioDTO){
        return mapper.convertValue(edificioDTO, Edificio.class);
    }
}
