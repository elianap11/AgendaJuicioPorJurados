package edu.egg.AgendaJJ.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import edu.egg.AgendaJJ.exception.TrialsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PhotoService {

    @Value("${my.property}")
    private String directory;

    public String copyPhoto(MultipartFile archive) throws TrialsException {
        try {
            String namePhoto = archive.getOriginalFilename();
            Path pathPhoto = Paths.get(directory, namePhoto).toAbsolutePath();
            //Files.copy copia la foto, es un método estático
            //este método getInputStream genera una excepción y dice: si ya existe, no lo copies
            Files.copy(archive.getInputStream(), pathPhoto, StandardCopyOption.REPLACE_EXISTING);
            return namePhoto;
        } catch (IOException e) {
            throw new TrialsException("Error al guardar la foto");
        }
    }
}