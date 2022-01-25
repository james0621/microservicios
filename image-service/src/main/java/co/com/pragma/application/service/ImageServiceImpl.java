package co.com.pragma.application.service;

import co.com.pragma.application.repository.ImageRepository;
import co.com.pragma.domain.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{

    private final ImageRepository imageRepository;

    @Override
    public List<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Image findById(String id) {
        return imageRepository.findById(id);
    }

    @Override
    public List<Image> findByDocClient(Long numDoc) {
        return imageRepository.findByDocClient(numDoc);
    }

    @Override
    public Image save(MultipartFile file, Long docClient) throws IOException {
        return imageRepository.save(file, docClient);
    }

    @Override
    public boolean delete(String id) {
        return imageRepository.delete(id);
    }

    @Override
    public boolean deleteAllByDocClient(Long numDoc) {
        return imageRepository.deleteAllByDocClient(numDoc);
    }
}
