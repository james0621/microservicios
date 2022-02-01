package co.com.pragma.application.service;

import java.io.IOException;
import java.util.List;

import co.com.pragma.domain.Client;
import org.springframework.web.multipart.MultipartFile;

public interface ClientService {

	List<Client> findAll();
	Client findById(Long id);
	Client findByDocTypeAndNumDoc(String docType, Long numDoc);
	List<Client> findByAgeGreatherThanEqual (Integer age);
	Client save(Client client, MultipartFile file) throws IOException;
	Client update(Client client, MultipartFile file, Long id) throws IOException;
	boolean delete(Long id);
	
}
