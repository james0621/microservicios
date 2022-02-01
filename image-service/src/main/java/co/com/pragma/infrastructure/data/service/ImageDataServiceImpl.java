package co.com.pragma.infrastructure.data.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import co.com.pragma.application.repository.ImageRepository;
import co.com.pragma.domain.Image;
import co.com.pragma.infrastructure.data.entity.ImageEntity;
import co.com.pragma.infrastructure.data.mapper.ImageDomainMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.pragma.infrastructure.exception.DataNoFoundException;
import co.com.pragma.infrastructure.data.repository.ImageDataRepository;

@Service
@RequiredArgsConstructor
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
        ImageEntity imageEntity = imageRepo.findById(id).orElseThrow(DataNoFoundException::new);
        return mapper.imageEntityToImage(imageEntity);
    }

    @Override
    public Image save(Image image)  {
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
        List<Image> images = new ArrayList<>();
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
