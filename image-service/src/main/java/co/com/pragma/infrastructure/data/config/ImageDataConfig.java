package co.com.pragma.infrastructure.data.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = "co.com.pragma.infrastructure.data.repository")
@ConfigurationProperties("spring.datasource")
@NoArgsConstructor
@Getter
@Setter
@EnableMongoAuditing
@EntityScan(basePackages = "co.com.pragma.infrastructure.data.entity")
public class ImageDataConfig {
}
