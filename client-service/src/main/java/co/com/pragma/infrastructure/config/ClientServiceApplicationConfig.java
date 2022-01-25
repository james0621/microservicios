package co.com.pragma.infrastructure.config;

import co.com.pragma.application.repository.ClientRepository;
import co.com.pragma.application.service.ClientService;
import co.com.pragma.application.service.ClientServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientServiceApplicationConfig {

    @Bean
    public ClientServiceImpl clientService(ClientRepository clientRepository){
        return new ClientServiceImpl(clientRepository);
    }

}
