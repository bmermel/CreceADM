package com.crece.crece.service;

import com.crece.crece.model.Archivo;
import com.crece.crece.model.Edificio;
import com.crece.crece.model.MailStructure;
import com.crece.crece.model.Novedades;
import com.crece.crece.model.dto.*;
import com.crece.crece.repository.ArchivoRepository;
import com.crece.crece.repository.NovedadesRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class NovedadesService {
    @Autowired
    private NovedadesRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MailService mailService;
    @Autowired
    private UsuarioService usuarioService;

    public void eliminarNovedad(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("La novedad con ID " + id + " no existe");
        }    }
    public void guardarNovedad(NovedadesSinIDDTO novedadesDTO) throws MessagingException, UnsupportedEncodingException {

        Optional<Novedades> optionalNovedadExistente = repository.findByEdificioId(novedadesDTO.getEdificioId());
        if (optionalNovedadExistente.isPresent()) {
            Novedades novedadExistente = optionalNovedadExistente.get();
            repository.delete(novedadExistente);
        }
        Novedades novedad = modelMapper.map(novedadesDTO, Novedades.class);

        repository.save(novedad);
        MailStructure mailStructure = new MailStructure();
        mailStructure.setSubject("Nuevas notificaciones - Administración");
        mailStructure.setMessage("Mensaje del correo");
        List<String> mails = usuarioService.getEmailsPorEdificioSinTipo(novedadesDTO.getEdificioId());

        if (novedad.getSendEmail()){

            CompletableFuture.runAsync(() -> {
                try {
                    mailService.sendMailWithoutAttach(mails, mailStructure, novedad);
                    System.out.println("Correo enviado por novedades.");
                } catch (MessagingException | UnsupportedEncodingException e) {
                    // Manejar excepciones si es necesario
                    e.printStackTrace();
                }
            });
        }
    }

    public List<NovedadesDTO> obtenerNovedades (){
        List<Novedades> novedadesList = repository.findAll();
        return novedadesList.stream()
                .map(novedad -> modelMapper.map(novedad, NovedadesDTO.class))
                .collect(Collectors.toList());
    }
    public NovedadesDTO obtenerNovedadPorEdificioId(Long edificioId) throws Exception {
        Optional<Novedades> optionalNovedad = repository.findByEdificioId(edificioId);

        if (optionalNovedad.isPresent()) {
            Novedades novedad = optionalNovedad.get();
            return modelMapper.map(novedad, NovedadesDTO.class);
        } else {
            // Puedes personalizar la excepción según tus necesidades
            throw new Exception("No hay novedades para el edidicio ID: " + edificioId);
        }
    }

    public void editarNovedad(Long id, NovedadesSinIDDTO novedadesDTO) {
        Optional<Novedades> novedadOptional = repository.findById(id);

        if (novedadOptional.isPresent()) {
            Novedades novedadExistente = novedadOptional.get();
            novedadExistente.setTitulo(novedadesDTO.getTitulo());
            novedadExistente.setTexto(novedadesDTO.getTexto());
            novedadExistente.setFecha(novedadesDTO.getFecha());
            novedadExistente.setSendEmail(novedadesDTO.getSendEmail());
            repository.save(novedadExistente);
        } else {
            throw new NoSuchElementException("No se encontró la novedad con ID: " + id);
        }
    }
    @Cacheable(value = "novedadesCache")
    public List<NovedadesDTO> obtenerTodasLasNovedades() {
        List<Novedades> novedades = repository.findAll();
        return novedades.stream()
                .map(novedad -> modelMapper.map(novedad, NovedadesDTO.class))
                .collect(Collectors.toList());
    }

}
