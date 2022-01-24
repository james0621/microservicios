package co.com.pragma.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import co.com.pragma.mapper.ImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.pragma.dto.ImageDto;
import co.com.pragma.entity.Image;
import co.com.pragma.exception.DataNoFoundException;
import co.com.pragma.repository.ImageRepository;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageRepository imageRepo;

	@Autowired
	private ImageMapper mapper;

	@Override
	public List<ImageDto> findByAll() {
		return imageRepo.findAll().stream().map(i -> mapper.imageToImageDto(i)).collect(Collectors.toList());
	}

	@Override
	public ImageDto findById(String id) {
		Image imageEntity = imageRepo.findById(id).orElseThrow(() -> new DataNoFoundException());
		ImageDto image = mapper.imageToImageDto(imageEntity);
		return image;
	}

	@Override
	public ImageDto save(MultipartFile file, Long docClient) throws IOException {
		ImageDto image =  new ImageDto();
		Long docClientL = 0L;
		if (file != null && file.getContentType() != null){
			if(docClient != null && docClient.compareTo(0L) != 0 ){
				docClientL = docClient;
			}
			image = new ImageDto(file, docClientL);
		}
		Image imageEntity = mapper.imageDtoToImage(image);
		imageEntity = imageRepo.save(imageEntity);

		return mapper.imageToImageDto(imageEntity);
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
	public List<ImageDto> findByDocClient(Long numDoc) {
		List<ImageDto> images = new ArrayList<ImageDto>();
		imageRepo.findByDocClient(numDoc).forEach(i -> {
			ImageDto imageM = mapper.imageToImageDto(i);
			images.add(imageM);
		});
		return images;
	}

	@Override
	public boolean deleteAllByDocClient(Long numDoc) {
		List<ImageDto> images = findByDocClient(numDoc);
		if (images != null && !images.isEmpty()) {
			images.forEach(i -> delete(i.getId()));
			return true;
		}

		return false;
	}

}
