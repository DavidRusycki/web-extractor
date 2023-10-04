package extractor.service.scraper.comprasnet.jsoap;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import extractor.dto.ItemServicoDTO;
import extractor.service.scraper.comprasnet.Scraper;

public class ItensScraper extends Scraper {

	@Override
	public void scrap() {
		Elements linesOfItensTable = document.select("body > table:nth-child(3) > tbody > tr:nth-child(2) > td > table:nth-child(2) > tbody > tr:nth-child(3) > td.tex3 > table > tbody > tr");
		
		for (Element line : linesOfItensTable) {
			ItemServicoDTO ItemDto = new ItemServicoDTO();
			
			if ((line.select("span") != null) && (line.select("span").size() == 2)) {
				Element content = line.select("td:nth-child(2) > span.tex3").first();
				ItemDto.setConteudo(content.html());
			}
			else {
				// Salva todo o conteúdo do HTML da linha caso for uma situação não prevista. Assim não se perde informações.
				ItemDto.setConteudo(line.toString());
			}
			
			dto.addItem(ItemDto);
		}
	}

}
