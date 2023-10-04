package extractor.service.scraper.adapter;

import java.util.List;

import extractor.dto.BaseDto;

public abstract class ScrapingAdapter {

	public abstract void injectFirstUrl();
	public abstract void scrapPageElements() throws Exception;
	public abstract List<BaseDto> getDtos();
	
}
