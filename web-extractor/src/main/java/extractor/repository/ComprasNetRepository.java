package extractor.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import extractor.entity.ComprasNetEntity;

@Repository
public interface ComprasNetRepository extends JpaRepository<ComprasNetEntity, BigInteger>{

	List<ComprasNetEntity> findByVistoEquals(Boolean value);	
	
}
