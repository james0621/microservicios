package co.com.pragma.application.service;

import co.com.pragma.application.repository.ClientRepository;
import co.com.pragma.domain.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Client findByDocTypeAndNumDoc(String docType, Long numDoc) {
        return clientRepository.findByDocTypeAndNumDoc(docType, numDoc);
    }

    @Override
    public List<Client> findByAgeGreatherThanEqual(Integer age) {
        return clientRepository.findByAgeGreatherThanEqual(age);
    }

    @Override
    public Client save(Client client, MultipartFile file) throws IOException {
        return clientRepository.save(client, file);
    }

    @Override
    public Client update(Client client, MultipartFile file, Long id) throws IOException {
        return clientRepository.update(client, file, id);
    }

    @Override
    public boolean delete(Long id) {
        return clientRepository.delete(id);
    }
}
