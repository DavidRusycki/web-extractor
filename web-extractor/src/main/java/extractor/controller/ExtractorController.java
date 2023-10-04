package extractor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import extractor.dto.ResponseDto;
import extractor.entity.ComprasNetEntity;
import extractor.enums.OrganEnum;
import extractor.service.db.ComprasNetService;
import extractor.service.strategy.ExtractorServiceContext;

@RestController
@RequestMapping("/extractor")
public class ExtractorController {

	@Autowired
	private ExtractorServiceContext context;
	
	@GetMapping("/health")
	public String health() {
		return "Running";
	}
	
	@GetMapping("/init")
	public ResponseDto init(@RequestParam(name = "orgao") OrganEnum orgao) throws Exception {
		ResponseDto response = null;
		try {
			response = context.tryExtract(orgao);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return response;
	}
	
}
