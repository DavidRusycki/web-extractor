package extractor.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import extractor.enums.OrganEnum;
import extractor.service.strategy.ComprasNetExtractorService;
import extractor.service.strategy.ExtractorServiceStrategy;
import extractor.service.strategy.IPMExtractorService;
import lombok.Data;

@Data
@Component
public class ExtractorFactory {

	@Autowired
	private ComprasNetExtractorService comprasNetExtractorService;
	
	@Autowired
	private IPMExtractorService ipmExtractorService;
	
	public ExtractorServiceStrategy create(OrganEnum organ) throws Exception {
		ExtractorServiceStrategy instance = null;
		
		switch (organ) {
			case COMPRAS_NET:
				instance = comprasNetExtractorService;
				break;
	
			case IPM:
				// IPM Service
				instance = ipmExtractorService;
				break;
		}

		if (instance == null) {
			throw new Exception("Valor não permitido!");
		}
		
		return instance;
	}

}
