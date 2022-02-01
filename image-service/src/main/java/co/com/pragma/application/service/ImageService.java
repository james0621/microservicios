package co.com.pragma.application.service;

import co.com.pragma.domain.Image;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    List<Image> findAll();
    Image findById(String id);
    List<Image> findByDocClient(Long numDoc);
    Image save(Image image) throws IOException;
    boolean delete(String id);
    boolean deleteAllByDocClient(Long numDoc);
}
