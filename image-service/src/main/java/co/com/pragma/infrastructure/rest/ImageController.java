package co.com.pragma.infrastructure.rest;

import java.io.IOException;
import java.util.List;

import co.com.pragma.application.service.ImageService;
import co.com.pragma.infrastructure.rest.mapper.ImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.com.pragma.infrastructure.rest.dto.ImageDto;
import co.com.pragma.infrastructure.exception.message.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class ImageController {

	@Autowired
	private ImageService imageService;

	@Autowired
	private ImageMapper mapper;

	@Operation(summary = "Consulta todas las imagenes almacenadas", method = "GET", tags = "read", responses = {
			@ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ImageDto.class)))})
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ImageDto>> findAll(){
		return new ResponseEntity<>(mapper.toImageDtoList(imageService.findAll()), HttpStatus.OK);
	}

	@Operation(summary = "Consulta una imagen por su ID", method = "GET", tags = "read", responses = {
			@ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ImageDto.class))),
			@ApiResponse(responseCode = "400", description = "Envió de datos incorrectos", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ImageDto> findById(@PathVariable String id) {
		return new ResponseEntity<>(mapper.imageToImageDto(imageService.findById(id)), HttpStatus.OK);
	}

	@Operation(summary = "Permite el guardado de una imagen con un archivo", method = "POST", tags = "save", responses = {
			@ApiResponse(responseCode = "201", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ImageDto.class))),
			@ApiResponse(responseCode = "400", description = "Envió de datos incorrectos", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "500", description = "Error en el proceso", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	@PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ImageDto> save(@RequestBody ImageDto imageDto) throws IOException{
		return new ResponseEntity<>(mapper.imageToImageDto(imageService.save(mapper.imageDtoToImage(imageDto))), HttpStatus.CREATED);
	}

	@Operation(summary = "Elimina una imagen por ID", method = "DELETE", tags = "delete", responses = {
			@ApiResponse(responseCode = "204", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ImageDto.class))),
			@ApiResponse(responseCode = "400", description = "Envió de datos incorrectos", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	@DeleteMapping("delete/{id}")
	public ResponseEntity<ImageDto> delete(@PathVariable String id) {
		if (imageService.findById(id) != null) {
			imageService.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}


}
