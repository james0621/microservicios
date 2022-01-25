package co.com.pragma.application.service;

import co.com.pragma.domain.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    List<Image> findAll();
    Image findById(String id);
    List<Image> findByDocClient(Long numDoc);
    Image save(MultipartFile file, Long docClient) throws IOException;
    boolean delete(String id);
    boolean deleteAllByDocClient(Long numDoc);
}
