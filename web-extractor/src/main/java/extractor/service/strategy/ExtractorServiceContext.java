package extractor.service.strategy;

import org.springframework.stereotype.Component;

import extractor.dto.ResponseDto;
import extractor.enums.OrganEnum;
import extractor.factory.ExtractorFactory;

@Component
public class ExtractorServiceContext {

	private ExtractorServiceStrategy strategy;

	/**
	 * Inicia o processamento de acordo com a estratégia.
	 * @param organ
	 * @return
	 * @throws Exception
	 */
	public ResponseDto init(OrganEnum organ) throws Exception {
		strategy = ExtractorFactory.create(organ);
		var response = strategy.init();
		
		return response;
	}
	
}
