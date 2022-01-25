package co.com.pragma.infrastructure.data.repository;

import co.com.pragma.infrastructure.data.entity.ImageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageDataRepository extends MongoRepository<ImageEntity, String>{
	
	public Iterable<ImageEntity> findByDocClient(Long docClient);

}
