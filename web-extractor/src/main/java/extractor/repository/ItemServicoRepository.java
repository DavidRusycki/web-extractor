package extractor.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import extractor.entity.ItemServicoEntity;

@Repository
public interface ItemServicoRepository extends JpaRepository<ItemServicoEntity, BigInteger>{

}
