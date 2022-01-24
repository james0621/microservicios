package co.com.pragma.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import co.com.pragma.dto.ImageDto;
import co.com.pragma.exception.message.ErrorMessage;
import co.com.pragma.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class ImageController {

	@Autowired
	private ImageService imageService;

	@Operation(summary = "Consulta todas las imagenes almacenadas", method = "GET", tags = "read", responses = {
			@ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ImageDto.class)))})
	@GetMapping("/")
	public ResponseEntity<List<ImageDto>> findAll(){
		return new ResponseEntity<>(imageService.findByAll(), HttpStatus.OK);
	}

	@Operation(summary = "Consulta una imagen por su ID", method = "GET", tags = "read", responses = {
			@ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ImageDto.class))),
			@ApiResponse(responseCode = "400", description = "Envió de datos incorrectos", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	@GetMapping("/{id}")
	public ResponseEntity<ImageDto> findById(@PathVariable String id) {
		return new ResponseEntity<>(imageService.findById(id), HttpStatus.OK);
	}

	@Operation(summary = "Permite el guardado de una imagen con un archivo", method = "POST", tags = "save", responses = {
			@ApiResponse(responseCode = "201", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ImageDto.class))),
			@ApiResponse(responseCode = "400", description = "Envió de datos incorrectos", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "500", description = "Error en el proceso", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	@PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ImageDto> save(@RequestPart MultipartFile file , @RequestParam Long docClient) throws IOException{
		return new ResponseEntity<>(imageService.save(file, docClient), HttpStatus.CREATED);
	}

	@Operation(summary = "Elimina una imagen por ID", method = "DELETE", tags = "delete", responses = {
			@ApiResponse(responseCode = "204", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ImageDto.class))),
			@ApiResponse(responseCode = "400", description = "Envió de datos incorrectos", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) {
		if (imageService.findById(id) != null) {
			imageService.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}


}
