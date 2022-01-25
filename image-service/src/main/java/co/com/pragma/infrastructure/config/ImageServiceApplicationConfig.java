package co.com.pragma.infrastructure.config;

import co.com.pragma.application.repository.ImageRepository;
import co.com.pragma.application.service.ImageServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageServiceApplicationConfig {

    @Bean
    public ImageServiceImpl imageService (ImageRepository imageRepository){
        return new ImageServiceImpl(imageRepository);
    }
}
