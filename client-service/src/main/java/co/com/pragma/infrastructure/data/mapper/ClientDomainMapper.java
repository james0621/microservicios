package co.com.pragma.infrastructure.data.mapper;

import co.com.pragma.domain.Client;
import co.com.pragma.infrastructure.data.entity.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientDomainMapper {

    ClientDomainMapper INSTANCE = Mappers.getMapper(ClientDomainMapper.class);

    @Mappings({
            @Mapping(target = "id", source = "clientEntity.id"),
            @Mapping(target = "name", source = "clientEntity.name"),
            @Mapping(target = "lastName", source = "clientEntity.lastName"),
            @Mapping(target = "docType", source = "clientEntity.docType"),
            @Mapping(target = "numDoc", source = "clientEntity.numDoc"),
            @Mapping(target = "age", source = "clientEntity.age"),
            @Mapping(target = "cityBirth", source = "clientEntity.cityBirth"),
            @Mapping(target = "idImage", source = "clientEntity.idImage")
    })
    Client clientEntityToClient(ClientEntity clientEntity);

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
    ClientEntity clientToClientEntity(Client client);
}
