package extractor.service.db;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import extractor.entity.ComprasNetEntity;
import extractor.repository.ComprasNetRepository;

@Service
public class ComprasNetService {

	@Autowired
	private ComprasNetRepository repository;
	
	public List<ComprasNetEntity> getAll() {
		return repository.findAll();
	}
	
	public List<ComprasNetEntity> getAllNotViewed() {
		return repository.findByVistoEquals(false);
	}
	
	public ComprasNetEntity getById(BigInteger id) {
		Optional<ComprasNetEntity> optional = repository.findById(id);
		return optional.get();
	}
	
	public ComprasNetEntity insert(ComprasNetEntity entity) {
		System.out.println("Inserindo entidade");
		return repository.save(entity);
	}
	
	public ComprasNetEntity updateVisto(BigInteger id) throws Exception {
		if (!repository.existsById(id)) {
			throw new Exception("Registro não encontrado");
		}
		
		Optional<ComprasNetEntity> optional = repository.findById(id);
		ComprasNetEntity entity = optional.get();
		entity.setVisto(true);
		entity = repository.save(entity);
		
		return entity;
	}
	
}
