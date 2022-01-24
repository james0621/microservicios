package co.com.pragma.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.pragma.dto.ImageMongoDto;
import co.com.pragma.entity.ImageMongo;
import co.com.pragma.exception.DataNoFoundException;
import co.com.pragma.repository.ImageMongoRepository;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageMongoServiceImpl implements ImageMongoService {

	@Autowired
	private ImageMongoRepository imageRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<ImageMongoDto> findByAll() {
		return imageRepo.findAll().stream().map(i -> modelMapper.map(i , ImageMongoDto.class)).collect(Collectors.toList());
	}

	@Override
	public ImageMongoDto findById(String id) {
		ImageMongo imageEntity = imageRepo.findById(id).orElseThrow(() -> new DataNoFoundException());
		ImageMongoDto image = modelMapper.map(imageEntity, ImageMongoDto.class);
		return image;
	}

	@Override
	public ImageMongoDto save(MultipartFile file, Long docClient) throws IOException {
		ImageMongoDto image =  new ImageMongoDto();
		Long docClientL = 0L;
		if (file != null && file.getContentType() != null){
			if(docClient != null && docClient.compareTo(0L) != 0 ){
				docClientL = docClient;
			}
			image = new ImageMongoDto(file, docClientL);
		}
		ImageMongo imageEntity = modelMapper.map(image, ImageMongo.class);
		imageEntity = imageRepo.save(imageEntity);

		return modelMapper.map(imageEntity, ImageMongoDto.class);
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
	public List<ImageMongoDto> findByDocClient(Long numDoc) {
		List<ImageMongoDto> images = new ArrayList<ImageMongoDto>();
		imageRepo.findByDocClient(numDoc).forEach(i -> {
			ImageMongoDto imageM = modelMapper.map(i, ImageMongoDto.class);
			images.add(imageM);
		});
		return images;
	}

	@Override
	public boolean deleteAllByDocClient(Long numDoc) {
		List<ImageMongoDto> images = findByDocClient(numDoc);
		if (images != null && !images.isEmpty()) {
			images.forEach(i -> delete(i.getId()));
			return true;
		}

		return false;
	}

}
