package extractor.service.scraper.comprasnet.jsoap;

import org.jsoup.nodes.Element;

import extractor.service.scraper.comprasnet.Scraper;

public class ModalidadeScraper extends Scraper {

	@Override
	public void scrap() {
		Element element = document.select("body > table:nth-child(3) > tbody > tr:nth-child(2) > td > table:nth-child(2) > tbody > tr:nth-child(2) > td.tex3 > table > tbody > tr > td > p > span.tex3b").first();
		
		dto.setModalidade(element.html());
	}

}
