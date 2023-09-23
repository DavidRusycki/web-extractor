package extractor.service.strategy;

import extractor.dto.ResponseDto;
import extractor.service.adapter.ComprasNetJsoapScraper;
import extractor.service.adapter.ScrapingAdapter;
import extractor.service.properties.ComprasNetPropertiesService;

public class ComprasNetExtractorService extends ExtractorServiceStrategy {

	private ScrapingAdapter adapter;
	
	public ComprasNetExtractorService() {
		adapter = new ComprasNetJsoapScraper();
	}
	
	@Override
	public ResponseDto tryExtract() throws Exception {

		System.out.println("chegou na Compras Net");
		
		adapter.injectFirstUrl();
		adapter.scrapPageElements();
		adapter.getEntitys();

		return null;
	}

}
