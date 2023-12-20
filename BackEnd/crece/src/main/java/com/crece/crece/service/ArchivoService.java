package com.crece.crece.service;

import com.crece.crece.model.Archivo;
import com.crece.crece.model.Edificio;
import com.crece.crece.model.dto.ArchivoDTO;
import com.crece.crece.model.dto.EdificioDTO;
import com.crece.crece.model.dto.GetEdificioListDto;
import com.crece.crece.repository.ArchivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
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

@Service
public class ArchivoService{

  //  @Autowired
  //  private StorageRepository repository;

    @Autowired
    private ArchivoRepository fileDataRepository;
    @Autowired
    private EdificioService edificioService;
    @Value("file:${user.dir}/uploadedFiles/")  // user.dir representa el directorio de trabajo actual del proyecto
    private Resource uploadDirectory;
    public String uploadImageToFileSystem(MultipartFile file, Long edificioId, String categoria) throws IOException {
        String filePath=System.getProperty("user.dir") + File.separator + file.getOriginalFilename();
        LocalDateTime today = LocalDateTime.now();
        Edificio edificio = edificioService.leerEdificio(edificioId).orElseThrow(( )->new RuntimeException("No existe el edificio"));

        Archivo fileData=fileDataRepository.save(Archivo.builder()
                .name(StringUtils.replace(today.toString(), ":","-") + "-"+ file.getOriginalFilename())
                .type(categoria)
                .filePath(filePath)
                .edificio(edificio)
                .fechaCarga(LocalDate.now())

                .build());

        file.transferTo(new File(filePath));

        if (fileData != null) {
            return "file uploaded successfully : " + filePath;
        }
        return null;
    }


    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<Archivo> fileData = fileDataRepository.findByName(fileName);

        if (fileData.isPresent()) {
            String filePath = fileData.get().getFilePath();
            return Files.readAllBytes(new File(filePath).toPath());
        } else {
            // Manejar el caso cuando el archivo no se encuentra
            // Puedes lanzar una excepción, loggear un mensaje de error, etc.
            throw new FileNotFoundException("Archivo no encontrado: " + fileName);
        }
    }

    /*public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<Archivo> fileData = ArchivoRepository.findByName(fileName);
        String filePath=fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }*/
    public List<ArchivoDTO> getAllArchivos() {
        List<Archivo> archivos = fileDataRepository.findAll();
        List<ArchivoDTO> archivoDTOs = new ArrayList<>();

        for (Archivo archivo : archivos) {
            ArchivoDTO archivoDTO = convertirArchivoAArchivoDTO(archivo);
            archivoDTOs.add(archivoDTO);
        }

        return archivoDTOs;
    }

    private ArchivoDTO convertirArchivoAArchivoDTO(Archivo archivo) {

        ArchivoDTO archivoDTO = new ArchivoDTO();
        archivoDTO.setId(archivo.getId());
        archivoDTO.setDescripcion(archivo.getName());
        archivoDTO.setCategoria(archivo.getType());
        archivoDTO.setFilePath(archivo.getFilePath());
        if (archivo.getFechaCarga()!=null){
            archivoDTO.setFechaDeIngreso(archivo.getFechaCarga().toString());}
        // ... (otros campos)

        return archivoDTO;
    }

    public List<ArchivoDTO> getAllArchivosEntreFechas(LocalDate inicio, LocalDate fin) {
        List<Archivo> archivos = fileDataRepository.findAllByFechaCargaBetween(inicio, fin);
        List<ArchivoDTO> archivoDTOs = new ArrayList<>();

        for (Archivo archivo : archivos) {
            ArchivoDTO archivoDTO = convertirArchivoAArchivoDTO(archivo);
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
}
