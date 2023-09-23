package extractor.service.strategy;

import extractor.dto.ResponseDto;

public class IPMExtractorService extends ExtractorServiceStrategy {

	@Override
	public ResponseDto init() {
		
		System.out.println("chegou na IPM");
		
		return null;
	}

}
