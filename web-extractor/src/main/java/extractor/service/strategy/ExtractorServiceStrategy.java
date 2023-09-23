package extractor.service.strategy;

import org.springframework.stereotype.Component;

import extractor.dto.ResponseDto;

public abstract class ExtractorServiceStrategy {

	public abstract ResponseDto init();
	
}
