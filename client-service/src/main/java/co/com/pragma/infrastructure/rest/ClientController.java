package co.com.pragma.infrastructure.rest;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import co.com.pragma.infrastructure.rest.dto.ClientDto;
import co.com.pragma.infrastructure.rest.mapper.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import co.com.pragma.infrastructure.exception.message.ErrorMessage;
import co.com.pragma.application.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("client")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@Autowired
	private ClientMapper mapper;

	@Operation(summary = "Consulta todos los clientes registrados", method = "GET", tags = "read", responses = {
			@ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ClientDto.class))) })
	@GetMapping("/")
	public ResponseEntity<List<ClientDto>> findAll() {
		return new ResponseEntity<>(mapper.toDtoList(clientService.findAll()), HttpStatus.OK);
	}

	@Operation(summary = "Consulta un cliente por su ID", method = "GET", tags = "read", responses = {
			@ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ClientDto.class))),
			@ApiResponse(responseCode = "400", description = "Envió de datos incorrectos", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	@GetMapping("/{id}")
	public ResponseEntity<ClientDto> getById(@PathVariable Long id) {
		return new ResponseEntity<>(mapper.clientToClientDto(clientService.findById(id)), HttpStatus.OK);
	}

	@Operation(summary = "Consulta un cliente por tipo y numero de documento", method = "GET", tags = "read", responses = {
			@ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ClientDto.class))),
			@ApiResponse(responseCode = "400", description = "Envió de datos incorrectos", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	@GetMapping("/search/doc/type/{docType}/numDoc/{numDoc}")
	public ResponseEntity<ClientDto> getByDocTypeAndNumDoc(@PathVariable String docType, @PathVariable Long numDoc) {
		return new ResponseEntity<>(mapper.clientToClientDto(clientService.findByDocTypeAndNumDoc(docType, numDoc)), HttpStatus.OK);
	}

	@Operation(summary = "Consulta los clientes mayores o iguales a una edad", method = "GET", tags = "read", responses = {
			@ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ClientDto.class))),
			@ApiResponse(responseCode = "400", description = "Envió de datos incorrectos", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	@GetMapping("search/age/{age}")
	public ResponseEntity<List<ClientDto>> getByAgeGreatherThanEqual(@PathVariable Integer age) {
		return clientService.findByAgeGreatherThanEqual(age).size() > 0
				? new ResponseEntity<>(mapper.toDtoList(clientService.findByAgeGreatherThanEqual(age)), HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@Operation(summary = "Permite el guardado de un cliente", method = "POST", tags = "save", responses = {
			@ApiResponse(responseCode = "201", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ClientDto.class))),
			@ApiResponse(responseCode = "400", description = "Envió de datos incorrectos", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "500", description = "Error en el proceso", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	@PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientDto> save(@Valid @RequestPart ClientDto client, @RequestParam(required = false) MultipartFile file) throws IOException {
		return new ResponseEntity<>(mapper.clientToClientDto(clientService.save(mapper.clientDtoToClient(client), file)), HttpStatus.CREATED);
	}

	@Operation(summary = "Permite actualizar un cliente por ID", method = "PUT", tags = "update", responses = {
			@ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ClientDto.class))),
			@ApiResponse(responseCode = "400", description = "Envió de datos incorrectos", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "500", description = "Error en el proceso", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	@PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClientDto> update(@Valid @RequestPart ClientDto client, @RequestParam(required = false) MultipartFile file,@PathVariable Long id) {
		return new ResponseEntity<>(mapper.clientToClientDto(clientService.update(mapper.clientDtoToClient(client) ,file,id)), HttpStatus.OK);
	}

	@Operation(summary = "Elimina un cliente por ID", method = "DELETE", tags = "delete", responses = {
			@ApiResponse(responseCode = "204", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ClientDto.class))),
			@ApiResponse(responseCode = "400", description = "Envió de datos incorrectos", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		if (clientService.findById(id) != null) {
			clientService.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
