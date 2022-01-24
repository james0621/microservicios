package co.com.pragma.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import co.com.pragma.dto.ClientDto;

public interface ClientService {
	
	List<ClientDto> getAll();
	ClientDto getById(Long id);
	ClientDto save(ClientDto client, MultipartFile file);
	boolean delete(Long id);
	ClientDto getByDocTypeAndNumDoc(String docType, Long numDoc);
	List<ClientDto> findByAgeGreatherThanEqual (Integer age);
	ClientDto update(ClientDto client, MultipartFile file, Long id);
	
}
