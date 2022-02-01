package co.com.pragma.infrastructure.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import co.com.pragma.infrastructure.rest.dto.ImageDto;

import java.util.List;

@FeignClient(name = "image-service")
public interface ImageClientFeignRest {

	@GetMapping("/")
	public List<ImageDto> findAll();

	@GetMapping("/{id}")
	public ImageDto findById(@PathVariable String id);

	@PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ImageDto save(@RequestBody ImageDto imageDto);

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable String id);

}
