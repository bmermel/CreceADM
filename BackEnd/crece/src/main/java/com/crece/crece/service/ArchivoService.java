package com.crece.crece.service;

import com.crece.crece.model.Archivo;
import com.crece.crece.model.Edificio;
import com.crece.crece.model.dto.ArchivoDTO;

import com.crece.crece.model.dto.ArchivoYEdificioDTO;
import com.crece.crece.model.dto.NuevoArchivoDTO;
import com.crece.crece.repository.ArchivoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cglib.core.Local;
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
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ArchivoService{

  //  @Autowired
  //  private StorageRepository repository;

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ArchivoRepository fileDataRepository;
    @Autowired
    private EdificioService edificioService;
    /*@Value("file:${user.dir}/uploadedFiles/")  // user.dir representa el directorio de trabajo actual del proyecto
    private Resource uploadDirectory;*/

    @Value("${file.upload-directory}")
    private String uploadDirectoryPath;


    public NuevoArchivoDTO uploadImageToFileSystem(NuevoArchivoDTO nuevoArchivoDTO) throws IOException {

     Archivo archivo = mapper.convertValue(nuevoArchivoDTO,Archivo.class);
     archivo.setFechaCarga(LocalDate.now());
     Edificio edificio = edificioService.leerEdificio(nuevoArchivoDTO.getEdificioId()).orElse(null);
     if (edificio!= null){
         archivo.setEdificio(edificio);
         fileDataRepository.save(archivo);
     }
      return nuevoArchivoDTO;
    }

    @Cacheable(value = "archivosCache")

    public List<ArchivoYEdificioDTO> getAllArchivos() {
        List<Archivo> archivos = fileDataRepository.findAll();
        List<ArchivoYEdificioDTO> archivoDTOs = new ArrayList<>();

        for (Archivo archivo : archivos) {
            ArchivoYEdificioDTO archivoDTO = new ArchivoYEdificioDTO();
            archivoDTO.setId(archivo.getId());
            archivoDTO.setCategoria(archivo.getCategoria());
            archivoDTO.setFechaCarga(archivo.getFechaCarga());
            archivoDTO.setFilePath(archivo.getFilePath());
            archivoDTO.setAlias(archivo.getAlias());
            archivoDTO.setDescripcion(archivo.getDescripcion());
            archivoDTO.setTipoUsuario(archivo.getTipoUsuario());
            // Verificar si el campo 'edificio' es null y manejarlo apropiadamente
            if (archivo.getEdificio() != null) {
                archivoDTO.setEdificioNombre(archivo.getEdificio().getNombre());
            } else {
                archivoDTO.setEdificioNombre("Sin edificio");
                // Otra lógica de manejo para el caso de edificio null, si es necesario
            }

            archivoDTOs.add(archivoDTO);
        }
        return archivoDTOs;
    }





    public void borrarArchivo(Long id) {
        Optional<Archivo> archivoOptional = fileDataRepository.findById(id);
        if (archivoOptional.isPresent()) {
            Archivo archivo = archivoOptional.get();
            String filePath = archivo.getFilePath();

            // Eliminar el archivo del sistema de archivos
            try {
                Files.deleteIfExists(new File(filePath).toPath());
            } catch (IOException e) {
                throw new RuntimeException("Error al borrar el archivo del sistema de archivos", e);
            }

            // Eliminar la entrada de la base de datos
            fileDataRepository.deleteById(id);
        } else {
            throw new RuntimeException("No encontré el archivo, loro.");
        }
    }


       /*public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<Archivo> fileData = ArchivoRepository.findByName(fileName);
        String filePath=fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }*/

    /*    public List<ArchivoDTO> getAllArchivosEntreFechas(LocalDate inicio, LocalDate fin) {
        List<Archivo> archivos = fileDataRepository.findAllByFechaCargaBetween(inicio, fin);
        List<ArchivoDTO> archivoDTOs = new ArrayList<>();

        for (Archivo archivo : archivos) {
            ArchivoDTO archivoDTO = convertirArchivoAArchivoDTO(archivo);
            archivoDTOs.add(archivoDTO);
        }

        return archivoDTOs;
    }*/

    /*public List<ArchivoDTO> getArchivosPorEdificio(Long edificioId) {
        List<Archivo> archivos = fileDataRepository.findByEdificio_Id(edificioId);
        return archivos.stream()
                .map(archivo -> mapper.convertValue(archivo, ArchivoDTO.class))
                .collect(Collectors.toList());
    }

    public List<ArchivoDTO> getArchivosPorCategoria(String categoria) {
        List<Archivo> archivos = fileDataRepository.findByType(categoria);
        return archivos.stream()
                .map(archivo -> mapper.convertValue(archivo, ArchivoDTO.class))
                .collect(Collectors.toList());
    }

    public List<ArchivoDTO> getArchivosPorNombre(String nombre) {
        List<Archivo> archivos = fileDataRepository.findByNameContainingIgnoreCase(nombre);
        return archivos.stream()
                .map(archivo -> mapper.convertValue(archivo, ArchivoDTO.class))
                .collect(Collectors.toList());
    }

    public List<ArchivoDTO> getArchivosOrdenadosPorFecha() {
        List<Archivo> archivos = fileDataRepository.findAllByOrderByFechaCargaDesc();
        List<ArchivoDTO> archivosDTO = new ArrayList<>();
        for (Archivo archivo : archivos) {
            ArchivoDTO archivoDTO = mapper.convertValue(archivo, ArchivoDTO.class);
            // Verificar si el campo 'edificio' es null y manejarlo apropiadamente
            if (archivo.getEdificio() != null) {
                archivoDTO.setNombreEdificio(archivo.getEdificio().getNombre());
                // Otras operaciones con el objeto ArchivoDTO
            }
            archivosDTO.add(archivoDTO);
        }
        return archivosDTO;
    }



    public String obtenerNombrePorId(Long id){
        Optional<Archivo> archivoOptional = fileDataRepository.findById(id);
        if (archivoOptional.isPresent() ) {
            Archivo archivo = archivoOptional.get();
            System.out.println(archivo.getFilePath());
            return archivo.getFilePath();
        } else {
            throw new RuntimeException("No se encontró ningun archivo con el ID enviado");
        }
    }*/
}
