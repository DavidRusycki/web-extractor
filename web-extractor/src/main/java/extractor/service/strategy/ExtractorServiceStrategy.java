package extractor.service.strategy;

import extractor.dto.ResponseDto;

public abstract class ExtractorServiceStrategy {

	public abstract ResponseDto tryExtract() throws Exception;
	
}
