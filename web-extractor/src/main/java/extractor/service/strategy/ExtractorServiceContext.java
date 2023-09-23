package extractor.service.strategy;

import org.springframework.stereotype.Component;

import extractor.dto.ResponseDto;
import extractor.enums.OrganEnum;
import extractor.factory.ExtractorFactory;

@Component
public class ExtractorServiceContext {

	private ExtractorServiceStrategy strategy;

	/**
	 * Tenta extrair as informações de acordo com a estratégia.
	 * @param organ
	 * @return
	 * @throws Exception
	 */
	public ResponseDto tryExtract(OrganEnum organ) throws Exception {
		strategy = ExtractorFactory.create(organ);
		var response = strategy.tryExtract();
		
		return response;
	}
	
}
