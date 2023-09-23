package extractor.factory;

import extractor.enums.OrganEnum;
import extractor.service.strategy.ComprasNetExtractorService;
import extractor.service.strategy.ExtractorServiceStrategy;
import extractor.service.strategy.IPMExtractorService;

public class ExtractorFactory {

	public static ExtractorServiceStrategy create(OrganEnum organ) throws Exception {
		ExtractorServiceStrategy instance = null;
		
		switch (organ) {
			case COMPRAS_NET:
				instance = new ComprasNetExtractorService();
				break;
	
			case IPM:
				// IPM Service
				instance = new IPMExtractorService();
				break;
		}

		if (instance == null) {
			throw new Exception("Valor não permitido!");
		}
		
		return instance;
	}

}
