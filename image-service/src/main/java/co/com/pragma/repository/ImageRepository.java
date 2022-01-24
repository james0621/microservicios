package co.com.pragma.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.com.pragma.entity.Image;

public interface ImageRepository extends MongoRepository<Image, String>{
	
	public Iterable<Image> findByDocClient(Long docClient);

}
