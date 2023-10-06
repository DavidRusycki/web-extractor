package extractor.service.scraper.comprasnet;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import extractor.dto.ComprasNetDTO;
import lombok.Data;

@Data
public abstract class Scraper {

	protected ComprasNetDTO dto;
	protected Document document;
	
	public abstract void scrap();
	
	protected Element getOrgaoTable() {
		Element tabela = document.select("body > table:nth-child(3) > tbody > tr:nth-child(2) > td > table:nth-child(2) > tbody > tr:nth-child(1) > td.tex3 > table > tbody").first();
		
		return tabela;
	}
	
	protected String removeNonBreakingSpace(String content) {
		return content.replaceAll("&nbsp;", "");
	}
	
}
