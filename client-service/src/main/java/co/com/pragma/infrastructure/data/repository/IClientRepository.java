package co.com.pragma.infrastructure.data.repository;

import java.util.Optional;

import co.com.pragma.infrastructure.data.entity.ClientEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IClientRepository extends CrudRepository<ClientEntity, Long>{
	
	public Optional<ClientEntity> findByDocTypeAndNumDoc(String docType, Long numDoc);
	
	@Query("SELECT c FROM ClientEntity c WHERE c.age >= ?1")
	public Iterable<ClientEntity> findByAgeGreatherThanEqual(Integer age);

}
