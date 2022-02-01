package co.com.pragma.infrastructure.data.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.com.pragma.application.repository.ClientRepository;
import co.com.pragma.domain.Client;
import co.com.pragma.infrastructure.data.entity.ClientEntity;
import co.com.pragma.infrastructure.data.mapper.ClientDomainMapper;
import co.com.pragma.infrastructure.data.repository.IClientRepository;
import co.com.pragma.infrastructure.rest.dto.ImageDto;
import co.com.pragma.infrastructure.util.UtilImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import co.com.pragma.infrastructure.feign.ImageClientFeignRest;
import co.com.pragma.infrastructure.exception.DataNoFoundException;
import org.springframework.web.multipart.MultipartFile;

@Service
@Primary
public class ClientDataServiceImpl implements ClientRepository {

    @Autowired
    private IClientRepository clientRepo;

    @Autowired
    private ImageClientFeignRest imageFeign;

    @Autowired
    private ClientDomainMapper mapper;

    @Override
    public List<Client> findAll() {
        List<ClientEntity> clientsEntity = (List<ClientEntity>) clientRepo.findAll();
        List<ImageDto> images = imageFeign.findAll();
        List<Client> clients = new ArrayList<>();
        clientsEntity.stream().forEach(c -> {
            Client client = mapper.clientEntityToClient(c);
            if(client.getIdImage() != null) {
                images.forEach(i -> {
                    if (i.getId().equals(client.getIdImage())) {
                        client.setImage(i);
                    }
                });
            }
            clients.add(client);
        });
        return clients;
    }

    @Override
    public Client findById(Long id) {
        ClientEntity clientEntity = clientRepo.findById(id)
                .orElseThrow(DataNoFoundException::new);
        Client client = mapper.clientEntityToClient(clientEntity);
        if(client.getIdImage() != null){
            ImageDto image = imageFeign.findById(client.getIdImage());
            client.setImage(image);
        }
        return client;
    }

    @Override
    public Client save(Client client, MultipartFile file) throws IOException {
        ImageDto imageDto =  new ImageDto();
        if(file != null && file.getContentType() != null){
            UtilImage.validateImageType(file.getOriginalFilename());
            imageDto = new ImageDto(file, client.getNumDoc());
            imageDto = imageFeign.save(imageDto);
            client.setIdImage(imageDto.getId());
        }
        ClientEntity clientEntity = clientRepo.save(mapper.clientToClientEntity(client));
        client = mapper.clientEntityToClient(clientEntity);
        if(imageDto.getId() != null) {
            client.setImage(imageDto);
        }
        return client;
    }

    @Override
    public Client update(Client client, MultipartFile file, Long id) throws IOException {
        Client clientResult = findById(id);
        if(clientResult != null && clientResult.getId() != null){
            if (clientResult.getNumDoc().compareTo(client.getNumDoc()) != 0){
                throw new DataNoFoundException();
            }
            client.setId(id);
            client.setRegisDate(clientResult.getRegisDate());
            if(file != null && file.getContentType() != null){
                if(clientResult.getIdImage() != null) {
                    imageFeign.delete(clientResult.getIdImage());
                }
                ImageDto imageDto = new ImageDto(file, client.getNumDoc());
                imageDto = imageFeign.save(imageDto);
                client.setIdImage(imageDto.getId());
                client.setImage(imageDto);
            }else if(clientResult.getIdImage() != null){
                client.setIdImage(clientResult.getIdImage());
                client.setImage(clientResult.getImage());
            }
        }
        ClientEntity clientEntity = mapper.clientToClientEntity(client);
        clientResult = mapper.clientEntityToClient(clientRepo.save(clientEntity));
        clientResult.setImage(client.getImage());
        return clientResult;
    }

    @Override
    public boolean delete(Long id) {
        Client client = findById(id);
        if(client != null && client.getId() != null){
            if(client.getIdImage() != null){
                imageFeign.delete(client.getIdImage());
            }
            clientRepo.delete(mapper.clientToClientEntity(client));
            return true;
        }
        return false;
    }

    @Override
    public Client findByDocTypeAndNumDoc(String docType, Long numDoc) {
        ClientEntity clientEntity = clientRepo.findByDocTypeAndNumDoc(docType,numDoc).orElseThrow(DataNoFoundException::new);
        Client client = mapper.clientEntityToClient(clientEntity);
        if(client.getIdImage() != null){
            ImageDto image = imageFeign.findById(client.getIdImage());
            client.setImage(image);
        }
        return client;
    }

    @Override
    public List<Client> findByAgeGreatherThanEqual(Integer age) {
        List<ClientEntity> clientsEntity = (List<ClientEntity>) clientRepo.findByAgeGreatherThanEqual(age);
        List<ImageDto> images = imageFeign.findAll();
        List<Client> clients =  new ArrayList<>();
        clientsEntity.forEach(c -> {
            Client client = mapper.clientEntityToClient(c);
            if(client.getIdImage() != null){
                images.forEach(i -> {
                    if(i.getId().equals(c.getIdImage())){
                        client.setImage(i);
                    }
                });
            }
            clients.add(client);
        });
        return clients;
    }

}
