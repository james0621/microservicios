package co.com.pragma.service;

import java.io.IOException;
import java.util.List;

import co.com.pragma.dto.ImageMongoDto;
import org.springframework.web.multipart.MultipartFile;

public interface ImageMongoService {

	List<ImageMongoDto> findByAll();
	ImageMongoDto findById(String id);
	List<ImageMongoDto> findByDocClient(Long numDoc);
	ImageMongoDto save(MultipartFile file, Long docClient) throws IOException;
	boolean delete(String id);
	boolean deleteAllByDocClient(Long numDoc);

}
