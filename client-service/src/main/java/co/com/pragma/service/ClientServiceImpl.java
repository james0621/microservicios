package co.com.pragma.service;

import java.util.ArrayList;
import java.util.List;

import co.com.pragma.util.UtilImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import co.com.pragma.clients.ImageClientFeignRest;
import co.com.pragma.dto.ClientDto;
import co.com.pragma.dto.ImageDto;
import co.com.pragma.entity.Client;
import co.com.pragma.exception.DataNoFoundException;
import co.com.pragma.mapper.ClientMapper;
import co.com.pragma.repository.IClientRepository;
import org.springframework.web.multipart.MultipartFile;

@Service
@Primary
public class ClientServiceImpl implements ClientService {

    @Autowired
    private IClientRepository clientRepo;

    @Autowired
    private ImageClientFeignRest imageFeign;

    @Autowired
    private ClientMapper mapper;

    @Override
    public List<ClientDto> getAll() {
        List<Client> clientsEntity = (List<Client>) clientRepo.findAll();
        List<ImageDto> images = imageFeign.findAll();
        List<ClientDto> clients = new ArrayList<ClientDto>();
        clientsEntity.stream().forEach(c -> {
            ClientDto clientDto = mapper.clientToClientDto(c);
            if(clientDto.getIdImage() != null) {
                images.forEach(i -> {
                    if (i.getId().equals(clientDto.getIdImage())) {
                        clientDto.setImage(i);
                    }
                });
            }
            clients.add(clientDto);
        });
        return clients;
    }

    @Override
    public ClientDto getById(Long id) {
        Client clientEntity = clientRepo.findById(id)
                .orElseThrow(() -> new DataNoFoundException());
        ClientDto client = mapper.clientToClientDto(clientEntity);
        if(client.getIdImage() != null){
            ImageDto image = imageFeign.findById(client.getIdImage());
            client.setImage(image);
        }
        return client;
    }

    @Override
    public ClientDto save(ClientDto client, MultipartFile file) {
        ImageDto imageDto =  new ImageDto();
        if(file != null && file.getContentType() != null){
            UtilImage.validateImageType(file.getOriginalFilename());
            imageDto = imageFeign.save(file, client.getNumDoc());
            client.setIdImage(imageDto.getId());
        }
        Client clientEntity = clientRepo.save(mapper.clientDtoToClient(client));
        ClientDto clientDto = mapper.clientToClientDto(clientEntity);
        if(imageDto.getId() != null) {
            clientDto.setImage(imageDto);
        }
        return clientDto;
    }

    @Override
    public ClientDto update(ClientDto client, MultipartFile file, Long id) {
        ClientDto clientResult = getById(id);
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
                ImageDto imageDto = imageFeign.save(file, client.getNumDoc());
                client.setIdImage(imageDto.getId());
                client.setImage(imageDto);
            }else if(clientResult.getIdImage() != null){
                client.setIdImage(clientResult.getIdImage());
                client.setImage(clientResult.getImage());
            }
        }
        Client clientEntity = mapper.clientDtoToClient(client);
        clientResult = mapper.clientToClientDto(clientRepo.save(clientEntity));
        clientResult.setImage(client.getImage());
        return clientResult;
    }

    @Override
    public boolean delete(Long id) {
        ClientDto client = getById(id);
        if(client != null & client.getId() != null){
            if(client.getIdImage() != null){
                imageFeign.delete(client.getIdImage());
            }
            clientRepo.delete(mapper.clientDtoToClient(client));
            return true;
        }
        return false;
    }

    @Override
    public ClientDto getByDocTypeAndNumDoc(String docType, Long numDoc) {
        Client clientEntity = clientRepo.findByDocTypeAndNumDoc(docType,numDoc).orElseThrow(() -> new DataNoFoundException());
        ClientDto clientDto = mapper.clientToClientDto(clientEntity);
        if(clientDto.getIdImage() != null){
            ImageDto image = imageFeign.findById(clientDto.getIdImage());
            clientDto.setImage(image);
        }
        return clientDto;
    }

    @Override
    public List<ClientDto> findByAgeGreatherThanEqual(Integer age) {
        List<Client> clientsEntity = (List<Client>) clientRepo.findByAgeGreatherThanEqual(age);
        List<ImageDto> images = imageFeign.findAll();
        List<ClientDto> clients =  new ArrayList<ClientDto>();
        clientsEntity.forEach(c -> {
            ClientDto clientDto = mapper.clientToClientDto(c);
            if(clientDto.getIdImage() != null){
                images.forEach(i -> {
                    if(i.getId().equals(c.getIdImage())){
                        clientDto.setImage(i);
                    }
                });
            }
            clients.add(clientDto);
        });
        return clients;
    }

}
