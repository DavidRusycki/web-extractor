package extractor.service.scraper.comprasnet.jsoap;

import org.jsoup.select.Elements;

import extractor.service.scraper.comprasnet.Scraper;

public class ObjetoScraper extends Scraper {

	@Override
	public void scrap() {
		Elements elements = document.select("body > table:nth-child(3) > tbody > tr:nth-child(2) > td > table:nth-child(2) > tbody > tr:nth-child(2) > td.tex3 > table > tbody > tr > td");
		String result = elements.first().childNodes().get(0).childNode(4).toString();		
		
		dto.setObjeto(removeNonBreakingSpace(result));
	}

}
