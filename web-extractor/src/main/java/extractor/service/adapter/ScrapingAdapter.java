package extractor.service.adapter;

public abstract class ScrapingAdapter {

	public abstract void injectFirstUrl();
	public abstract void scrapPageElements() throws Exception;
	public abstract String getEntitys();
	
}
