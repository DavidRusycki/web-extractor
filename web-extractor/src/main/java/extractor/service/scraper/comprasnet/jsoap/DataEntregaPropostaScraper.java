package extractor.service.scraper.comprasnet.jsoap;

import org.jsoup.select.Elements;

import extractor.service.scraper.comprasnet.Scraper;

public class DataEntregaPropostaScraper extends Scraper {

	@Override
	public void scrap() {
		Elements elements = document.select("body > table:nth-child(3) > tbody > tr:nth-child(2) > td > table:nth-child(2) > tbody > tr:nth-child(2) > td.tex3 > table > tbody > tr > td");
		String result = elements.first().childNodes().get(0).childNode(19).toString();
		
		dto.setDataEntregaProposta(removeNonBreakingSpace(result));
	}

}
