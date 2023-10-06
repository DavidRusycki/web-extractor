package extractor.service.strategy;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import extractor.dto.BaseDto;
import extractor.dto.ComprasNetDTO;
import extractor.entity.ComprasNetEntity;
import extractor.service.db.ComprasNetService;
import extractor.service.scraper.adapter.ComprasNetJsoapScraper;
import extractor.service.scraper.adapter.ScrapingAdapter;

@Service
public class ComprasNetExtractorService extends ExtractorServiceStrategy {

	@Autowired
	private ComprasNetService service;
	private ScrapingAdapter adapter;
	
	public ComprasNetExtractorService() {
		adapter = new ComprasNetJsoapScraper();
	}
	
	@Override
	public Boolean tryExtract() throws Exception {
		adapter.injectFirstUrl();
		adapter.scrapPageElements();
		List<ComprasNetDTO> dtos = castList(adapter.getDtos());
		List<ComprasNetEntity> entities = convertDtoToEntities(dtos); 
		saveAllEntities(entities);
		
		return true;
	}

	private List<ComprasNetDTO> castList(List<BaseDto> dtoList) {
		System.out.println("Realizando casting");
		List<ComprasNetDTO> dtos = dtoList.stream().map(dto -> (ComprasNetDTO)dto).collect(Collectors.toList());
		System.out.println("Casting finalizado");
		
		return dtos; 
	}
	
	private List<ComprasNetEntity> convertDtoToEntities(List<ComprasNetDTO> dtos) {
		System.out.println("Convertendo para entidades");
		List<ComprasNetEntity> entities = dtos.stream().map(dto -> ComprasNetDTO.toEntity(dto)).collect(Collectors.toList());
		System.out.println("Converção finalizada");
		
		return entities;
	}
	
	private void saveAllEntities(List<ComprasNetEntity> entities) {
		System.out.println("Salvando entidades");
		for (ComprasNetEntity comprasNetEntity : entities) {
			service.insert(comprasNetEntity);
		}
		System.out.println("entidades salvas");
	}
	
}
