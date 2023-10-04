package extractor.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import extractor.entity.ComprasNetEntity;
import extractor.service.db.ComprasNetService;

@RestController
@RequestMapping("/api/v1/oportunidade")
public class OportunityController {

	@Autowired
	private ComprasNetService service;
	
	@GetMapping("/todas")
	public List<ComprasNetEntity> getAll() {
		return service.getAll();
	}
	
	@GetMapping("/livres")
	public List<ComprasNetEntity> getAllNotViewed() {
		return service.getAllNotViewed();
	}
	
	@GetMapping("/{id}")
	public ComprasNetEntity getById(@PathVariable(name = "id") BigInteger id) {
		return service.getById(id);
	}
	
	@PatchMapping("/visto/{id}")
	public ComprasNetEntity updateVisto(@PathVariable(name = "id") BigInteger id) throws Exception {
		return service.updateVisto(id);
	}
	
}
