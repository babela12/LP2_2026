package pe.edu.upeu.lp2_back_gp1.services;

import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {
    void init(); //crea la carpeta img
    String store(MultipartFile file);
    void delete (String filename);
}
