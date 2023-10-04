package extractor.service.scraper.comprasnet.jsoap;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import extractor.service.scraper.comprasnet.Scraper;

public class OrgaoScraper extends Scraper {

	@Override
	public void scrap() {
		Element tabela = this.getOrgaoTable();
		Element last = tabela.select("p").last();
		Elements pElements = tabela.select("p");
		//Remove o último pois ele é o código da UASG
		pElements.remove(last);
		
		StringBuilder builder = new StringBuilder();
		for (Element element : pElements) {
			builder.append(element.html()+";");
		}
		
		dto.setOrgao(builder.toString());
	}
	
}
