package extractor.service.strategy;

import org.springframework.stereotype.Service;

@Service
public class IPMExtractorService extends ExtractorServiceStrategy {

	@Override
	public Boolean tryExtract() {
		System.out.println("chegou na IPM");
		
		return true;
	}

}
