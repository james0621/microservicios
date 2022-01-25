package co.com.pragma.infrastructure.rest.mapper;

import co.com.pragma.domain.Client;
import co.com.pragma.infrastructure.rest.dto.ClientDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDto clientToClientDto(Client client);

    List<ClientDto> toDtoList(List<Client> clients);

    Client clientDtoToClient(ClientDto dto);

    List<Client> toEntityList(List<ClientDto> clientDto);
}
