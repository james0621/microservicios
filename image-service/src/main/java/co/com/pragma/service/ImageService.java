package co.com.pragma.service;

import java.io.IOException;
import java.util.List;

import co.com.pragma.dto.ImageDto;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	List<ImageDto> findByAll();
	ImageDto findById(String id);
	List<ImageDto> findByDocClient(Long numDoc);
	ImageDto save(MultipartFile file, Long docClient) throws IOException;
	boolean delete(String id);
	boolean deleteAllByDocClient(Long numDoc);

}
