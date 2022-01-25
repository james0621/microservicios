package co.com.pragma.application.repository;

import co.com.pragma.domain.Client;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClientRepository {

    List<Client> findAll();
    Client findById(Long id);
    Client findByDocTypeAndNumDoc(String docType, Long numDoc);
    List<Client> findByAgeGreatherThanEqual (Integer age);
    Client save(Client client, MultipartFile file);
    Client update(Client client, MultipartFile file, Long id);
    boolean delete(Long id);

}
