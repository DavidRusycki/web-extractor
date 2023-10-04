package extractor.service.scraper.comprasnet.jsoap;

import org.jsoup.nodes.Element;

import extractor.service.scraper.comprasnet.Scraper;

public class UasgScraper extends Scraper {

	@Override
	public void scrap() {
		Element tabela = this.getOrgaoTable();
		Element last = tabela.select("p").last();
		String codigo = last.html().replaceAll("[\\D]", "");
		
		dto.setUasg(Integer.parseInt(codigo));
	}

}
