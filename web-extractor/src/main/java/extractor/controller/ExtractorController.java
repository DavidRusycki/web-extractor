package extractor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import extractor.entity.ComprasNetEntity;
import extractor.enums.OrganEnum;
import extractor.service.db.ComprasNetService;
import extractor.service.strategy.ExtractorServiceContext;

@RestController
@RequestMapping("/extractor")
public class ExtractorController {

	@Autowired
	private ExtractorServiceContext context;
	
	@Autowired
	private ComprasNetService service;
	
	@GetMapping("/health")
	public String health() {
		return "Running";
	}
	
	@GetMapping("/init")
	public String init(@RequestParam(name = "orgao") OrganEnum orgao) throws Exception {
		try {
			context.tryExtract(orgao);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return "sucess!";
	}
	
	@GetMapping("/test")
	public ComprasNetEntity teste() {
		
		var entity = new ComprasNetEntity();
		
		// ARRANGE
		entity.setOrgao("orgao teste do david");
		entity.setUasg(123456);
		entity.setModalidade("modalidade do david");
		entity.setObjeto("construção de api para licitações");
		entity.setDataEdital("a data é amanhã");
		entity.setEndereco("rua jão gomez da silva");
		entity.setTelefone("(47) 40028922");
		entity.setFax("(134) Não sei como é um fax");
		entity.setDataEntregaProposta("amanha até de tarde");
		entity.setUrlEdital("https://localhost:8080/amanhaedia");
		
		// ACT
		entity = service.insert(entity);
				
		return entity;
	}
	
}
