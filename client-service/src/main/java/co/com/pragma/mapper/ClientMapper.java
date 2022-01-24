package co.com.pragma.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import co.com.pragma.dto.ClientDto;
import co.com.pragma.entity.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mappings({
            @Mapping(target = "id", source = "client.id"),
            @Mapping(target = "name", source = "client.name"),
            @Mapping(target = "lastName", source = "client.lastName"),
            @Mapping(target = "docType", source = "client.docType"),
            @Mapping(target = "numDoc", source = "client.numDoc"),
            @Mapping(target = "age", source = "client.age"),
            @Mapping(target = "cityBirth", source = "client.cityBirth"),
            @Mapping(target = "idImage", source = "client.idImage")
    })
    ClientDto clientToClientDto(Client client);

    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "name", source = "dto.name"),
            @Mapping(target = "lastName", source = "dto.lastName"),
            @Mapping(target = "docType", source = "dto.docType"),
            @Mapping(target = "numDoc", source = "dto.numDoc"),
            @Mapping(target = "age", source = "dto.age"),
            @Mapping(target = "cityBirth", source = "dto.cityBirth"),
            @Mapping(target = "idImage", source = "dto.idImage")
    })
    Client clientDtoToClient(ClientDto dto);
}
