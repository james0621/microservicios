package co.com.pragma.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import co.com.pragma.dto.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "image-service")
public interface ImageClientFeignRest {

	@GetMapping("/")
	public List<ImageDto> findAll();

	@GetMapping("/{id}")
	public ImageDto findById(@PathVariable String id);

	@PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ImageDto save(@RequestPart MultipartFile file, @RequestParam Object docClient);

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable String id);

}
