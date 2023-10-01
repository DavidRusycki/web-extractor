package extractor.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import extractor.entity.ComprasNetEntity;
import extractor.repository.ComprasNetRepository;

@Service
public class ComprasNetService {

	@Autowired
	private ComprasNetRepository repository;
	
	public ComprasNetEntity insert(ComprasNetEntity entity) {
		return repository.save(entity);
	}
	
}
