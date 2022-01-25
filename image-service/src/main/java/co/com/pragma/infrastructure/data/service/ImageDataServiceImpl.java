package co.com.pragma.infrastructure.data.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import co.com.pragma.application.repository.ImageRepository;
import co.com.pragma.domain.Image;
import co.com.pragma.infrastructure.data.entity.ImageEntity;
import co.com.pragma.infrastructure.data.mapper.ImageDomainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.pragma.infrastructure.exception.DataNoFoundException;
import co.com.pragma.infrastructure.data.repository.ImageDataRepository;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageDataServiceImpl implements ImageRepository {

    @Autowired
    private ImageDataRepository imageRepo;

    @Autowired
    private ImageDomainMapper mapper;

    @Override
    public List<Image> findAll() {
        return imageRepo.findAll().stream().map(i -> mapper.imageEntityToImage(i)).collect(Collectors.toList());
    }

    @Override
    public Image findById(String id) {
        ImageEntity imageEntity = imageRepo.findById(id).orElseThrow(() -> new DataNoFoundException());
        Image image = mapper.imageEntityToImage(imageEntity);
        return image;
    }

    @Override
    public Image save(MultipartFile file, Long docClient) throws IOException {
        Image image =  new Image();
        Long docClientL = 0L;
        if (file != null && file.getContentType() != null){
            if(docClient != null && docClient.compareTo(0L) != 0 ){
                docClientL = docClient;
            }
            image = new Image(file, docClientL);
        }
        ImageEntity imageEntity = mapper.imageToImageEntity(image);
        imageEntity = imageRepo.save(imageEntity);

        return mapper.imageEntityToImage(imageEntity);
    }

    @Override
    public boolean delete(String id) {
        if (findById(id) != null) {
            imageRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Image> findByDocClient(Long numDoc) {
        List<Image> images = new ArrayList<Image>();
        imageRepo.findByDocClient(numDoc).forEach(i -> {
            Image imageM = mapper.imageEntityToImage(i);
            images.add(imageM);
        });
        return images;
    }

    @Override
    public boolean deleteAllByDocClient(Long numDoc) {
        List<Image> images = findByDocClient(numDoc);
        if (images != null && !images.isEmpty()) {
            images.forEach(i -> delete(i.getId()));
            return true;
        }

        return false;
    }

}
